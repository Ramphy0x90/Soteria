package com.soteria;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.soteria.models.Credential;
import com.soteria.models.Entity;
import com.soteria.repositories.CredentialRepository;
import com.soteria.repositories.EntityRepository;
import com.soteria.models.Role;
import com.soteria.repositories.RoleRepository;
import com.soteria.models.User;
import com.soteria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This commandline is used to generate default entities
     * and roles for Soteria, it also creates a default admin user (root)
     *
     * @param entityRepository
     * @param roleRepository
     * @param userRepository
     * @param credentialRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(EntityRepository entityRepository,
                                        RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        CredentialRepository credentialRepository) throws IOException {

        // Object mapper to read json file and get entities
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get entities from json file
        List<Entity> entities = objectMapper.readValue(
                new File(new ClassPathResource("static/entities.default.json").getFile().toURI()),
                new TypeReference<>(){}
        );

        return args -> {
            entityRepository.saveAll(entities);

            Role standard = new Role("ROLE_STANDARD");
            Role admin = new Role("ROLE_ADMIN");
            roleRepository.saveAll(List.of(standard, admin));

            User user = new User(
                    "root",
                    passwordEncoder.encode("toor"),
                    new ArrayList<>()
            );
            user.setRole(admin);
            userRepository.save(user);

            Credential credentialGithub = new Credential(user, entities.get(0), "ramphy0x90", "Ramphy123@hello");
            Credential credentialOutlook = new Credential(user, entities.get(1), "ramphy_an@outlook.com", "Ramphy123@");
            credentialRepository.saveAll(List.of(credentialGithub, credentialOutlook));
        };
    }
}

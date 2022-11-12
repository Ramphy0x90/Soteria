package com.soteria;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(EntityRepository entityRepository,
                                        RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        CredentialRepository credentialRepository) {
        return args -> {
            Entity github = new Entity("Github", "github.com", "");
            Entity outlook = new Entity("Outlook", "outlook.com", "");
            Entity gmail = new Entity("Gmail", "gmail.com", "");
            entityRepository.saveAll(List.of(github, outlook, gmail));

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

            Credential credentialGithub = new Credential(user, outlook, "ramphy0x90", "Ramphy123@hello");
            Credential credentialOutlook = new Credential(user, outlook, "ramphy_an@outlook.com", "Ramphy123@");
            credentialRepository.saveAll(List.of(credentialGithub, credentialOutlook));
        };
    }
}

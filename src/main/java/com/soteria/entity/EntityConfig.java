package com.soteria.entity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EntityConfig {
    @Bean
    CommandLineRunner commandLineRunner(EntityRepository repository) {
        return args -> {
            Entity outlook = new Entity("Outlook", "outlook.com", "");
            Entity gmail = new Entity("Gmail", "gmail.com", "");

            repository.saveAll(List.of(outlook, gmail));
        };
    }
}

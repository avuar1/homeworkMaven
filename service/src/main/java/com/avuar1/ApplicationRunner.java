package com.avuar1;

import com.avuar1.config.ApplicationConfiguration;
import com.avuar1.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        
        try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            UserRepository userRepository = context.getBean(UserRepository.class);
            System.out.println(userRepository.findAll());
        }
    }
}

package com.example.assigment3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import validator.EmailValidator;
import validator.PasswordChecker;
import validator.PhoneValidator;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

//        return args -> {
//            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
//            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
//        };

        return args -> {
            log.info("Preloading " + repository.save(new User("Vardenis", "Pavardenis", "+37062527965", "email@prov.com", "an address", "abc123")));
        };
    }

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }

    @Bean
    public PhoneValidator phoneValidator() {
        return new PhoneValidator();
    }

    @Bean
    public PasswordChecker passwordChecker() {
        return new PasswordChecker();
    }

    @Bean
    public UserHandler userHandler(EmailValidator emailValidator, PhoneValidator phoneValidator, PasswordChecker passwordChecker, UserRepository repository) {
        return new UserHandler(emailValidator, phoneValidator, passwordChecker, repository);
    }
}
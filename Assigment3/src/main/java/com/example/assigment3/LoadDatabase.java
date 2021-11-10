package com.example.assigment3;

import com.example.assigment3.wrappers.EmailValidatorWrapper;
import com.example.assigment3.wrappers.PasswordValidatorWrapper;
import com.example.assigment3.wrappers.PhoneValidatorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("Vardenis", "Pavardenis",
                    "+37062527965", "email@prov.com", "an address", "abc123")));
        };
    }

    @Bean
    public EmailValidatorWrapper emailValidator() {
        return new EmailValidatorWrapper();
    }

    @Bean
    public PhoneValidatorWrapper phoneValidator() {
        return new PhoneValidatorWrapper();
    }

    @Bean
    public PasswordValidatorWrapper passwordChecker() {
        return new PasswordValidatorWrapper();
    }

    @Bean
    public UserHandler userHandler(EmailValidatorWrapper emailValidatorWrapper, PhoneValidatorWrapper phoneValidator,
                                   PasswordValidatorWrapper passwordChecker, UserRepository repository) {
        return new UserHandler(emailValidatorWrapper, phoneValidator, passwordChecker, repository);
    }
}
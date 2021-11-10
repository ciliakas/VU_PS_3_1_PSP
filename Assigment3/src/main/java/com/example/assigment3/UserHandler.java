package com.example.assigment3;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import validator.EmailValidator;
import validator.PasswordChecker;
import validator.PhoneValidator;

import javax.annotation.Resource;
import java.util.List;

public class UserHandler {
    private EmailValidator emailValidator;
    private PhoneValidator phoneValidator;
    private PasswordChecker passwordChecker;
    private final UserRepository repository;

    public UserHandler(EmailValidator emailValidator, PhoneValidator phoneValidator, PasswordChecker passwordChecker, UserRepository repository) {
        this.emailValidator = emailValidator;
        this.phoneValidator = phoneValidator;
        this.passwordChecker = passwordChecker;
        this.repository = repository;
    }

    public List<User> all() {
        return repository.findAll();
        //return repository.save(newUser);
    }

    public User newUser(User newUser) {
        return repository.save(newUser);
        //return repository.save(newUser);
    }

    public User one(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User replaceUser(User newUser, Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setEmail(newUser.getEmail());
                    user.setAddress(newUser.getAddress());
                    user.setPassword(newUser.getPassword());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}

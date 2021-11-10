package com.example.assigment3;

import com.example.assigment3.wrappers.EmailValidator;
import com.example.assigment3.wrappers.PasswordValidator;
import com.example.assigment3.wrappers.PhoneValidator;
import java.util.List;

public class UserHandler {
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;
    private final PasswordValidator passwordChecker;
    private final UserRepository repository;

    public UserHandler(EmailValidator emailValidator, PhoneValidator phoneValidator, PasswordValidator passwordChecker, UserRepository repository) {
        this.emailValidator = emailValidator;
        this.phoneValidator = phoneValidator;
        this.passwordChecker = passwordChecker;
        this.repository = repository;
    }

    public List<User> all() {
        return repository.findAll();
    }

    public User newUser(User newUser) {
        validateUser(newUser);
        return repository.save(newUser);
    }

    public User one(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User replaceUser(User newUser, Long id) {
        var userToEdit = repository.findById(id);
        if(userToEdit.isEmpty()) {
            throw new UserNotFoundException((id));
        }

        validateUser(newUser);
        var user = userToEdit.get();
        user.map(newUser);
        return repository.save(user);
    }

    public void deleteUser(Long id) {
        var user = repository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException((id));
        }

        repository.delete(user.get());
    }

    private void validateUser(User user){
        if(!emailValidator.validate(user.getEmail())){
            throw new UserInvalidRequestException("Email '" + user.getEmail() + "' is invalid");
        }
        if(!phoneValidator.validate(user.getPhoneNumber())){
            throw new UserInvalidRequestException("Phone number '" + user.getPhoneNumber() + "' is invalid");
        }
        if(!passwordChecker.validate(user.getPassword())){
            throw new UserInvalidRequestException("Password is invalid. Password must contain at least 8 symbols, an uppercase and a lowercase letter, a number and a special symbol");
        }
    }
}

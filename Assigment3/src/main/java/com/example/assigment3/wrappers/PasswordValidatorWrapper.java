package com.example.assigment3.wrappers;

import com.alemal.validation.PasswordChecker;

public class PasswordValidatorWrapper implements PasswordValidator{
    private final PasswordChecker passwordValidator;

    public PasswordValidatorWrapper() {
        this.passwordValidator = new PasswordChecker();
    }

    @Override
    public boolean validate(String password) {
        return passwordValidator.validate(password);
    }
}

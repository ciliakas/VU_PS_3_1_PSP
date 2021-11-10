package com.example.assigment3.wrappers;

import com.alemal.validation.PhoneNumber;
import com.example.assigment3.UserInvalidRequestException;

public class PhoneValidatorWrapper implements  PhoneValidator{
    private final com.alemal.validation.PhoneValidator phoneValidator;

    public PhoneValidatorWrapper() {
        this.phoneValidator = new com.alemal.validation.PhoneValidator();
    }

    //Due to limitations of time and the library implementation, I only validate lithuanian numbers
    @Override
    public boolean validate(String phoneNumber) {
        if(phoneNumber.charAt(0) == '8'){
            return phoneValidator.validate(phoneValidator.convert(PhoneNumber.CountryCode.LT, phoneNumber));
        }
        if(phoneNumber.substring(0, 4).equals("+370")) {
            return phoneValidator.validate(phoneNumber);
        }
        throw new UserInvalidRequestException("Please enter a lithuanian phone number");
    }
}

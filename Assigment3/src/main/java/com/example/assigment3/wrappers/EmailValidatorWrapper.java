package com.example.assigment3.wrappers;

import com.example.assigment3.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class EmailValidatorWrapper implements EmailValidator {
    private final com.alemal.validation.EmailValidator emailValidator;

    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!#$%&'*+-/=?^_`{|}~.";
    private static final String ALLOWED_DOMAIN_PART_SYMBOLS = LOWERCASE_LETTERS + UPPERCASE_LETTERS + DIGITS + ".-";

    public EmailValidatorWrapper() {
        this.emailValidator = new com.alemal.validation.EmailValidator();
    }

    //Due to limitations of time and the library implementation, I need to additionally validate the domain part of the email address
    @Override
    public boolean validate(String email) {
        var isValid = emailValidator.validate(email);
        if (isValid) {
            var domainP = getDomainPart(email);
            isValid = validateDomainPart(domainP);
        }
        return isValid;
    }

    private boolean validateDomainPart(String domainPart) {
        if (!validateDomainDots(domainPart) || !Utils.containsOnly(domainPart, ALLOWED_DOMAIN_PART_SYMBOLS)) {
            return false;
        }
        var subDomains = splitIntoSubDomains(domainPart);
        return Arrays.stream(subDomains).noneMatch(subDomain -> !validateDomainHyphens(subDomain));
    }

    private boolean validateDomainDots(String domainPart) {
        var indexes = Utils.getIndexes(domainPart, '.');
        return indexes.size() != 0
                && !indexes.contains(0) && !indexes.contains(domainPart.length() - 1) && Utils.checkForNoTouchingIndexes(indexes);
    }

    private boolean validateDomainHyphens(String domainPart) {
        var indexes = Utils.getIndexes(domainPart, '-');
        return !indexes.contains(0) && !indexes.contains(domainPart.length() - 1);
    }

    private String getDomainPart(String emailAddress) {
        var atIndex = emailAddress.indexOf('@');
        return  emailAddress.substring(atIndex + 1);
    }

    private String[] splitIntoSubDomains(String domain) {
        var indexes = Utils.getIndexes(domain, '.');
        var subDomains = new ArrayList<String>();
        var indexCount = indexes.size();
        subDomains.add(domain.substring(0, indexes.get(0)));
        for (var i = 0; i < indexCount; i++) {
            subDomains.add(i == indexCount - 1 ?
                    domain.substring(indexes.get(i) + 1) : domain.substring(indexes.get(i) + 1, indexes.get(i + 1)));
        }
        return subDomains.toArray(new String[0]);
    }
}

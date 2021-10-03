package validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EmailValidatorExtendedTest {

    EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    void checkStartHyphenDomain_False() {
        assertFalse(emailValidator.validate("abc.def@-mailarchive.com"));
    }

    @Test
    void checkEndHyphenDomain_False() {
        assertFalse(emailValidator.validate("abc.def@mail-archive-.com"));
    }

    @Test
    void checkDoubleDotLocal_False() {
        assertFalse(emailValidator.validate("abc..def@mail-archive.com"));
    }

    @Test
    void checkStartDotLocal_False() {
        assertFalse(emailValidator.validate(".abc.def@mail-archive.com"));
    }

    @Test
    void checkEndDotLocal_False() {
        assertFalse(emailValidator.validate("abc.def.@mail-archive.com"));
    }

    @Test
    void checkOnlyTLD_False() {
        assertFalse(emailValidator.validate("abc.def@com"));
    }

    @Test
    void checkNoDomain_False() {
        assertFalse(emailValidator.validate("abc.def@"));
    }

    @Test
    void checkNonExistentTLD_False() {
        assertFalse(emailValidator.validate("abc.def@mail.blablablaDontexist"));
    }

    @Test
    void checkDoubleAt_False() {
        assertFalse(emailValidator.validate("abc@.def@mail.com"));
    }

    @Test
    void checkDoubleAtTogether_False() {
        assertFalse(emailValidator.validate("abc.def@@mail.com"));
    }
}

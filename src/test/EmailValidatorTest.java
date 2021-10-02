package test;

import main.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EmailValidatorTest {

    EmailValidator emailValidator;
    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    void checkNoAtSign_False (){
        assertFalse(emailValidator.validate("gmail.com"));
    }

    @Test
    void checkNoNamePart_False (){
        assertFalse(emailValidator.validate("@gmail.com"));
    }

    @Test
    void checkValidName_True (){
        assertTrue(emailValidator.validate("abc_def@mail.com"));
    }

    @Test
    void checkAtSign_True (){
        assertTrue(emailValidator.validate("vardenis@gmail.com"));
    }

    @Test
    void checkInvalidSymbol1_False (){
        assertFalse(emailValidator.validate("vardenis pavardenis@gmail.com"));
    }

    @Test
    void checkInvalidSymbol2_False (){
        assertFalse(emailValidator.validate("vardenis\"is@gmail.com"));
    }

    @Test
    void checkInvalidDomain1_False (){
        assertFalse(emailValidator.validate("vardenis.pavardenis@g#mail.com"));
    }
    @Test
    void checkInvalidDomain2_False (){
        assertFalse(emailValidator.validate("vardenis.pavardenis@gmail..com"));
    }

    @Test
    void checkValidDomain_True (){
        assertTrue(emailValidator.validate("abc.def@mail-archive.com"));
    }

    @Test
    void checkEmpty_False (){
        assertFalse(emailValidator.validate(""));
    }

    @Test
    void checkNull_False (){
        assertFalse(emailValidator.validate(null));
    }

    @AfterEach
    void tearDown() {

    }
}

package test;

import main.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {

    PhoneValidator phoneValidator;

    @BeforeEach
    void setUp() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    void checkValid_LT_Number1_True() {
        assertTrue(phoneValidator.validate("867123456"));
    }

    @Test
    void checkValid_LT_Number2_True() {
        assertTrue(phoneValidator.validate("+37067123456"));
    }

    @Test
    void checkValid_PL_Number_True() {
        phoneValidator.addValidation(48, 9);
        assertTrue(phoneValidator.validate("48501123456"));
    }

    @Test
    void checkInvalid_LT_Length_False() {
        assertFalse(phoneValidator.validate("867123456736"));
    }

    @Test
    void checkInvalid_LT_Symbol_False() {
        assertFalse(phoneValidator.validate("867123+56"));
    }

    @Test
    void checkInvalid_LT_Letter_False() {
        assertFalse(phoneValidator.validate("37067123sZ6"));
    }

    @Test
    void checkInvalid_PL_Length_False() {
        phoneValidator.addValidation(48, 9);
        assertFalse(phoneValidator.validate("4850112345611251"));
    }

    @Test
    void checkNull_False() {
        assertFalse(phoneValidator.validate(null));
    }

    @Test
    void checkEmpty_False() {
        assertFalse(phoneValidator.validate(""));
    }


    @Test
    void checkAddValidation_Valid() {
        assertDoesNotThrow(() -> phoneValidator.addValidation(49, 10));
    }

    @Test
    void checkAddValidation_ZeroLength() {
        assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.addValidation(33, 0)
        );
    }

    @Test
    void checkAddValidation_NegativeLength() {
        assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.addValidation(33, -15)
        );
    }

    @Test
    void checkAddValidation_ZeroPrefix() {
        assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.addValidation(0, 10)
        );
    }

    @Test
    void checkAddValidation_NegativePrefix() {
        assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.addValidation(-10, 10)
        );
    }

    @Test
    void checkAddValidation_Duplicate() {
        phoneValidator.addValidation(33, 9);
        assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.addValidation(33, 9)
        );
    }

    @AfterEach
    void tearDown() {

    }
}

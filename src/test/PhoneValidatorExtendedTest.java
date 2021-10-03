package validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorExtendedTest {
    PhoneValidator phoneValidator;

    @BeforeEach
    void setUp() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    void checkValid_PL_Number_True() {
        phoneValidator.addValidation("+48", 9);
        assertTrue(phoneValidator.validate("+48501123456"));
    }
}

package tests;

import main.PhoneValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneValidatorTest
{
    /*
    Requirements:
        Nėra kitų simbolių nei skaičių
        Jei prasideda su 8, tai pakeičia į +370
        Yra galimybė pridėti naujų validavimo taisyklių pagal šalį (ilgis ir prefiksas)
    Notes:
        
     */
    //reikia konvertavima padaryt

    PhoneValidator phoneValidator;

    @BeforeEach
    void setup()
    {
        phoneValidator = new PhoneValidator();
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    @DisplayName("By default, should return true when phone number matches lithuanian international phone number format")
    void allowLithuanianInternationalNumberFormat()
    {
        assertTrue(phoneValidator.validate("+37061234567"));
    }

    @Test
    @DisplayName("By default, should return true when phone number matches lithuanian national phone number format")
    void allowLithuanianNationalNumberFormat()
    {
        assertTrue(phoneValidator.validate("861234567"));
    }


    @Test
    @DisplayName("Should return true when phone number matches provided phone number format")
    void allowCustomNumberFormat()
    {
        assertTrue(phoneValidator.validate("+123456789", "+123", 10));
    }

    @Test
    @DisplayName("Should return false when phone number is empty")
    void forbidEmpty()
    {
        assertFalse(phoneValidator.validate(""));
    }

    @Test
    @DisplayName("Should return false when phone number contains only whitespace")
    void forbidOnlyWhitespace()
    {
        assertFalse(phoneValidator.validate("     "));
    }

    @Test
    @DisplayName("Should return false when phone number contains non numeric characters")
    void forbidNonNumericCharacters()
    {
        assertFalse(phoneValidator.validate("+370!12 4567"));
    }

    @Test
    @DisplayName("Should return false when phone number contains letters")
    void forbidLetters()
    {
        assertTrue(phoneValidator.validate("+3706abc4567"));
    }

    @Test
    @DisplayName("Should return false when phone number length is shorter than given length requirement")
    void forbidTooShortLength()
    {
        assertFalse(phoneValidator.validate("+3706123", "+370", 12));
    }

    @Test
    @DisplayName("Should return false when phone number length is longer than given length requirement")
    void forbidTooLongLength()
    {
        assertFalse(phoneValidator.validate("+3706123456789123456", "+370", 12));
    }

    @Test
    @DisplayName("Should return false when phone number contains prefix not at the start")
    void forbidOutOfPlacePrefix()
    {
        assertFalse(phoneValidator.validate("123+37045678", "+370", 12));
    }

    @Test
    @DisplayName("Should return false when phone number contains prefix in the wrong order")
    void forbidWrongOrderPrefix()
    {
        assertFalse(phoneValidator.validate("+73012345678", "+370", 12));
    }

    @Test
    @DisplayName("Should return false when phone number does not contain prefix")
    void forbidMissingPrefix()
    {
        assertFalse(phoneValidator.validate("123412341234", "+370", 12));
    }

    @Test
    @DisplayName("Should throw error when custom prefix contains non numeric characters")
    void errorOnInvalidPrefix()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123a", 5)
        );

        String expectedMessage = "Prefix must contain only numeric characters or + at the start";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error when custom prefix contains + character and no number afterwards")
    void errorOnNoNumberAfterPlusInPrefix()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "+", 5)
        );

        String expectedMessage = "Prefix can must contain a number after +";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error when custom prefix contains + character not at the start of prefix")
    void errorOnInvalidPlusPlacementInPrefix()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "1+23", 5)
        );

        String expectedMessage = "Prefix can only contains + at the start";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error when length requirement is shorter than custom prefix length")
    void errorOnShorterLengthThanPrefix()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123", 2)
        );

        String expectedMessage = "Length requirement must be longer than prefix length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error when length requirement is equal custom prefix length")
    void errorOnRequirementLengthEqualToPrefixLength()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123", 3)
        );

        String expectedMessage = "Length requirement must be longer than prefix length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error when given length requirement is less than 1")
    void errorOnLengthRequirementLessThanOne()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123", 0)
        );

        String expectedMessage = "Length requirement must be more 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

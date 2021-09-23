package tests;

import main.PhoneValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest
{
    /*
    Requirements:
        Nėra kitų simbolių nei skaičių
        Jei prasideda su 8, tai pakeičia į +370
        Yra galimybė pridėti naujų validavimo taisyklių pagal šalį (ilgis ir prefiksas)
    Notes:
        Assuming that conversion is it's own separate function
        Assuming simple validation structure - user passes the required prefix and length, and the validator checks if the phone number matches
        Converter basically throws errors on every conversion that fails, maybe this could be changed to null string instead? I'm not sure which is the better way to handle logic
     */

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
    @DisplayName("When phone number is valid should convert from original prefix to new prefix")
    void when_phone_number_is_valid_should_convert_from_original_prefix_to_new_prefix()
    {
        assertEquals(phoneValidator.convert("+861234567", "8", "+370"), "+37061234567");
    }

    @Test
    @DisplayName("By default, phone number that matches lithuanian international phone number format should be valid")
    void by_default_phone_number_that_matches_lithuanian_international_phone_number_format_should_be_valid()
    {
        assertTrue(phoneValidator.validate("+37061234567"));
    }

    @Test
    @DisplayName("By default, phone number that matches lithuanian national phone number format should be valid")
    void by_default_phone_number_that_matches_lithuanian_national_phone_number_format_should_be_valid()
    {
        assertTrue(phoneValidator.validate("861234567"));
    }

    @Test
    @DisplayName("Phone number that matches provided phone number format should be valid")
    void phone_number_that_matches_provided_phone_number_format_should_be_valid()
    {
        assertTrue(phoneValidator.validate("+123456789", "+123", 10));
    }

    @Test
    @DisplayName("Phone number that is empty should be invalid")
    void phone_number_that_is_empty_should_be_invalid()
    {
        assertFalse(phoneValidator.validate(""));
    }

    @Test
    @DisplayName("Phone number that only contains whitespace should be invalid")
    void phone_number_that_only_contains_whitespace_should_be_invalid()
    {
        assertFalse(phoneValidator.validate("     "));
    }

    @Test
    @DisplayName("Phone number that contains non numeric characters should be invalid")
    void phone_number_that_contains_non_numeric_characters_should_be_invalid()
    {
        assertFalse(phoneValidator.validate("+370!12 4567"));
    }

    @Test
    @DisplayName("Phone number that contains phone number contains letters should be invalid")
    void phone_number_that_contains_phone_number_contains_letters_should_be_invalid()
    {
        assertTrue(phoneValidator.validate("+3706abc4567"));
    }

    @Test
    @DisplayName("Phone number that is shorter than required length should be invalid")
    void phone_number_that_is_shorter_than_required_length_should_be_invalid()
    {
        assertFalse(phoneValidator.validate("+3706123", "+370", 12));
    }

    @Test
    @DisplayName("Phone number that is longer than required length should be invalid")
    void phone_number_that_is_longer_than_required_length_should_be_invalid()
    {
        assertFalse(phoneValidator.validate("+3706123456789123456", "+370", 12));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123+37045678", "+73012345678", "123412341234"})
    @DisplayName("Phone number that doesn't start with given prefix should be invalid")
    void phone_number_that_does_not_start_with_given_prefix_should_be_invalid(String prefix)
    {
        assertFalse(phoneValidator.validate(prefix, "+370", 12));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123a", "+", "1+23"})
    @DisplayName("Should throw error on validate when custom prefix is invalid")
    void should_throw_error_on_validate_when_custom_prefix_is_invalid(String prefix)
    {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", prefix, 5)
        );

        String expectedMessage = "Prefix must contain only numeric characters or plus sign beforehand";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    @DisplayName("Should throw error on validate when length requirement is not longer than prefix length")
    void should_throw_error_on_validate_when_length_requirement_is_not_longer_than_prefix_length(int length)
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123", length)
        );

        String expectedMessage = "Length requirement must be longer than prefix length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should throw error on validate when given length requirement is less than 1")
    void should_throw_error_on_validate_when_given_length_requirement_is_less_than_1()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.validate("12345", "123", 0)
        );

        String expectedMessage = "Length requirement must be more 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"321", "23"})
    @DisplayName("Should throw error on convert when phone number does not start with from prefix")
    void should_throw_error_on_convert_when_phone_number_does_not_start_with_from_prefix(String prefix)
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.convert("12345", prefix, "3")
        );

        String expectedMessage = "Phone number must start with given from prefix";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123a", "+", "1+23"})
    @DisplayName("Should throw error on convert when from prefix is invalid")
    void should_throw_error_on_convert_when_from_prefix_is_invalid(String prefix)
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.convert("12345", prefix, "1")
        );

        String expectedMessage = "Prefix must contain only numeric characters or plus sign beforehand";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123a", "+", "1+23"})
    @DisplayName("Should throw error on convert when to prefix is invalid")
    void should_throw_error_on_convert_when_to_prefix_is_invalid(String prefix)
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                phoneValidator.convert("12345", "1", prefix)
        );

        String expectedMessage = "Prefix must contain only numeric characters or plus sign beforehand";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

package tests;

import main.PasswordChecker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordCheckerTest
{
    /*
    Requirements:
        Tikrina ar slaptažodžio ilgis netrumpesnis nei X
        Tikrina ar yra Uppercase simbolių
        Tikrina ar yra specialus simbolis (specialių simbolių sąrašas turi būti konfiguruojamas)
    Notes:
        Error thrown on length < 1, because I assume that password should be empty
     */

    PasswordChecker passwordChecker;

    @BeforeEach
    void setup()
    {
        passwordChecker = new PasswordChecker();
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    @DisplayName("Password which is not shorter then required, with uppercase letters and no special symbol required should be valid")
    void password_which_is_not_shorter_then_required_with_uppercase_letters_and_no_special_symbol_required_should_be_valid()
    {
        assertTrue(passwordChecker.validate("12345Abcd", 6));
    }

    @Test
    @DisplayName("Password which is not shorter then required, with uppercase letters and required special symbol should be valid")
    void password_which_is_not_shorter_then_required_with_uppercase_letters_and_required_special_symbol_should_be_valid()
    {
        assertTrue(passwordChecker.validate("123!Abcd", 6, new char[]{'!', '@', '#'}));
    }

    @Test
    @DisplayName("Password which is shorter than required should be invalid")
    void password_which_is_shorter_than_required_should_be_invalid()
    {
        assertFalse(passwordChecker.validate("1234A", 6));
    }

    @Test
    @DisplayName("Password which does not contain uppercase letters should be invalid")
    void password_which_does_not_contain_uppercase_letters_should_be_invalid()
    {
        assertFalse(passwordChecker.validate("abcd", 3));
    }

    @Test
    @DisplayName("Password which does not contain at least a single required special symbol should be invalid")
    void password_which_does_not_contain_at_least_a_single_required_special_symbol_should_be_invalid()
    {
        assertFalse(passwordChecker.validate("Abcd", 3, new char[]{'!', '@', '#'}));
    }

    @Test
    @DisplayName("Empty Password should be invalid")
    void empty_Password_should_be_invalid()
    {
        assertFalse(passwordChecker.validate("", 3));
    }

    @Test
    @DisplayName("Password only containing whitespace should be invalid")
    void password_only_containing_whitespace_should_be_invalid()
    {
        assertFalse(passwordChecker.validate("     ", 3));
    }

    @Test
    @DisplayName("Should throw error when given length requirement is less than 1")
    void should_throw_error_when_given_length_requirement_is_less_than_1()
    {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                passwordChecker.validate("abcA", 0)
        );

        String expectedMessage = "Length requirement must be more 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

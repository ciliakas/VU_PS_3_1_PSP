package tests;

import main.PhoneValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {
	/*
	 * Requirements: Nėra kitų simbolių nei skaičių Jei prasideda su 8, tai pakeičia
	 * į +370 Yra galimybė pridėti naujų validavimo taisyklių pagal šalį (ilgis ir
	 * prefiksas) Notes: Assuming that conversion is it's own separate function
	 * Assuming simple validation structure - user passes the required prefix and
	 * length, and the validator checks if the phone number matches Converter
	 * basically throws errors on every conversion that fails, maybe this could be
	 * changed to null string instead? I'm not sure which is the better way to
	 * handle logic
	 */

	PhoneValidator phoneValidator;

	@BeforeEach
	void setup() {
		phoneValidator = new PhoneValidator();
	}

	@Test
	@DisplayName("When phone number is valid should convert from original prefix to new prefix")
	void when_phone_number_is_valid_should_convert_from_original_prefix_to_new_prefix() {
		assertEquals(phoneValidator.convert("861234567"), "+37061234567");
	}

	@Test
	@DisplayName("By default, phone number that matches lithuanian international phone number format should be valid")
	void by_default_phone_number_that_matches_lithuanian_international_phone_number_format_should_be_valid() {
		assertTrue(phoneValidator.validate("+37061234567"));
	}

	@Test
	@DisplayName("By default, phone number that matches lithuanian national phone number format should be valid")
	void by_default_phone_number_that_matches_lithuanian_national_phone_number_format_should_be_valid() {
		assertTrue(phoneValidator.validate("861234567"));
	}

	@Test
	@DisplayName("Phone number that is empty should be invalid")
	void phone_number_that_is_empty_should_be_invalid() {
		assertFalse(phoneValidator.validate(""));
	}

	@Test
	@DisplayName("Phone number that only contains whitespace should be invalid")
	void phone_number_that_only_contains_whitespace_should_be_invalid() {
		assertFalse(phoneValidator.validate("     "));
	}

	@Test
	@DisplayName("Phone number that contains non numeric characters should be invalid")
	void phone_number_that_contains_non_numeric_characters_should_be_invalid() {
		assertFalse(phoneValidator.validate("+370!12 4567"));
	}

	@Test
	@DisplayName("Phone number that contains phone number contains letters should be invalid")
	void phone_number_that_contains_phone_number_contains_letters_should_be_invalid() {
		assertFalse(phoneValidator.validate("+3706abc4567"));
	}

	@Test
	@DisplayName("Phone number that is shorter than required length should be invalid")
	void phone_number_that_is_shorter_than_required_length_should_be_invalid() {
		assertFalse(phoneValidator.validate("+3706123"));
	}

	@Test
	@DisplayName("Phone number that is longer than required length should be invalid")
	void phone_number_that_is_longer_than_required_length_should_be_invalid() {
		assertFalse(phoneValidator.validate("+3706123456789123456"));
	}

	@ParameterizedTest
	@ValueSource(strings = { "123+37045678", "+73012345678", "123412341234" })
	@DisplayName("Phone number that doesn't start with given prefix should be invalid")
	void phone_number_that_does_not_start_with_given_prefix_should_be_invalid(String phone) {
		assertFalse(phoneValidator.validate(phone));
	}
}

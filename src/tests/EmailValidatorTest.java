package tests;

import main.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidatorTest {
	/*
	 * Requirements: Turi @ simbolį Neturi neleistinų simbolių Turi teisingą domeną
	 * ir TLD Notes: I assume that we're not going to check online if the domain
	 * actually exists, we only need to check if domain is syntactically correct
	 * Domain rules taken from:
	 * https://tools.ietf.org/id/draft-liman-tld-names-00.html#rfc.section.2
	 */

	EmailValidator emailValidator;

	@BeforeEach
	void setup() {
		emailValidator = new EmailValidator();
	}

	@Test
	@DisplayName("Email with at sign, no forbidden symbols and valid domain and tld should be valid")
	void email_with_at_symbol_no_forbidden_symbols_and_valid_domain_and_tld_should_be_valid() {
		assertTrue(emailValidator.validate("vardas@gmail.com"));
	}

	@Test
	@DisplayName("Email with at sign, no forbidden symbol requirement and valid domain and tld should be valid")
	void email_with_at_sign_no_forbidden_symbol_requirement_and_valid_domain_and_tld_should_be_valid() {
		assertTrue(emailValidator.validate("vardas@mif.stud.vu.lt"));
	}

	@Test
	@DisplayName("Domain label can contain hyphen not at the start or end")
	void domain_label_can_contain_hyphen_not_at_the_start_or_end() {
		assertTrue(emailValidator.validate("vardas@weirdsite.XN--AABDCED"));
	}

	@Test
	@DisplayName("Domain label can contain number not at the start")
	void domain_label_can_contain_number_not_at_the_start() {
		assertTrue(emailValidator.validate("vardas@weirdsite.abc4"));
	}

	@Test
	@DisplayName("Domain label can contain uppercase letters")
	void domain_label_can_contain_uppercase_letters() {
		assertTrue(emailValidator.validate("vardas@weirdsite.LT"));
	}

	@Test
	@DisplayName("Domain label containing non ASCII letters should be invalid")
	void domain_label_containing_non_ASCII_letters_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@lietuviškai.lt"));
	}

	@Test
	@DisplayName("Domain label containing symbols outside of (a-z, A-Z, 0–9, -) set should be invalid")
	void domain_label_containing_symbols_outside_of_allowed_set_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@weirdsite!.lt"));
	}

	@Test
	@DisplayName("Domain label containing more than 63 characters should be invalid")
	void domain_label_containing_more_than_63_characters_should_be_invalid() {
		assertFalse(emailValidator
				.validate("vardas@weirdsite.domaindomaindomaindomaindomaindomaindomaindomain64CharactersLong"));
	}

	@Test
	@DisplayName("Domain label containing less than 2 characters should be invalid")
	void domain_label_containing_less_than_2_characters_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@weirdsite.l"));
	}

	@Test
	@DisplayName("Domain label not starting with a letter should be invalid")
	void domain_label_not_starting_with_a_letter_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@-weirdsite.lt"));
	}

	@Test
	@DisplayName("Email not containing a domain should be invalid")
	void email_not_containing_a_domain_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@.lt"));
	}

	@Test
	@DisplayName("Email not containing TLD should be invalid")
	void email_not_containing_TLD_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas@gmail"));
	}

	@Test
	@DisplayName("Email not containing at sign should be invalid")
	void email_not_containing_at_sign_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas!mif.stud.vu.lt"));
	}

	@Test
	@DisplayName("Email not containing a name before at sign should be invalid")
	void email_not_containing_a_name_before_at_sign_should_be_invalid() {
		assertFalse(emailValidator.validate("@email.com"));
	}

	@Test
	@DisplayName("Email containing a symbol from forbidden symbol list should be invalid")
	void email_containing_a_symbol_from_forbidden_symbol_list_should_be_invalid() {
		assertFalse(emailValidator.validate("vardas$@mif.stud.vu.lt"));
	}

	@Test
	@DisplayName("Empty email should be invalid")
	void empty_email_should_be_invalid() {
		assertFalse(emailValidator.validate(""));
	}

	@Test
	@DisplayName("Email only containing whitespace should be invalid")
	void email_only_containing_whitespace_should_be_invalid() {
		assertFalse(emailValidator.validate("     "));
	}
}

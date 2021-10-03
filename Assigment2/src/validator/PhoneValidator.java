package validator;

import java.util.ArrayList;

public class PhoneValidator {
    private static final int MAX_PARSABLE_INT_LENGTH = 9;
    private final ArrayList<Rule> rules;

    public PhoneValidator() {
        this.rules = new ArrayList<>();
        this.rules.add(Rule.lithuanianNationalRule());
        this.rules.add(Rule.lithuanianInternationalRule());
    }

    public void addValidation(int prefix, int length) throws IllegalArgumentException {
        var rule = new Rule(prefix, length);
        validateNewRule(rule);
        rules.add(rule);
    }

    public void addValidation(String prefix, int length) throws IllegalArgumentException {
        var rule = new Rule(prefix, length);
        validateNewRule(rule);
        rules.add(rule);
    }

    public boolean validate(String phoneNumber) {
        for (var rule : rules) {
            if (validateRule(phoneNumber, rule)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateRule(String phoneNumber, Rule rule) {
        return phoneNumber != null &&
                validateLength(phoneNumber, rule) &&
                validateOnlyNumbers(phoneNumber) &&
                validatePrefix(phoneNumber, rule);
    }

    private boolean validateLength(String phoneNumber, Rule rule) {
        return phoneNumber.length() == rule.getLength() + rule.getPrefix().length();
    }

    private boolean validateOnlyNumbers(String phoneNumber) {
        try {
            var hasPlus = phoneNumber.charAt(0) == '+';
            var splitString = Utils.splitEveryXPlaces(hasPlus ? phoneNumber.substring(1) : phoneNumber, MAX_PARSABLE_INT_LENGTH);
            for (var string : splitString) {
                if(Integer.parseInt(string) < 0){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validatePrefix(String phoneNumber, Rule rule) {
        return phoneNumber.startsWith(rule.getPrefix());
    }

    private void validateNewRule(Rule rule) throws IllegalArgumentException {
        for (var existingRule : rules) {
            if (existingRule.equals(rule)) {
                throw new IllegalArgumentException("Duplicate rule");
            }
        }
    }
}

package validator;

class Rule {
    private static final int MAX_PARSABLE_INT_LENGTH = 9;
    private final String prefix;
    private final int length;

    public Rule(int prefix, int length) throws IllegalArgumentException {
        this.validate(prefix, length);
        this.prefix = Integer.toString(prefix);
        this.length = length;
    }

    public Rule(String prefix, int length) throws IllegalArgumentException {
        this.validate(prefix, length);
        this.prefix = prefix;
        this.length = length;
    }

    public static Rule lithuanianNationalRule() {
        return new Rule(8, 8);
    }

    public static Rule lithuanianInternationalRule() {
        return new Rule("+370", 8);
    }

    private void validate(int prefix, int length) throws IllegalArgumentException {
        validatePrefix(prefix);
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be more than 0");
        }
    }

    private void validate(String prefix, int length) throws IllegalArgumentException {
        validatePrefix(prefix);
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be more than 0");
        }
    }

    private void validatePrefix(int prefix) throws IllegalArgumentException {
        if (prefix <= 0) {
            throw new IllegalArgumentException("Prefix must be more than 0");
        }
    }

    private void validatePrefix(String prefix) throws IllegalArgumentException {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix can't be null");
        }
        try {
            var hasPlus = prefix.charAt(0) == '+';
            var splitString = Utils.splitEveryXPlaces(hasPlus ? prefix.substring(1) : prefix, MAX_PARSABLE_INT_LENGTH);
            for (var string : splitString) {
                Integer.parseInt(string);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Prefix must contain only numbers or a plus at the beginning", e);
        }
    }

    public boolean equals(Rule rule) {
        return rule.getLength() == this.length && rule.getPrefix().equals(this.prefix);
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLength() {
        return length;
    }
}

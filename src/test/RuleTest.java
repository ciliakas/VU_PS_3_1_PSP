package validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    void checkCreateRule_ValidIntPrefix() {
        assertDoesNotThrow(() -> new Rule(5, 5));
    }

    @Test
    void checkCreateRule_ValidStringPrefix1() {
        assertDoesNotThrow(() -> new Rule("5", 5));
    }

    @Test
    void checkCreateRule_ValidStringPrefix2() {
        assertDoesNotThrow(() -> new Rule("+5", 5));
    }

    @Test
    void checkCreateRule_NegativeStringPrefix_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Rule("-5", 5));
    }

    @Test
    void checkCreateRule_OnlyPlusPrefix_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Rule("+", 5));
    }

    @Test
    void checkCreateRule_OutOfPlacePlusPrefix_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Rule("4+5", 5));
    }

    @Test
    void checkCreateRule_IllegalSymbolPrefix_Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Rule("+45-", 5));
    }
}

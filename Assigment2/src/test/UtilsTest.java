package validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    private static Stream<Arguments> containsOnly() {
        return Stream.of(
                Arguments.of(null, null, true),
                Arguments.of("", null, true),
                Arguments.of(null, "", true),
                Arguments.of("", "", true),
                Arguments.of("smth", "", false),
                Arguments.of("smth", null, false),
                Arguments.of("abcd", "abc", false),
                Arguments.of("", "abc", true),
                Arguments.of(null, "abc", true),
                Arguments.of("abcabc", "abc", true),
                Arguments.of("a", "abc", true)
        );
    }

    private static Stream<Arguments> checkForNoTouchingIndexes() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 3), false),
                Arguments.of(Arrays.asList(1, 3, 6, 7, 9), false),
                Arguments.of(Arrays.asList(1, 3), true),
                Arguments.of(Collections.singletonList(1), true),
                Arguments.of(Collections.emptyList(), true),
                Arguments.of(null, true)
        );
    }

    private static Stream<Arguments> getIndexes() {
        return Stream.of(
                Arguments.of(null, 'c', Collections.emptyList()),
                Arguments.of("", 'c', Collections.emptyList()),
                Arguments.of("qwerty", 'c', Collections.emptyList()),
                Arguments.of("abccd", 'c', Arrays.asList(2, 3))
        );
    }

    private static Stream<Arguments> containsSymbol() {
        return Stream.of(
                Arguments.of(null, null, false),
                Arguments.of("", null, false),
                Arguments.of(null, "", false),
                Arguments.of("", "", false),
                Arguments.of("smth", "", false),
                Arguments.of("smth", null, false),
                Arguments.of("abcd", "abc", true),
                Arguments.of("", "abc", false),
                Arguments.of(null, "abc", false),
                Arguments.of("abcabc", "abc", true),
                Arguments.of("a", "abc", true)
        );
    }

    private static Stream<Arguments> splitEveryXPlaces() {
        return Stream.of(
                Arguments.of(null, 5, null),
                Arguments.of("message", 0, null),
                Arguments.of("", 5, new String[]{}),
                Arguments.of("message", 500, new String[]{"message"}),
                Arguments.of("message", 7, new String[]{"message"}),
                Arguments.of("message", 6, new String[]{"messag", "e"}),
                Arguments.of("message", 2, new String[]{"me", "ss", "ag", "e"}),
                Arguments.of("messages", 2, new String[]{"me", "ss", "ag", "es"})
        );
    }

    @ParameterizedTest
    @MethodSource
    void splitEveryXPlaces(String string, int place, String[] expected) {
        assertArrayEquals(expected, Utils.splitEveryXPlaces(string, place));
    }

    @ParameterizedTest
    @MethodSource
    void containsSymbol(String string, String symbols, boolean expected) {
        assertEquals(Utils.containsSymbol(string, symbols), expected);
    }

    @ParameterizedTest
    @MethodSource
    void getIndexes(String string, char symbol, List<Integer> expected) {
        assertEquals(Utils.getIndexes(string, symbol), expected);
    }

    @ParameterizedTest
    @MethodSource
    void checkForNoTouchingIndexes(List<Integer> list, boolean expected) {
        assertEquals(Utils.checkForNoTouchingIndexes(list), expected);
    }

    @ParameterizedTest
    @MethodSource
    void containsOnly(String string, String symbols, boolean expected) {
        assertEquals(Utils.containsOnly(string, symbols), expected);
    }
}

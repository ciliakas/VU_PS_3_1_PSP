package validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Utils {
    public static String[] splitEveryXPlaces(String string, int place) {
        if (string == null) {
            return null;
        }
        if (place < 1) {
            return null;
        }
        if (string.isEmpty()) {
            return null;
        }
        int length = string.length();
        int numberOfArrays = (length / place) + 1;
        var result = new ArrayList<String>();
        for (var i = 0; i < numberOfArrays; i++) {
            if (i != numberOfArrays - 1) {
                result.add(string.substring(i * place, (i + 1) * place));
            } else if (i * place != length) {
                result.add(string.substring(i * place));
            }
        }
        return result.toArray(new String[0]);
    }

    public static boolean containsSymbol(String string, String symbols) {
        if (string == null || symbols == null) {
            return false;
        }
        var chars = symbols.toCharArray();
        for (var symbol : chars) {
            if (string.contains(Character.toString(symbol))) {
                return true;
            }
        }
        return false;
    }

    public static List<Integer> getIndexes(String string, char symbol) {
        return IntStream
                .iterate(string.indexOf(symbol), index -> index >= 0, index -> string.indexOf(symbol, index + 1))
                .boxed()
                .collect(Collectors.toList());
    }

    public static boolean checkForNoTouchingIndexes(List<Integer> list) {
        for (var i = 0; i < list.size() - 1; i++) {
            if (list.get(i) + 1 == list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsOnly(String string, String symbols) {
        for (var i = 0; i < string.length(); i++) {
            if(!symbols.contains(Character.toString(string.charAt(i)))){
                return false;
            }
        }
        return true;
    }
}

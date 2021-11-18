package com.example.assigment3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
       public static List<Integer> getIndexes(String string, char symbol) {
        if (string == null) {
            return new ArrayList<>();
        }
        return IntStream
                .iterate(string.indexOf(symbol), index -> index >= 0, index -> string.indexOf(symbol, index + 1))
                .boxed()
                .collect(Collectors.toList());
    }

    public static boolean checkForNoTouchingIndexes(List<Integer> sortedList) {
        if(sortedList == null){
            return true;
        }
        for (var i = 0; i < sortedList.size() - 1; i++) {
            if (sortedList.get(i) + 1 == sortedList.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsOnly(String string, String symbols) {
        if(string == null || string.isEmpty()){
            return true;
        }
        if(symbols == null || symbols.isEmpty()){
            return false;
        }
        for (var i = 0; i < string.length(); i++) {
            if (!symbols.contains(Character.toString(string.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}

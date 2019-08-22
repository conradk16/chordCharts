package com.realbook.jazz.chord.charts;

import java.util.Comparator;

public class AlphabeticalComparator implements Comparator<String> {

    // Sorts alphabetically, not taking length into consideration

    public int compare(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (b.charAt(i) != a.charAt(i)) {
                return a.charAt(i) - b.charAt(i);
            }
        }
        return -1;
    }
}

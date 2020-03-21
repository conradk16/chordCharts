package com.realbook.jazz.chord.charts;

import java.util.ArrayList;

public class Bar {
    private static final int NONE = 0;
    private static final int A = 1;
    private static final int B = 2;
    private static final int C = 3;
    private static final int D = 4;

    public ArrayList<Chord> chords = new ArrayList<>();
    public boolean fourFour = false;
    public boolean threeFour = false;
    public boolean leftRepeat = false;
    public boolean rightRepeat = false;
    public boolean toCota = false;
    public boolean cota = false;
    public boolean firstEnding = false;
    public boolean secondEnding = false;
    public int section = NONE;
    public boolean isEmpty = false;
    public boolean isBlank = false;

    public Bar(String bar) {
        if(bar.equals("empty")) {
            isEmpty = true;
            return;
        }
        if(bar.charAt(0) == '%') {
            isBlank = true;
        }
        String centerSplitDelim = "[_]";
        String[] firstDivision = bar.split(centerSplitDelim);
        boolean centerSplit;
        boolean leftSplit;
        boolean rightSplit;
        //if there is a center split
        if(firstDivision.length == 2) {
            centerSplit = true;
            String sideSplitDelim = "[-]";
            String[] leftHalf = firstDivision[0].split(sideSplitDelim);
            //if there is a split down the left side
            if (leftHalf.length == 2) {
                leftSplit = true;
                leftHalf[0] = this.extractBarData(leftHalf[0]);
                chords.add(new Chord(0, leftHalf[0]));
                leftHalf[1] = this.extractBarData(leftHalf[1]);
                chords.add(new Chord(1, leftHalf[1]));
            } else {
                leftSplit = false;
                firstDivision[0] = this.extractBarData(firstDivision[0]);
                chords.add(new Chord(4, firstDivision[0]));
            }
            String[] rightHalf = firstDivision[1].split(sideSplitDelim);
            //if there is a split down the right side
            if (rightHalf.length == 2) {
                rightSplit = true;
                rightHalf[0] = this.extractBarData(rightHalf[0]);
                chords.add(new Chord(2, rightHalf[0]));
                rightHalf[1] = this.extractBarData(rightHalf[1]);
                chords.add(new Chord(3, rightHalf[1]));
            } else {
                rightSplit = false;
                firstDivision[1] = this.extractBarData(firstDivision[1]);
                chords.add(new Chord(5, firstDivision[1]));
            }
        }
        else {
            centerSplit = false;
            bar = this.extractBarData(bar);
            chords.add(new Chord(6, bar));
        }
    }

    private String extractBarData(String unprocessedChord) {
        String firstDelims = "[()]";
        String[] firstDivision = unprocessedChord.split(firstDelims);
        if(firstDivision.length == 1) {
            return firstDivision[0];
        }
        else {
            String secondDelims = "[&]";
            String[] modifiers = firstDivision[1].split(secondDelims);
            ArrayList<String> remainingModifiers = new ArrayList<>();
            for (int i = 0; i < modifiers.length; i++) {
                if (modifiers[i].equals("4/4")) {
                    fourFour = true;
                } else if (modifiers[i].equals("3/4")) {
                    threeFour = true;
                } else if (modifiers[i].equals("{")) {
                    leftRepeat = true;
                } else if (modifiers[i].equals("}")) {
                    rightRepeat = true;
                } else if (modifiers[i].equals("tocota")) {
                    toCota = true;
                } else if (modifiers[i].equals("cota")) {
                    cota = true;
                } else if (modifiers[i].equals("firstending")) {
                    firstEnding = true;
                } else if (modifiers[i].equals("secondending")) {
                    secondEnding = true;
                } else if (modifiers[i].equals("A")) {
                    section = A;
                } else if (modifiers[i].equals("B")) {
                    section = B;
                } else if (modifiers[i].equals("C")) {
                    section = C;
                } else if (modifiers[i].equals("D")) {
                    section = D;
                } else {
                    remainingModifiers.add(modifiers[i]);
                }
            }
            String toReturn = new String(firstDivision[0]);
            if (remainingModifiers.size() != 0) {
                toReturn = toReturn + "(";
                for (int i = 0; i < remainingModifiers.size(); i++) {
                    toReturn = toReturn + remainingModifiers.get(i);
                    if (i != remainingModifiers.size() - 1) toReturn = toReturn + "&";
                }
                toReturn = toReturn + ")";
            }
            return toReturn;
        }
    }
}

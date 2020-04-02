package com.realbook.jazz.chord.charts;

import java.util.ArrayList;

public class Bar {
    public static final int NONE = 0;
    public static final int A = 1;
    public static final int B = 2;
    public static final int C = 3;
    public static final int D = 4;
    public static final int IN = 5;
    public static final int VAMP = 6;

    public ArrayList<Chord> chords = new ArrayList<>();
    public boolean fourFour = false;
    public boolean threeFour = false;
    public boolean leftRepeat = false;
    public boolean rightRepeat = false;
    public boolean toCoda = false;
    public boolean coda = false;
    public boolean fine = false;
    public boolean dcalfine = false;
    public boolean dcal2 = false;
    public boolean dsal2 = false;
    public boolean dcalcoda = false;
    public boolean segno = false;
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
                } else if (modifiers[i].equals("tocoda")) {
                    toCoda = true;
                } else if (modifiers[i].equals("coda")) {
                    coda = true;
                } else if (modifiers[i].equals("fine")) {
                    fine = true;
                } else if (modifiers[i].equals("dcalfine")) {
                    dcalfine = true;
                } else if (modifiers[i].equals("dcal2")) {
                    dcal2 = true;
                } else if (modifiers[i].equals("dcalcoda")) {
                    dcalcoda = true;
                } else if (modifiers[i].equals("dsal2")) {
                    dsal2 = true;
                } else if (modifiers[i].equals("segno")) {
                    segno = true;
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
                } else if (modifiers[i].equals("in")) {
                    section = IN;
                } else if (modifiers[i].equals("V")) {
                    section = VAMP;
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

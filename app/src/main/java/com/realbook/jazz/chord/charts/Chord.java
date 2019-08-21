package com.realbook.jazz.chord.charts;

public class Chord {
    private static final int MAJOR7 = 0;
    private static final int MINOR7 = 1;
    private static final int HALFDIMINISHED7 = 2;
    private static final int DIMINISHED7 = 3;
    private static final int DOMINANT = 4;
    private static final int AUGMENTED7 = 5;
    private static final int MINMAJ7 = 6;
    private static final int MAJOR = 7;
    private static final int MINOR = 8;
    private static final int DIMINISHED = 9;
    private static final int AUGMENTED = 10;

    private static final int C = 0;
    private static final int CSHARP = 1;
    private static final int DFLAT = 2;
    private static final int D = 3;
    private static final int DSHARP = 4;
    private static final int EFLAT = 5;
    private static final int E = 6;
    private static final int F = 7;
    private static final int FSHARP = 8;
    private static final int GFLAT = 9;
    private static final int G = 10;
    private static final int GSHARP = 11;
    private static final int AFLAT = 12;
    private static final int A = 13;
    private static final int ASHARP = 14;
    private static final int BFLAT = 15;
    private static final int B = 16;

    private int key;
    private int type;
    private Chord chordSubstitute = null;
    private String modifier1 = null;
    private String modifier2 = null;
    private String overNote = null;
    private int locationInBar; //quarters are 0,1,2,3; halfs are 4,5; wholes are 6, -1 is a substitute

    public Chord(int locationInBar, String chord) {
        locationInBar = locationInBar;
        String delims = "[()&]";
        String[] parts = chord.split(delims);
        int[] keyAndType = getKeyAndType(parts[0]);
        key = keyAndType[0];
        type = keyAndType[1];

        int i = 1;
        while(i < parts.length) {
            if(parts[i].charAt(0) == '[') {
                String newChordString = parts[i].substring(1, parts[i].length() - 1);
                chordSubstitute = new Chord(-1, newChordString);
            }
            else if(parts[i].charAt(0) == '/') {
                overNote = parts[i];
            }
            else if(modifier1 != null) {
                modifier1 = parts[i];
            }
            else if(modifier1 == null) {
                modifier2 = parts[i];
            }
            else {
                System.out.println("maybe too many modifiers?");
            }
            i++;
        }

    }

    public static int[] getKeyAndType(String firstPart) {
        int charsEaten = 0;
        int key = -1;
        int type = -1;
        if ((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) != 'b' && firstPart.charAt(1) != '#') {
            key = C;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) == '#') {
            key = CSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) == 'b') {
            key = DFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = D;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) == '#') {
            key = DSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'E' && firstPart.charAt(1) == 'b') {
            key = EFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'E' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = E;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'F' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = F;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'F' && firstPart.charAt(1) == '#') {
            key = FSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) == 'b') {
            key = GFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = G;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) == '#') {
            key = GSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) == 'b') {
            key = AFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = A;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) == '#') {
            key = ASHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'B' && firstPart.charAt(1) == 'b') {
            key = BFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'B' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = B;
            charsEaten = 1;
        }

        if (firstPart.length() == charsEaten + 1) {
            if (firstPart.charAt(charsEaten) == 'M') {
                type = MAJOR;
            } else if (firstPart.charAt(charsEaten) == 'm') {
                type = MINOR;
            } else if (firstPart.charAt(charsEaten) == 'd') {
                type = DIMINISHED;
            } else if (firstPart.charAt(charsEaten) == 'a') {
                type = AUGMENTED;
            } else if (firstPart.charAt(charsEaten) == '7') {
                type = DOMINANT;
            }
        } else {
            if (firstPart.charAt(charsEaten) == 'M' && firstPart.charAt(charsEaten + 1) == '7') {
                type = MAJOR7;
            } else if (firstPart.charAt(charsEaten) == 'm' && firstPart.charAt(charsEaten + 1) == '7') {
                type = MINOR7;
            } else if (firstPart.charAt(charsEaten) == 'h' && firstPart.charAt(charsEaten + 1) == '7') {
                type = HALFDIMINISHED7;
            } else if (firstPart.charAt(charsEaten) == 'd' && firstPart.charAt(charsEaten + 1) == '7') {
                type = DIMINISHED7;
            } else if (firstPart.charAt(charsEaten) == 'a' && firstPart.charAt(charsEaten + 1) == '7') {
                type = AUGMENTED7;
            } else if (firstPart.charAt(charsEaten) == 'n' && firstPart.charAt(charsEaten + 1) == '7') {
                type = MINMAJ7;
            }
        }
        int[] toReturn = {key, type};
        return toReturn;
    }

}

package com.realbook.jazz.chord.charts;
import com.realbook.jazz.chord.charts.Global;

import java.util.ArrayList;

public class Chord {

    public int key;
    public int type;
    public Chord chordSubstitute = null;
    public ArrayList<String> modifiers = new ArrayList<>();
    public String overNote = null;
    public int locationInBar; //quarters are 0,1,2,3; halfs are 4,5; wholes are 6, -1 is a substitute

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
            else {
                modifiers.add(parts[i]);
            }
            i++;
        }

    }

    public static int[] getKeyAndType(String firstPart) {
        int charsEaten = 0;
        int key = -1;
        int type = -1;
        if ((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) != 'b' && firstPart.charAt(1) != '#') {
            key = Global.C;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) == '#') {
            key = Global.CSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) == 'b') {
            key = Global.DFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.D;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'D' && firstPart.charAt(1) == '#') {
            key = Global.DSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'E' && firstPart.charAt(1) == 'b') {
            key = Global.EFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'E' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.E;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'F' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.F;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'F' && firstPart.charAt(1) == '#') {
            key = Global.FSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) == 'b') {
            key = Global.GFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.G;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'G' && firstPart.charAt(1) == '#') {
            key = Global.GSHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) == 'b') {
            key = Global.AFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.A;
            charsEaten = 1;
        } else if ((firstPart.charAt(0)) == 'A' && firstPart.charAt(1) == '#') {
            key = Global.ASHARP;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'B' && firstPart.charAt(1) == 'b') {
            key = Global.BFLAT;
            charsEaten = 2;
        } else if ((firstPart.charAt(0)) == 'B' && firstPart.charAt(1) != '#' && firstPart.charAt(1) != 'b') {
            key = Global.B;
            charsEaten = 1;
        }

        if (firstPart.length() == charsEaten + 1) {
            if (firstPart.charAt(charsEaten) == 'M') {
                type = Global.MAJOR;
            } else if (firstPart.charAt(charsEaten) == 'm') {
                type = Global.MINOR;
            } else if (firstPart.charAt(charsEaten) == 'd') {
                type = Global.DIMINISHED;
            } else if (firstPart.charAt(charsEaten) == 'a') {
                type = Global.AUGMENTED;
            } else if (firstPart.charAt(charsEaten) == '7') {
                type = Global.DOMINANT;
            }
        } else {
            if (firstPart.charAt(charsEaten) == 'M' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.MAJOR7;
            } else if (firstPart.charAt(charsEaten) == 'm' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.MINOR7;
            } else if (firstPart.charAt(charsEaten) == 'h' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.HALFDIMINISHED7;
            } else if (firstPart.charAt(charsEaten) == 'd' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.DIMINISHED7;
            } else if (firstPart.charAt(charsEaten) == 'a' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.AUGMENTED7;
            } else if (firstPart.charAt(charsEaten) == 'n' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.MINMAJ7;
            }
        }
        int[] toReturn = {key, type};
        return toReturn;
    }

}

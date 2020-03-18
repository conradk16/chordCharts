package com.realbook.jazz.chord.charts;
import com.realbook.jazz.chord.charts.Global;

import java.util.ArrayList;

public class Chord {

    public boolean empty;
    public int key;
    public int type;
    public Chord chordSubstitute = null;
    public ArrayList<String> modifiers = new ArrayList<>();
    public String overNote = null;
    public int locationInBar; //quarters are 0,1,2,3; halfs are 4,5; wholes are 6, -1 is a substitute

    public Chord(int locationInBarArg, String chord) {
        System.out.println("chordString: " + chord);
        locationInBar = locationInBarArg;
        if(chord.equals("/")) {
            empty = true;
            return;
        }
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
        if((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) == 'b') {
            key = Global.CFLAT;
            charsEaten = 2;
        }else if ((firstPart.charAt(0)) == 'C' && firstPart.charAt(1) != 'b' && firstPart.charAt(1) != '#') {
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
            } else if (firstPart.charAt(charsEaten) == '6') {
                type = Global.MAJOR6;
            }
        } else {
            if (firstPart.charAt(charsEaten) == 'M' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.MAJOR7;
            } else if (firstPart.charAt(charsEaten) == 'm' && firstPart.charAt(charsEaten + 1) == '7') {
                type = Global.MINOR7;
            } else if (firstPart.charAt(charsEaten) == 'm' && firstPart.charAt(charsEaten + 1) == '6') {
                type = Global.MINOR6;
            } else if (firstPart.charAt(charsEaten) == 'm' && firstPart.charAt(charsEaten + 1) == '9') {
                type = Global.MINOR9;
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

    //return: String[0] is the main symbol, String[1] is the lower text, String[2] is the upper text
    public String[] getChordText() {
        if(empty)
            return new String[] {" /", "", ""};

        String keyText = "";
        String lowerText = "";
        String upperText = "";

        if(key == Global.CFLAT) { keyText = "C"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.C) { keyText = "C"; }
        else if(key == Global.CSHARP) { keyText = "C"; upperText = Character.toString((char)0x266f);}
        else if(key == Global.DFLAT) { keyText = "D"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.D) { keyText = "D";}
        else if(key == Global.DSHARP) { keyText = "D"; upperText = Character.toString((char)0x266f);}
        else if(key == Global.EFLAT) { keyText = "E"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.E) { keyText = "E";}
        else if(key == Global.F) { keyText = "F";}
        else if(key == Global.FSHARP) { keyText = "F"; upperText = Character.toString((char)0x266f);}
        else if(key == Global.GFLAT) { keyText = "G"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.G) { keyText = "G";}
        else if(key == Global.GSHARP) { keyText = "G"; upperText = Character.toString((char)0x266f);}
        else if(key == Global.AFLAT) { keyText = "A"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.A) { keyText = "A";}
        else if(key == Global.ASHARP) { keyText = "A"; upperText = Character.toString((char)0x266f);}
        else if(key == Global.BFLAT) { keyText = "B"; upperText = Character.toString((char)0x266d);}
        else if(key == Global.B) { keyText = "B";}

        if(type == Global.MAJOR7) {lowerText = Character.toString((char) 0x25b3) + "7";}
        else if(type == Global.MAJOR6) {lowerText = "6";}
        else if(type == Global.MINOR7) {lowerText = Character.toString((char) 0x2013) + "7";}
        else if(type == Global.MINOR6) {lowerText = Character.toString((char) 0x2013) + "6";}
        else if(type == Global.MINOR9) {lowerText = Character.toString((char) 0x2013) + "9";}
        else if(type == Global.HALFDIMINISHED7) {lowerText = Character.toString((char) 0x00f8) + "7";}
        else if(type == Global.DIMINISHED7) {lowerText = Character.toString((char) 0x006f) + "7";}
        else if(type == Global.DOMINANT) {lowerText = "7";}
        else if(type == Global.AUGMENTED7) {lowerText = Character.toString((char) 0x002b) + "7";}
        else if(type == Global.MINMAJ7) {lowerText = Character.toString((char) 0x2013) +
                Character.toString((char) 0x25b3) + "7";}
        else if(type == Global.MAJOR) {lowerText = "";}
        else if(type == Global.MINOR) {lowerText = Character.toString((char) 0x2013);}
        else if(type == Global.DIMINISHED) {lowerText = Character.toString((char) 0x006f);}
        else if(type == Global.AUGMENTED) {lowerText = Character.toString((char) 0x002b);}

        for(int i = 0; i < modifiers.size(); i++) {
            String modifier = modifiers.get(i);
            if(modifier.equals("b9")) { lowerText = lowerText + Character.toString((char)0x266d) + "9";}
            else if(modifier.equals("b13")) { lowerText = lowerText + Character.toString((char)0x266d) + "13";}
            else if(modifier.equals("#11")) {lowerText = lowerText + Character.toString((char)0x266f) + "11";}
            else if(modifier.equals("#9")) {lowerText = lowerText + Character.toString((char)0x266f) + "9";}
            else if(modifier.equals("#5")) {lowerText = lowerText + Character.toString((char)0x266f) + "5";}
            else if(modifier.equals("alt")) {lowerText = lowerText + "alt";}
            else if(modifier.equals("sus")) {lowerText = lowerText + "sus";}

        }
        String[] toReturn = {keyText, lowerText, upperText};
        return toReturn;
    }

}

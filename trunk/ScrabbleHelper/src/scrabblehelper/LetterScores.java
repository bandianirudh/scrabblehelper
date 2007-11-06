/*
 * LetterScores.java
 *
 * Created on September 12, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scrabblehelper;

import java.util.Arrays;

/**
 *
 * @author Nick
 */
public class LetterScores {
    static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    public static char EMPTY_SQUARE = Character.MIN_VALUE;
    public static char OUT_OF_BOUNDS = ' ';
    public static char UNUSED_BLANK = '0';
    
    
    
    static int[] scores = {1, 2, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    /** Creates a new instance of LetterScores */
    public LetterScores() {
    }
    
    public static boolean isValidLetter(char c) {
        return (Character.isLetter(c));
    }
    
    public static char getCharFromInt(int i) {
        return alphabet[i];
    }
    
    public static int getIntFromChar(char c) {
        return Arrays.binarySearch(alphabet, Character.toUpperCase(c));
    }
    
    public static int getIntFromCharGuaranteedUppercase(char upperCaseCharacter) {
        return Arrays.binarySearch(alphabet, upperCaseCharacter);
    }
    
    public static int getLetterScore(char c) {
        return scores[getIntFromChar(c)];
    }
    
    public static int getWordScore(char[] word) {
        int score = 0;
        for (char c : word) score += getLetterScore(c);
        if (word.length >= 7) score += 50;
        return score;
    }
}
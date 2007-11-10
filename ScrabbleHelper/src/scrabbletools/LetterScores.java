/*
 * LetterScores.java
 *
 * Created on September 12, 2007, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package scrabbletools;

import java.util.Arrays;

/**
 *
 * @author Nick
 */
public class LetterScores {

    public static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static char[] allLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static char[] lowerCaseLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static char EMPTY_SQUARE = Character.MIN_VALUE;
    public static char OUT_OF_BOUNDS = ' ';
    public static char UNUSED_BLANK = '0';
    static {
        Arrays.sort(allLetters);
    }
    static int[] scores = {1, 2, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    /** Creates a new instance of LetterScores */

    public LetterScores() {
    }

    public static boolean isValidLetter(char c) {
        return (Arrays.binarySearch(allLetters, c) >= 0);
    }

    public static char getCharFromInt(int i) {
        return alphabet[i];
    }

    public static int getIntFromChar(char c) {
        return Arrays.binarySearch(allLetters, Character.toUpperCase(c)) % 26;
    }

    public static int getIntFromCharGuaranteedUppercase(char upperCaseCharacter) {
        return Arrays.binarySearch(alphabet, upperCaseCharacter);
    }

    public static int getLetterScore(char c) {
        return scores[getIntFromChar(c)];
    }

    public static int getBoardIndependentWordScore(char[] word) {
        int score = 0;
        for (char c: word) {
            score += getLetterScore(c);
        }
        return score;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package competition;

import java.util.Random;
import scrabbletools.LetterScores;

/**
 *
 * @author Nick
 */
public class LetterSupply {

    public static final int[] FREQUENCIES = {1, 2, 3, 4, 5, 6, 7, 5, 2, 3, 5, 8, 5, 2, 4, 5, 3, 2, 1, 5, 7, 8, 5, 4, 3, 4, 2};
    char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0'};
    int[] gameFreqs = FREQUENCIES.clone();

    public char[] getLetters(int numberOfThem) {
        String result = "";
        int resultSize = Math.min(numberOfThem, getNumberOfRemainingLetters());

        for (int i = 0; i < resultSize; i++) {
            int index = new Random().nextInt(27);
            if (gameFreqs[index] > 0) {
                result += LETTERS[index];
                gameFreqs[index]--;
            }
        }

        return result.toCharArray();
    }

    public char pollNextLetter() {
        if (getNumberOfRemainingLetters() == 0) {
            return LetterScores.EMPTY_SQUARE;
        }

        int index = new Random().nextInt(27);
        gameFreqs[index]--;
        return LETTERS[index];
    }

    public int getNumberOfRemainingLetters() {
        int result = 0;
        for (int i : gameFreqs) {
            result += i;
        }
        return result;
    }
}

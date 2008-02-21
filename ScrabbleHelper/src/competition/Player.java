/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package competition;

import java.util.ArrayList;
import java.util.Arrays;
import scrabbletools.LetterScores;

/**
 *
 * @author Nick
 */
public class Player {

    ArrayList<Character> rack = new ArrayList<Character>();
    private int score = 0;

    public Player(LetterSupply ls) {
        for (int i = 0; i < 7; i++) {
            rack.add(ls.pollNextLetter());
        }
    }
    
    public char[] getRackCharArray() {
        char[] result = new char[rack.size()];
        int i = 0;
        for (char c : rack) {
            result[i] = c;
            i++;
        }
        return result;
    }
    
    public void removeAndRefillLetters(char[] lettersRemoved, LetterSupply ls) {
        rack.removeAll(Arrays.asList(lettersRemoved));
        for (int i = 0; i < lettersRemoved.length; i++) {
            rack.remove(lettersRemoved[i]);
            rack.add(LetterScores.EMPTY_SQUARE);
        }
        refillLetters(ls);
    }
    
    public void refillLetters(LetterSupply ls) {
        for (int i = 0; i < rack.size(); i++) {
            if (rack.get(i) != LetterScores.EMPTY_SQUARE) {
                char newLetter = ls.pollNextLetter();
                if (newLetter != LetterScores.EMPTY_SQUARE) {
                    rack.set(i, newLetter);
                } else {
                    rack.remove(i);
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void addToScore(int scoreToAdd) {
        setScore(getScore() + scoreToAdd);
    }
}

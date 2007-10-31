/*
 * Word.java
 *
 * Created on September 12, 2007, 9:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scrabblehelper;

/**
 *
 * @author Nick
 */
public class Word implements Comparable {
    
    String word;
    
    /** Creates a new instance of Word */
    public Word(String word) {
        this.word = word;
    }
    
    public int getScore() {
        return LetterScores.getWordScore(word.toCharArray());
    }

    public int compareTo(Object o) {
        if (!(o instanceof Word)) return -1;
        int other = ((Word)o).getScore();
        int myScore = this.getScore();
        if (myScore > other) {
            return 1;
        } else if (myScore == other) {
            return 0;
        } else {
            return -1;
        }
    }
    
    public String getWordString() {
        return word;
    }
    
    public String toString() {
        return word + ": " + getScore();
    }
}

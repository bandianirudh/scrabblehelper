/*
 * TileLine.java
 *
 * Created on September 13, 2007, 3:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scrabblehelper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nick
 */
public class TileLine {
    
    public static final int SINGLE_LETTER = 0;
    public static final int DOUBLE_LETTER = 1;
    public static final int TRIPLE_LETTER = 2;
    public static final int DOUBLE_WORD = 3;
    public static final int TRIPLE_WORD = 4;
    
    public static final int BOARD_END = 5;
    public static final int BOARD_BEGINNING = 6;
    
    String[] regexes;
    int[] tileTypes;
    
    /** Creates a new instance of TileLine */
    public TileLine(String[] regexes, int[] tileTypes) {
        this.regexes = regexes;
        this.tileTypes = tileTypes;
    }
    
    public boolean wordFits(Word word) {
        return Pattern.matches(getCombinedRegEx(), word.toString());
    }
    
    public String getCombinedRegEx() {
        String result = "";
        for (int i = 0; i < regexes.length; i++) {
            result += ("(" + regexes[i] + ")");
        }
        return result;
    }
    
    public String[] getRegexArray() {
        return regexes;
    }
    
    public char[] getWordPosition(Word w) {
        Matcher m = Pattern.compile(getCombinedRegEx(),
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(w.getWordString());
        if (m.matches()) {
            char[] wordPosition = new char[tileTypes.length];
            Arrays.fill(wordPosition, '0');
            for (int i = 1; i <= m.groupCount(); i++) {
                String currentGroup = "";
                if (!(currentGroup = m.group(i)).isEmpty()) {
                    wordPosition[i - 1] = currentGroup.toCharArray()[0];
                }
            }
            return wordPosition;
        } else {
            return new char[0];
        }
    }
    
    public int getScore(Word w) {
        char[] wordPosition = getWordPosition(w);
        int score = 0;
        for (int i = 0; i < wordPosition.length; i++) {
            if (Character.isLetter(wordPosition[i])) {
                int tileType = tileTypes[i];
                int letterScore = LetterScores.getLetterScore(wordPosition[i]);
                if (tileType == SINGLE_LETTER) {
                    letterScore *= 1;
                } else if (tileType == DOUBLE_LETTER) {
                    letterScore *= 2;
                } else if (tileType == TRIPLE_LETTER) {
                    letterScore *= 3;
                }
                score += letterScore;
            }
        }
        
        for (int i = 0; i < wordPosition.length; i++) {
            if (Character.isLetter(wordPosition[i])) {
                int tileType = tileTypes[i];
                int letterScore = LetterScores.getLetterScore(wordPosition[i]);
                if (tileType == DOUBLE_WORD) {
                    score *= 2;
                } else if (tileType == TRIPLE_WORD) {
                    score *= 3;
                }
            }
        }
        return score;
    }
}

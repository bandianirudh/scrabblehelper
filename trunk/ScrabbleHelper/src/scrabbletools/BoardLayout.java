/*
 * Board.java
 *
 * Created on September 29, 2007, 6:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package scrabbletools;

import java.awt.Color;

/**
 *
 * @author Nick
 */
public class BoardLayout {

    public static final char EMPTY_CHARACTER = Character.MIN_VALUE;
    public static final char SINGLE_LETTER = '0';
    public static final char DOUBLE_LETTER = '2';
    public static final char TRIPLE_LETTER = '3';
    public static final char DOUBLE_WORD = '4';
    public static final char TRIPLE_WORD = '5';
    public static final Color BEIGE = new Color(200, 200, 100);
    public static char[][] charBoardValues;
    public static final String[] stringBoardValues = {"500200050002005",
            "040003000300040",
            "004000202000400",
            "200400020004002",
            "000040000040000",
            "030003000300030",
            "002000202000200",
            "500200040002005",
            "002000202000200",
            "030003000300030",
            "000040000040000",
            "200400020004002",
            "004000202000400",
            "040003000300040",
            "500200050002005"};
    static {
        charBoardValues = generateCharBoardValues();
    }

    /** Creates a new instance of Board */
    public BoardLayout() {
    }

    public static Color getColorFromTileType(char tileType) {
        switch (tileType) {
            case SINGLE_LETTER:
                return BEIGE;
            case DOUBLE_LETTER:
                return Color.CYAN;
            case TRIPLE_LETTER:
                return Color.BLUE;
            case DOUBLE_WORD:
                return Color.PINK;
            case TRIPLE_WORD:
                return Color.RED;
        }
        return Color.BLACK;
    }

    public static int getWordScore(SingleWordOnBoard word) {
        return getWordScore(word.startRow, word.startCol, word.word, word.occupiedTiles, word.isAcross);
    }

    public static int getWordScore(int startRow, int startCol, char[] word, boolean[] occupiedTiles, boolean isAcross) {
        int score = 0;
        //look for single-tile modifiers
        for (int i = 0; i < word.length; i++) {
            int row = startRow;
            int col = startCol;
            if (isAcross) {
                col += i;
            } else {
                row += i;
            }
            char c = word[i];
            if (Character.isUpperCase(c)) { //only add if letter is uppercase (don't bother with played blanks)
                int letterScore = LetterScores.getLetterScore(c);
                if (!occupiedTiles[i]) {
                    switch (charBoardValues[row][col]) {
                        case DOUBLE_LETTER:
                            letterScore *= 2;
                            break;
                        case TRIPLE_LETTER:
                            letterScore *= 3;
                            break;
                    }
                }
                score += letterScore;
            }
        }

        //look for word modifiers
        for (int i = 0; i < word.length; i++) {
            int row = startRow;
            int col = startCol;
            if (isAcross) {
                col += i;
            } else {
                row += i;
            }
            char modifier = charBoardValues[row][col];
            if (!occupiedTiles[i]) {
                switch (modifier) {
                    case DOUBLE_WORD:
                        score *= 2;
                        break;
                    case TRIPLE_WORD:
                        score *= 3;
                        break;
                }
            }
        }

        //check for bingo
        int playedTiles = 0;
        for (boolean b : occupiedTiles) {
            if (!b) {
                playedTiles++;
            }
        }
        if (playedTiles == 7) {
            score += 50; //bingo!
        }
        return score;
    }

    private static char[][] generateCharBoardValues() {
        char[][] result = new char[stringBoardValues.length][];
        for (int row = 0; row <
                stringBoardValues.length; row++) {
            result[row] = stringBoardValues[row].toCharArray();
        }
        return result;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Board {

    private char[][] letters;
    private char[][] storedLetters;

    public Board() {
        initializeLetters();
    }

    public void initializeLetters() {
        setLetters(new char[BoardLayout.stringBoardValues.length][]);
        for (int row = 0; row < getLetters().length; row++) {
            getLetters()[row] = new char[BoardLayout.stringBoardValues[0].length()];
            Arrays.fill(getLetters()[row], LetterScores.EMPTY_SQUARE);
        }
    }

    public char[] getCharLine(int startRow, int startCol,
            int length, boolean isAcross) {
        char[] result = new char[length];
        if (isAcross) {
            for (int col = 0; col < length; col++) {
                result[col] = getValue(startRow, col + startCol);
            }
        } else {  //is vertical
            for (int row = 0; row < length; row++) {
                result[row] = getValue(row + startRow, startCol);
            }
        }
        return result;
    }

    public void setCharLine(int startRow, int startCol,
            char[] word, boolean isAcross) {
        if (isAcross) {
            for (int col = 0; col < word.length; col++) {
                char toSet = word[col];
                if (LetterScores.isValidLetter(toSet)) {
                    getLetters()[startRow][col + startCol] = toSet;
                }
            }
        } else {  //is vertical
            for (int row = 0; row < word.length; row++) {
                char toSet = word[row];
                if (LetterScores.isValidLetter(toSet)) {
                    getLetters()[row + startRow][startCol] = toSet;
                }
            }
        }
    }

    public char getValue(int row, int col) {
        try {
            return getLetters()[row][col];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return LetterScores.OUT_OF_BOUNDS;
        }
    }

    public boolean squareHasAdjascentVerticalLetter(int row, int col) {
        boolean result = false;
        char left = getValue(row - 1, col);
        char right = getValue(row + 1, col);
        result = result || (!(left == LetterScores.EMPTY_SQUARE) ||
                left == LetterScores.OUT_OF_BOUNDS);
        result = result || (!(right == LetterScores.EMPTY_SQUARE) ||
                right == LetterScores.OUT_OF_BOUNDS);
        return result;
    }

    public boolean squareHasAdjascentHorizontalLetter(int row, int col) {
        boolean result = false;
        char above = getValue(row, col - 1);
        char below = getValue(row, col + 1);
        result = result || (!(above == LetterScores.EMPTY_SQUARE) ||
                above == LetterScores.OUT_OF_BOUNDS);
        result = result || (!(below == LetterScores.EMPTY_SQUARE) ||
                below == LetterScores.OUT_OF_BOUNDS);
        return result;
    }

    public boolean squareHasAdjascentLetter(int row, int col) {
        return squareHasAdjascentHorizontalLetter(row, col) ||
                squareHasAdjascentVerticalLetter(row, col);
    }

    public List<char[]> getWords(int startRow, int startCol,
            boolean isAcross, char[] word) {
        ArrayList<char[]> result = new ArrayList<char[]>();
        result.add(word);
        if (isAcross) {
            for (int loc = 0; loc < word.length; loc++) {
                int col = loc + startCol;
                if (getValue(startRow, col) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentVerticalLetter(loc, loc)) {
                    result.add(getVerticalWordFromSquare(startRow, col, word[loc]));
                }
            }
        } else {
            for (int loc = 0; loc < word.length; loc++) {
                int row = loc + startRow;
                if (getValue(row, startCol) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentHorizontalLetter(loc, loc)) {
                    result.add(getHorizontalWordFromSquare(row, startCol, word[loc]));
                }
            }
        }
        return result;
    }

    public List<char[]> getWordsFromSingleLetterPlay(int row, int col, char letter) {
        ArrayList<char[]> result = new ArrayList<char[]>();
        char[] array = new char[1];
        array[0] = letter;

        if (squareHasAdjascentHorizontalLetter(row, col)) {
            result.addAll(getWords(row, col, false, array));
        }
        if (squareHasAdjascentVerticalLetter(row, col)) {
            result.addAll(getWords(row, col, true, array));
        }

        return result;
    }

    public char[] getVerticalWordFromSquare(int row, int col, char possibleChar) {
        int topRow = row;
        int bottomRow = row;

        while (LetterScores.isValidLetter(getValue(--topRow, col))) {
        }
        while (LetterScores.isValidLetter(getValue(++bottomRow, col))) {
        }

        char[] result = getCharLine(topRow + 1, col,
                bottomRow - topRow - 1, false);
        if (LetterScores.isValidLetter(possibleChar)) {
            result[row - topRow + 1] = possibleChar;
        }
        return result;
    }

    public char[] getHorizontalWordFromSquare(int row, int col, char possibleChar) {
        int leftCol = col;
        int rightCol = col;

        while (LetterScores.isValidLetter(getValue(row, --leftCol))) {
        }
        while (LetterScores.isValidLetter(getValue(row, ++rightCol))) {
        }

        char[] result = getCharLine(leftCol + 1, col,
                rightCol - leftCol - 1, true);
        if (LetterScores.isValidLetter(possibleChar)) {
            result[col - leftCol + 1] = possibleChar;
        }
        return result;
    }

    public Dimension getBoardSize() {
        return new Dimension(getLetters().length, getLetters()[0].length);
    }

    public char[][] getLetters() {
        return letters;
    }

    public void setLetters(char[][] letters) {
        this.letters = letters;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbletools;

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

    public Board() {
        initializeLetters();
    }

    public void initializeLetters() {
        setLetters(new char[BoardLayout.charBoardValues.length][]);
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
        result = result || LetterScores.isValidLetter(left);
        result = result || LetterScores.isValidLetter(right);
        return result;
    }

    public boolean squareHasAdjascentHorizontalLetter(int row, int col) {
        boolean result = false;
        char above = getValue(row, col - 1);
        char below = getValue(row, col + 1);
        result = result || LetterScores.isValidLetter(above);
        result = result || LetterScores.isValidLetter(below);
        return result;
    }

    public boolean squareHasAdjascentLetter(int row, int col) {
        return squareHasAdjascentHorizontalLetter(row, col) ||
                squareHasAdjascentVerticalLetter(row, col);
    }

    public List<char[]> getWords(int startRow, int startCol,
            boolean isAcross, char[] word) {
        ArrayList<char[]> result = new ArrayList<char[]>();
        if (isAcross) {
            //look for parallel words
            for (int loc = 0; loc < word.length; loc++) {
                int col = loc + startCol;
                if (getValue(startRow, col) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentVerticalLetter(startRow, loc)) {
                    char[] vWord = getVerticalWordFromSquare(startRow, col, word[loc]);
                    if (vWord.length > 1) {
                        result.add(vWord);
                    }
                }
            }
            //look for words in series
            char[] horizWord = getHorizontalWordFromWordPlacement(startRow, startCol, word);
            if (horizWord.length > 1) {
                result.add(horizWord);
            }
        } else {
            for (int loc = 0; loc < word.length; loc++) {
                int row = loc + startRow;
                if (getValue(row, startCol) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentHorizontalLetter(loc, startCol)) {
                    char[] hWord = getHorizontalWordFromSquare(row, startCol, word[loc]);
                    if (hWord.length > 1) {
                        result.add(hWord);
                    }
                }
            }
            //look for words in series
            char[] vertWord = getVerticalWordFromWordPlacement(startRow, startCol, word);
            if (vertWord.length > 1) {
                result.add(vertWord);
            }
        }
        return result;
    }

    public WordPlacement getWordPlacement(int startRow, int startCol,
            boolean isAcross, char[] word) {
        List<SingleWordOnBoard> words = new ArrayList<SingleWordOnBoard>();
        if (isAcross) {
            //look for parallel words
            for (int loc = 0; loc < word.length; loc++) {
                int col = loc + startCol;
                if (getValue(startRow, col) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentVerticalLetter(startRow, loc)) {
                    char[] letter = new char[1];
                    letter[0] = word[loc];
                    SingleWordOnBoard vWord = getVerticalSingleWordFromWordPlacement(startRow, col, letter);
                    if (vWord.word.length > 1) {
                        words.add(vWord);
                    }
                }
            }
            //look for words in series
            SingleWordOnBoard horizWord = getHorizontalSingleWordFromWordPlacement(startRow, startCol, word);
            if (horizWord.word.length > 1) {
                words.add(horizWord);
            }
        } else {
            for (int loc = 0; loc < word.length; loc++) {
                int row = loc + startRow;
                if (getValue(row, startCol) == LetterScores.EMPTY_SQUARE &&
                        squareHasAdjascentHorizontalLetter(loc, startCol)) {
                    char[] letter = new char[1];
                    letter[0] = word[loc];
                    SingleWordOnBoard hWord = getHorizontalSingleWordFromWordPlacement(row, startCol, letter);
                    if (hWord.word.length > 1) {
                        words.add(hWord);
                    }
                }
            }
            //look for words in series
            SingleWordOnBoard vertWord = getVerticalSingleWordFromWordPlacement(startRow, startCol, word);
            if (vertWord.word.length > 1) {
                words.add(vertWord);
            }
        }
        
        int score = 0;
        for (SingleWordOnBoard swop: words) {
            score += BoardLayout.getWordScore(swop);
        }
        
        
        return new WordPlacement(new TileLine(startRow, startCol, word.length, isAcross),
                words, score);
    }

    public int getScoreFromWord(int row, int col, char[] word) {

        return 0;
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
            result[row - topRow - 1] = possibleChar;
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

        char[] result = getCharLine(row, leftCol + 1,
                rightCol - leftCol - 1, true);
        if (LetterScores.isValidLetter(possibleChar)) {
            result[col - leftCol - 1] = possibleChar;
        }
        return result;
    }

    public char[] getVerticalWordFromWordPlacement(int startRow, int col, char[] letters) {
        int topRow = startRow;
        int bottomRow = startRow + letters.length - 1;

        while (LetterScores.isValidLetter(getValue(--topRow, col))) {
        }

        while (LetterScores.isValidLetter(getValue(++bottomRow, col))) {
        }

        char[] result = getCharLine(topRow + 1, col,
                bottomRow - topRow - 1, false);

        for (int i = 0; i < letters.length; i++) {
            result[startRow - topRow + i - 1] = letters[i];
        }

        return result;
    }

    public char[] getHorizontalWordFromWordPlacement(int row, int startCol, char[] letters) {
        int leftCol = startCol;
        int rightCol = startCol + letters.length - 1;

        while (LetterScores.isValidLetter(getValue(row, --leftCol))) {
        }

        while (LetterScores.isValidLetter(getValue(row, ++rightCol))) {
        }

        char[] result = getCharLine(row, leftCol + 1,
                rightCol - leftCol - 1, true);

        for (int i = 0; i < letters.length; i++) {
            result[startCol - leftCol + i - 1] = letters[i];
        }

        return result;
    }

    public SingleWordOnBoard getVerticalSingleWordFromWordPlacement(int startRow, int col, char[] letters) {
        int topRow = startRow;
        int bottomRow = startRow + letters.length - 1;

        while (LetterScores.isValidLetter(getValue(--topRow, col))) {
        }

        while (LetterScores.isValidLetter(getValue(++bottomRow, col))) {
        }

        char[] result = getCharLine(topRow + 1, col,
                bottomRow - topRow - 1, false);

        boolean[] occupied = new boolean[result.length];
        for (int i = 0; i < result.length; i++) {
            occupied[i] = LetterScores.isValidLetter(this.letters[topRow + i + 1][col]);
        }

        for (int i = 0; i < letters.length; i++) {
            result[startRow - topRow + i - 1] = letters[i];
        }

        return new SingleWordOnBoard(topRow + 1, col, result, false, occupied);
    }

    public SingleWordOnBoard getHorizontalSingleWordFromWordPlacement(int row, int startCol, char[] letters) {
        int leftCol = startCol;
        int rightCol = startCol + letters.length - 1;

        while (LetterScores.isValidLetter(getValue(row, --leftCol))) {
        }

        while (LetterScores.isValidLetter(getValue(row, ++rightCol))) {
        }

        char[] result = getCharLine(row, leftCol + 1,
                rightCol - leftCol - 1, true);

        for (int i = 0; i < letters.length; i++) {
            result[startCol - leftCol + i - 1] = letters[i];
        }

        boolean[] occupied = new boolean[result.length];
        for (int i = 0; i < result.length; i++) {
            occupied[i] = 
                    LetterScores.isValidLetter(this.letters[row][leftCol + i + 1]);
        }

        for (int i = 0; i < letters.length; i++) {
            result[startCol - leftCol + i - 1] = letters[i];
        }

        return new SingleWordOnBoard(row, leftCol + 1, result, true, occupied);
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

    public String getBoardDisplay() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < letters[0].length; i++) {
            sb.append(i);
            sb.append(" ");
        }
        sb.append("\n");
        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[0].length; col++) {
                char c = getValue(row, col);
                if (Character.isLetter(c)) {
                    sb.append(getValue(row, col));
                } else {
                    sb.append(".");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

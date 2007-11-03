/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.awt.Dimension;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class BoardAnagramUtils {

    Square[][] processedBoard;
    Board board;
    private IntHashDictionary dictionary;

    public BoardAnagramUtils(Board board) {
        this.board = board;
        initalizeProcessedBoard();
    }

    public void initalizeProcessedBoard() {
        Dimension d = board.getBoardSize();
        processedBoard = new Square[d.height][];
        
        for (int a = 0; a < processedBoard.length; a++) {
            processedBoard[a] = new Square[d.width];
            for (int i = 0; i < processedBoard[a].length; i++) {
                processedBoard[a][i] = new Square();
            }
        }
    }

    public void generatePossibilities() {
        char[][] letters = board.getLetters();
        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[0].length; col++) {
                if (board.squareHasAdjascentLetter(row, col) &&
                        board.getValue(row, col) == LetterScores.EMPTY_SQUARE) {
                    StringBuffer sb = new StringBuffer(10);
                    for (int i = 0; i < LetterScores.alphabet.length; i++) {
                        char letter = LetterScores.alphabet[i];
                        List<char[]> strings = board.getWordsFromSingleLetterPlay(row,
                                col, letter);
                        boolean letterWorks = true;
                        for (char[] array : strings) {
                            letterWorks = letterWorks && getDictionary().isWord(array);
                        }
                        if (letterWorks) {
                            sb.append(letter);
                        }
                    }
                    processedBoard[row][col].possibilities = sb.toString().toCharArray();
                }
            }
        }
    }

    public IntHashDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(IntHashDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public class Square {

        char[] possibilities;

        public Square() {
            possibilities = new char[0];
        }
    }
}

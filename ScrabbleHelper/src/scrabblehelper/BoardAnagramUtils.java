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
    IntHashDictionary dictionary;

    public BoardAnagramUtils(Board board) {
        this.board = board;
        initalizeProcessedBoard();
    }

    public void initalizeProcessedBoard() {
        Dimension d = board.getBoardSize();
        processedBoard = new Square[d.width][];

        for (Square[] array : processedBoard) {
            for (int i = 0; i < array.length; i++) {
                array[i] = new Square();
            }
        }
    }

    public void generatePossibilities() {
        char[][] letters = board.getLetters();
        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[0].length; col++) {
                if (board.squareHasAdjascentLetter(row, col)) {
                    StringBuffer sb = new StringBuffer(10);
                    for (int i = 0; i < LetterScores.alphabet.length; i++) {
                        char letter = LetterScores.alphabet[i];
                        List<char[]> strings = board.getWordsFromSingleLetterPlay(row,
                                col, letter);
                        boolean letterWorks = true;
                        for (char[] array : strings) {
                            letterWorks = letterWorks && dictionary.isWord(array);
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

    public class Square {

        char[] possibilities;

        public Square() {
            possibilities = new char[0];
        }
    }
}

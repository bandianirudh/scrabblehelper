/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.awt.Dimension;

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
        initalizeProcessedBoard();
        char[][] letters = board.getLetters();
        for (int row = 0; row < letters.length; row++) {
            for (int col = 0; col < letters[0].length; col++) {
                boolean adjascentHorizontal = false;
                boolean adjascentVertical = false;
                Square s = processedBoard[row][col];
                char squareLetter = board.getValue(row, col);

                adjascentHorizontal = board.squareHasAdjascentHorizontalLetter(row, col);
                adjascentVertical = board.squareHasAdjascentVerticalLetter(row, col);
                if (squareLetter == LetterScores.EMPTY_SQUARE && //if it's empty and it either has a horizontal or vertical neighbor
                        (adjascentHorizontal || adjascentVertical)) {
                    StringBuffer vertLetters = new StringBuffer(10);
                    StringBuffer horizLetters = new StringBuffer(10);
                    for (int i = 0; i < LetterScores.alphabet.length; i++) {
                        char letter = LetterScores.alphabet[i];
                        if (adjascentHorizontal &&
                                getDictionary().isWord(board.getHorizontalWordFromSquare(row, col, letter))) {
                            horizLetters.append(letter);
                        }
                        if (adjascentVertical &&
                                getDictionary().isWord(board.getVerticalWordFromSquare(row, col, letter))) {
                            vertLetters.append(letter);
                        }
                    }
                    s.horizontalPossibilities = horizLetters.toString().toCharArray();
                    s.verticalPossibilities = vertLetters.toString().toCharArray();

                    s.horizontalAdjascent = adjascentHorizontal;
                    s.verticalAdjascent = adjascentVertical;
                } else if (LetterScores.isValidLetter(squareLetter)) {
                    s.letter = squareLetter;
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

        boolean verticalAdjascent = false;
        boolean horizontalAdjascent = false;
        char[] horizontalPossibilities = new char[0];
        char[] verticalPossibilities = new char[0];
        char letter = LetterScores.EMPTY_SQUARE;

        public Square() {
        }

        public boolean hasAdjascent() {
            return verticalAdjascent || horizontalAdjascent;
        }

        public boolean isConnectedToRestOfBoard() {
            return hasAdjascent() || LetterScores.isValidLetter(letter);
        }

        public boolean isOccupied() {
            return letter != LetterScores.EMPTY_SQUARE && letter != LetterScores.OUT_OF_BOUNDS;
        }
    }
}

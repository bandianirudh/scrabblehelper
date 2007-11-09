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
public class BoardAnagramUtils {

    Square[][] processedBoard;
    Board board;
    private Rack rack = new Rack();
    private IntHashDictionary dictionary;
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];

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
    //Theoretically Done

    public List<WordPlacement> findAllBoardPossibilities() {
        ArrayList<WordPlacement> result = new ArrayList<WordPlacement>(100);

        char[] sortedRack = rack.letters.clone();
        Arrays.sort(sortedRack);
        
        AnagramPermuter permuter = new AnagramPermuter(sortedRack);
        TileLineFactory t = new TileLineFactory(board);
        
        generatePossibilities();
        
        ArrayList<TileLine> lines = t.generateTileLines(processedBoard);

        
        for (TileLine tl : lines) {
            permuter.set(getOccupiedLetters(tl), getLetterPossibilitiesInLine(tl), tl);
            List<char[]> resultWords = permuter.findValidWords();
            for (char[] word : resultWords) {
                char[] fullWord;
                result.add(new WordPlacement(tl, board.getWords(tl.startRow, tl.startCol, tl.isAcross, word)));
                System.out.println(new String(word));
            }
        }

        return result;
    }

    public char[][] getLetterPossibilitiesInLine(int startRow, int startCol, int length, boolean isAcross) {
        return getLetterPossibilitiesInLine(new TileLine(startRow, startCol, length, isAcross));
    }
    
    public char[][] getLetterPossibilitiesInLine(TileLine line) {
        char[][] result = new char[line.length][];
        int row = line.startRow;
        int col = line.startCol;

        if (line.isAcross) {
            col--;
        } else {
            row--;
        }
        for (int i = 0; i < line.length; i++) {

            if (line.isAcross) {
                col++;
            } else {
                row++;
            }

            Square s = processedBoard[row][col];
            if (s.hasAdjascent()) {
                if (s.isOccupied()) {
                    result[i] = EMPTY_CHAR_ARRAY;
                } else if (!line.isAcross && s.horizontalAdjascent) {
                    result[i] = s.horizontalPossibilities;
                } else if (line.isAcross && s.verticalAdjascent) {
                    result[i] = s.verticalPossibilities;
                } else {
                    result[i] = LetterScores.alphabet;
                }
            } else {
                result[i] = LetterScores.alphabet;
            }
            Arrays.sort(result[i]);
        }
        return result;
    }

    public char[] getOccupiedLetters(TileLine line) {
        char[] result = new char[line.length];
        Arrays.fill(result, LetterScores.EMPTY_SQUARE);
        for (int i = 0; i <
                line.length; i++) {
            int row = line.startRow;
            int col = line.startCol;
            if (line.isAcross) {
                col += i;
            } else {
                row += i;
            }

            Square s = processedBoard[row][col];
            if (s.isOccupied()) {
                result[i] = s.letter;
            }

        }
        return result;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
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

    public class WordPlacement {

        int score;
        private TileLine line;
        private List<char[]> words;

        public WordPlacement(TileLine line, List<char[]> words) {
            this.line = line;
            this.words = words;
        }

        public List<char[]> getWords() {
            return words;
        }

        public void setWords(List<char[]> words) {
            this.words = words;
        }

        public TileLine getLine() {
            return line;
        }

        public void setLine(TileLine line) {
            this.line = line;
        }
    }

    public class AnagramPermuter {

        private char[] rackLetters;
        private char[][] possibilities;
        private char[] occupiedLetters;
        private TileLine tileLine;
        private List<char[]> resultWords;
        private char[] currentWord;
        private boolean[] useable;
        private int currentSquare;

        public AnagramPermuter(char[] rackLetters) {
            this.rackLetters = rackLetters;
        }

        public List<char[]> findValidWords() {
            currentSquare = 0;
            permute();
            return resultWords;
        }

        public void set(char[] occupiedLetters, char[][] possibilities,
                TileLine tileLine) {
            this.occupiedLetters = occupiedLetters;
            this.possibilities = possibilities;
            this.tileLine = tileLine;

            currentWord = new char[tileLine.length];
            useable = new boolean[tileLine.length];
            Arrays.fill(useable, true);
            currentSquare = 0;
            resultWords = new ArrayList<char[]>();
        }

        private void permute() {
            if (currentSquare == getTileLine().length) { //do your thing if it's the last line
                List<char[]> possibleWords = board.getWords(getTileLine().startRow,
                        getTileLine().startCol, getTileLine().isAcross, currentWord);

                //tester:  board.getWords(1, 0, true, "ODE".toCharArray())
                //
                for (char[] possibleWord : possibleWords) {
                    if (!dictionary.isWord(possibleWord)) {
                        return;
                    }
                }
                if (possibleWords.size() == 0) {
                    return;
                }
                getResultWords().add(currentWord);
                board.getWords(getTileLine().startRow,
                        getTileLine().startCol, getTileLine().isAcross, currentWord);
                return;
            } else {
                if (getOccupiedLetters()[currentSquare] != LetterScores.EMPTY_SQUARE) {
                    currentWord[currentSquare] = getOccupiedLetters()[currentSquare];
                    currentSquare++;
                    permute();
                    currentSquare--;
                } else {
                    for (int i = 0; i < rackLetters.length; i++) {
                        if (useable[currentSquare] &&
                                Arrays.binarySearch(getPossibilities()[currentSquare], getRackLetters()[i]) >= 0) {
                            currentWord[currentSquare] = getRackLetters()[i];
                            useable[currentSquare] = false;
                            currentSquare++;
                            permute();
                            currentSquare--;
                            useable[currentSquare] = true;
                        } else if (isUseableBlank(i)) {
                            for (int aLetter = 0; aLetter < LetterScores.alphabet.length; aLetter++) {
                                if (Arrays.binarySearch(getPossibilities()[currentSquare], LetterScores.alphabet[aLetter]) >= 0) {
                                    currentWord[currentSquare] = LetterScores.alphabet[aLetter];
                                    useable[currentSquare] = false;
                                    currentSquare++;
                                    permute();
                                    currentSquare--;
                                    useable[currentSquare] = true;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }

        public boolean containsUseableBlank() {
            for (int i = 0; i < getRackLetters().length; i++) {
                if (isUseableBlank(i)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isUseableBlank(int rackPosition) {
            return getRackLetters()[rackPosition] == LetterScores.UNUSED_BLANK && useable[rackPosition];
        }

        public char[] getRackLetters() {
            return rackLetters;
        }

        public void setRackLetters(char[] rackLetters) {
            this.rackLetters = rackLetters;
        }

        public char[][] getPossibilities() {
            return possibilities;
        }

        public void setPossibilities(char[][] possibilities) {
            this.possibilities = possibilities;
        }

        public char[] getOccupiedLetters() {
            return occupiedLetters;
        }

        public void setOccupiedLetters(char[] occupiedLetters) {
            this.occupiedLetters = occupiedLetters;
        }

        public TileLine getTileLine() {
            return tileLine;
        }

        public void setTileLine(TileLine tileLine) {
            this.tileLine = tileLine;
        }

        public List<char[]> getResultWords() {
            return resultWords;
        }

        public void setResultWords(List<char[]> resultWords) {
            this.resultWords = resultWords;
        }
    }
}

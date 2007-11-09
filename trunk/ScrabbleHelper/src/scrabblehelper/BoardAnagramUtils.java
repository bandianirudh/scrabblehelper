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
        boolean hasBlank = Arrays.binarySearch(rack.letters, LetterScores.UNUSED_BLANK) >= 0;
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

                    int le;
                    if (hasBlank) {
                        le = rack.letters.length;
                    } else {
                        le = LetterScores.alphabet.length;
                    }

                    for (int i = 0; i < le; i++) {
                        char letter;
                        if (hasBlank) {
                            letter = rack.letters[i];
                        } else {
                            letter = LetterScores.alphabet[i];
                        }

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

    public List<WordPlacement> findAllBoardPossibilities() {
        ArrayList<WordPlacement> result = new ArrayList<WordPlacement>(100);

        char[] sortedRack = rack.letters.clone();
        Arrays.sort(sortedRack);

        AnagramPermuter permuter = new AnagramPermuter(sortedRack);
        TileLineFactory t = new TileLineFactory(board);

        generatePossibilities();

        ArrayList<TileLine> lines = t.generateTileLines(processedBoard);

        for (int i = 0; i < lines.size(); i++) {
            TileLine tl = lines.get(i);
            permuter.set(getOccupiedLetters(tl), getLetterPossibilitiesInLine(tl), tl);
            List<char[]> resultWords = permuter.findValidWords();
            for (char[] word : resultWords) {
                char[] fullWord;
                if (tl.isAcross) {
                    fullWord = board.getHorizontalWordFromWordPlacement(tl.startRow, tl.startCol, word);
                } else {
                    fullWord = board.getVerticalWordFromWordPlacement(tl.startRow, tl.startCol, word);
                }
                result.add(new WordPlacement(tl, board.getWords(tl.startRow, tl.startCol, tl.isAcross, word)));
                System.out.println(new String(fullWord));
            }
            System.out.println("TileLine ###: " + i);
        }
        System.out.println("Number of TileLines:  " + lines.size());
        System.out.println("Number of results :  " + result.size());
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

    public class AnagramPermuter {

        private char[] rackLettersReduced;
        private char[][] possibilities;
        private char[] occupiedLetters;
        private TileLine tileLine;
        private List<char[]> resultWords = new ArrayList<char[]>(20);
        private char[] currentWord;
        private int[] frequency;
        private int[] frequencyBackup;
        private int currentSquare;

        public AnagramPermuter(char[] rackLetters) {
            reduceRackLetters(rackLetters);
        }

        public List<char[]> findValidWords() {
            resultWords.clear();
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
            currentSquare = 0;
            resultWords.clear();
            this.frequency = frequencyBackup.clone();
        }

        private void permute() {
            if (currentSquare == tileLine.length) { //do your thing if it's the last square
                List<char[]> possibleWords = board.getWords(tileLine.startRow,
                        tileLine.startCol, tileLine.isAcross, currentWord);

                //tester:  board.getWords(1, 0, true, "ODE".toCharArray())
                //
                
                if (tileLine.length > 1) {
                    if (tileLine.isAcross) {
                        if (!getDictionary().isWord(
                                board.getHorizontalWordFromWordPlacement(
                                tileLine.startRow, tileLine.startCol, currentWord))) {
                            return;
                        }
                    } else {
                        if (!getDictionary().isWord(
                                board.getVerticalWordFromWordPlacement(
                                tileLine.startRow, tileLine.startCol, currentWord))) {
                            return;
                        }
                    }
                }
                
                for (char[] possibleWord : possibleWords) {
                    if (!dictionary.isWord(possibleWord)) {
                        return;
                    }
                }
                if (possibleWords.size() == 0) {
                    return;
                }
                getResultWords().add(currentWord.clone());
                return;
            } else {
                if (occupiedLetters[currentSquare] != LetterScores.EMPTY_SQUARE) {
                    currentWord[currentSquare] = occupiedLetters[currentSquare];
                    currentSquare++;
                    permute();
                    currentSquare--;
                } else {
                    for (int i = 0; i < rackLettersReduced.length; i++) {
                        if (frequency[i] > 0 &&
                                Arrays.binarySearch(possibilities[currentSquare], rackLettersReduced[i]) >= 0) {
                            currentWord[currentSquare] = rackLettersReduced[i];
                            frequency[i]--;
                            currentSquare++;
                            permute();
                            currentSquare--;
                            frequency[i]++;
                        } else if (isUseableBlank(i)) {
                            for (int aLetter = 0; aLetter < LetterScores.alphabet.length; aLetter++) {
                                if (Arrays.binarySearch(possibilities[currentSquare], LetterScores.alphabet[aLetter]) >= 0) {
                                    currentWord[currentSquare] = LetterScores.alphabet[aLetter];
                                    frequency[i]--;
                                    currentSquare++;
                                    permute();
                                    currentSquare--;
                                    frequency[i]++;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }

        public boolean containsUseableBlank() {
            for (int i = 0; i < rackLettersReduced.length; i++) {
                if (isUseableBlank(i)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isUseableBlank(int rackPosition) {
            return rackLettersReduced[rackPosition] == LetterScores.UNUSED_BLANK && frequency[rackPosition] > 0;
        }

        public List<char[]> getResultWords() {
            return resultWords;
        }

        public void setResultWords(List<char[]> resultWords) {
            this.resultWords = resultWords;
        }

        private void reduceRackLetters(char[] rackLetters) {
            ArrayList<Character> tempHolder = new ArrayList<Character>();
            ArrayList<Integer> freqHolder = new ArrayList<Integer>();
            for (Character c : LetterScores.alphabet) {
                int freq = 0;
                for (int i = 0; i < rackLetters.length; i++) {
                    if (rackLetters[i] == c.charValue()) {
                        freq++;
                    }
                }
                if (freq > 0) {
                    tempHolder.add(c);
                    freqHolder.add(freq);
                }
            }
            for (int i = 0; i < rackLetters.length; i++) {
                int freq = 0;
                if (rackLetters[i] == LetterScores.UNUSED_BLANK) {
                    freq++;
                }
                if (freq > 0) {
                    tempHolder.add(LetterScores.UNUSED_BLANK);
                    freqHolder.add(freq);
                }
            }
            char[] rackLettersReduced1 = new char[tempHolder.size()];
            for (int i = 0; i < tempHolder.size(); i++) {
                rackLettersReduced1[i] = tempHolder.get(i);
            }
            this.rackLettersReduced = rackLettersReduced1;

            int[] frequency1 = new int[freqHolder.size()];
            for (int i = 0; i < freqHolder.size(); i++) {
                frequency1[i] = freqHolder.get(i);
            }
            this.frequencyBackup = frequency1;
            this.frequency = this.frequencyBackup.clone();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Administrator
 */
public class BoardTest extends TestCase {

    public BoardTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of initializeLetters method, of class Board.
     */
    public void testInitializeLetters() {
        System.out.println("initializeLetters");
        Board instance = BoardFactory.getSampleBoard();
        instance.initializeLetters();
    }

    /**
     * Test of getCharLine method, of class Board.
     */
    public void testGetCharLine() {
        System.out.println("getCharLine");
        int startRow = 0;
        int startCol = 0;
        int length = 12;
        boolean isAcross = true;
        Board instance = BoardFactory.getSampleBoard();
        char[] expResult = "DEFENESTRATE".toCharArray();
        char[] result = instance.getCharLine(startRow, startCol, length, isAcross);
        System.out.println("Result exp:  " + new String(expResult));
        System.out.println("Result act:  " + new String(result));
        assertEquals(new String(expResult), new String(result));
    }


    /**
     * Test of getValue method, of class Board.
     */
    public void testGetValue() {
        System.out.println("getValue");
        int row = 0;
        int col = 0;
        Board instance = BoardFactory.getSampleBoard();
        char expResult = 'D';
        char result = instance.getValue(0, 0);
        assertEquals(expResult, result);
    }

    /**
     * Test of squareHasAdjascentVerticalLetter method, of class Board.
     */
    public void testSquareHasAdjascentVerticalLetter() {
        System.out.println("squareHasAdjascentVerticalLetter");
        int row = 8;
        int col = 1;
        Board instance = BoardFactory.getSampleBoard();
        boolean expResult = true;
        boolean result = instance.squareHasAdjascentVerticalLetter(row, col);
        assertEquals(expResult, result);

        row = 9;
        expResult = false;
        result = instance.squareHasAdjascentVerticalLetter(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of squareHasAdjascentHorizontalLetter method, of class Board.
     */
    public void testSquareHasAdjascentHorizontalLetter() {
        System.out.println("squareHasAdjascentHorizontalLetter");
        int row = 7;
        int col = 2;
        Board instance = BoardFactory.getSampleBoard();
        boolean expResult = true;
        boolean result = instance.squareHasAdjascentHorizontalLetter(row, col);
        assertEquals(expResult, result);

        col = 3;
        expResult = false;
        result = instance.squareHasAdjascentHorizontalLetter(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of squareHasAdjascentLetter method, of class Board.
     */
    public void testSquareHasAdjascentLetter() {
        System.out.println("squareHasAdjascentLetter");
        int row = 2;
        int col = 2;
        Board instance = BoardFactory.getSampleBoard();
        boolean expResult = true;
        boolean result = instance.squareHasAdjascentLetter(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getWords method, of class Board.
     */
    public void testGetWords() {
        System.out.println("getWords");
        int startRow = 1;
        int startCol = 11;
        boolean isAcross = true;
        char[] word = "SCALDING".toCharArray();
        Board instance = BoardFactory.getSampleBoard();
        List<char[]> result = new ArrayList<char[]>();
        for (int i = 0; i < 1000; i++) {
            result = instance.getWords(startRow, startCol, isAcross, word);
        }
        ArrayList<String> conv = new ArrayList<String>();
        for (char[] c : result) {
            conv.add(new String(c));
        }

        assertTrue(conv.contains("ES"));
        assertTrue(conv.contains("SCALDING"));
        
        System.out.println("BOARD");
        System.out.println(instance.getBoardDisplay());
    }

    /**
     * Test of getWordsFromSingleLetterPlay method, of class Board.
     */
    public void testGetWordsFromSingleLetterPlay() {
        System.out.println("getWordsFromSingleLetterPlay");
        int row = 1;
        int col = 0;
        char letter = 'O';
        Board instance = BoardFactory.getSampleBoard();
        List<char[]> result = instance.getWordsFromSingleLetterPlay(row, col, letter);
        
        
        ArrayList<String> conv = new ArrayList<String>();
        for (char[] c : result) {
            conv.add(new String(c));
        }
        assertTrue(conv.contains("DO"));
        assertTrue(conv.contains("ON"));
    }

    /**
     * Test of getVerticalWordFromSquare method, of class Board.
     */
    public void testGetVerticalWordFromSquare() {
        System.out.println("getVerticalWordFromSquare");
        int row = 8;
        int col = 1;
        char possibleChar = 'S';
        Board instance = BoardFactory.getSampleBoard();
        String expResult = "ENVISIONS";
        char[] result = instance.getVerticalWordFromSquare(row, col, possibleChar);
        assertEquals(expResult, new String(result));
    }

    /**
     * Test of getHorizontalWordFromSquare method, of class Board.
     */
    public void testGetHorizontalWordFromSquare() {
        System.out.println("getHorizontalWordFromSquare");
        int row = 0;
        int col = 12;
        char possibleChar = 'S';
        Board instance = BoardFactory.getSampleBoard();
        String expResult = "DEFENESTRATES";
        char[] result = instance.getHorizontalWordFromSquare(row, col, possibleChar);
        assertEquals(expResult, new String(result));
    }

    /**
     * Test of getBoardSize method, of class Board.
     */
    public void testGetBoardSize() {
        System.out.println("getBoardSize");
        Board instance = BoardFactory.getSampleBoard();
        Dimension expResult = new Dimension(15, 15);
        Dimension result = instance.getBoardSize();
        assertEquals(expResult, result);
    }
}

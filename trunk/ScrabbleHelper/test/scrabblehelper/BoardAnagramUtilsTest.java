/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Administrator
 */
public class BoardAnagramUtilsTest extends TestCase {

    
    public static IntHashDictionary ihd;
    static {
        ihd = new IntHashDictionary();
    }

    public BoardAnagramUtilsTest(String testName) {
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
     * Test of initalizeProcessedBoard method, of class BoardAnagramUtils.
     */
    /*  public void testInitalizeProcessedBoard() {
        System.out.println("initalizeProcessedBoard");
        BoardAnagramUtils instance = new BoardAnagramUtils(BoardFactory.getSampleBoard());
        instance.initalizeProcessedBoard();
    }/*

    /**
     * Test of generatePossibilities method, of class BoardAnagramUtils.
     */
    /* public void testGeneratePossibilities() {
        System.out.println("generatePossibilities");
        BoardAnagramUtils instance = new BoardAnagramUtils(BoardFactory.getSampleBoard());
        instance.setDictionary(ihd);
        instance.generatePossibilities();
    }*/
    public void testFindAllBoardPossibilities() {
        System.out.println("findAllBoardPossibilities");
        BoardAnagramUtils instance = new BoardAnagramUtils(BoardFactory.getSampleBoard());
        instance.setDictionary(ihd);
        System.out.println(BoardFactory.getSampleBoard().getBoardDisplay());
        Arrays.sort(LetterScores.allLetters);
        Rack r = new Rack();
        r.letters = "SELSOND".toCharArray();
        instance.setRack(r);
        List<WordPlacement> result = instance.findAllBoardPossibilities();
        System.out.println(result.size());
    }
    public void testPermuter() {
        System.out.println("findAllBoardPossibilities");
        BoardAnagramUtils instance = new BoardAnagramUtils(BoardFactory.getSampleBoard());
        instance.setDictionary(ihd);
        Rack r = new Rack();
        r.letters = "SECRETS".toCharArray();
        instance.setRack(r);
    }

    public void testGetLetterPossibilitiesInLine() {
        System.out.println("testGeneratePossibilities");
        BoardAnagramUtils instance = new BoardAnagramUtils(BoardFactory.getSmallSample());
        instance.setDictionary(ihd);
        System.out.println(BoardFactory.getSmallSample().getBoardDisplay());
        Rack r = new Rack();
        r.letters = "MISSALT".toCharArray();
        instance.generatePossibilities();
        char[][] result = instance.getLetterPossibilitiesInLine(1, 0, 4, false);
        System.out.println(result[0][0]);
    }
}

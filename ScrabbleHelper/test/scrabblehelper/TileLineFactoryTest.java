/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import java.util.ArrayList;
import junit.framework.TestCase;
import scrabblehelper.BoardAnagramUtils.Square;

/**
 *
 * @author Nick
 */
public class TileLineFactoryTest extends TestCase {
    public static IntHashDictionary ihd;
    static {
        ihd = new IntHashDictionary();
        System.out.println(BoardFactory.getSampleBoard().getBoardDisplay());
    }

    public TileLineFactoryTest(String testName) {
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
     * Test of generateTileLines method, of class TileLineFactory.
     */
    public void testGenerateTileLines() {
        System.out.println("generateTileLines");
        Board b = BoardFactory.getSampleBoard();
        BoardAnagramUtils bau = new BoardAnagramUtils(b);
        bau.setDictionary(ihd);
        bau.generatePossibilities();

        Square[][] processedBoard = bau.processedBoard;
        TileLineFactory instance = new TileLineFactory(b);

        ArrayList<TileLine> result = instance.generateTileLines(processedBoard);
        assertTrue(result.size() > 0);
    }
}

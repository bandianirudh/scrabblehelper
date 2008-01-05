/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbletools;

import junit.framework.TestCase;

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
}

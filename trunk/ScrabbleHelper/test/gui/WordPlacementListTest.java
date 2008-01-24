/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import junit.framework.TestCase;

/**
 *
 * @author Nick
 */
public class WordPlacementListTest extends TestCase {
    
    public WordPlacementListTest(String testName) {
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
     * Test of getModel method, of class WordPlacementList.
     */
    public void testGetModel() {
        System.out.println("getModel");
        WordPlacementList instance = new WordPlacementList();
        WordPlacementListModel expResult = null;
        WordPlacementListModel result = instance.getModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    public void testInstantiate() {
        new WordPlacementList();
    }

}

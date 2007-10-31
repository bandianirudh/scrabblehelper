/*
 * BruteAnagramTest.java
 * JUnit based test
 *
 * Created on September 12, 2007, 6:02 PM
 */

package scrabblehelper;

import java.util.ArrayList;
import junit.framework.*;
import java.util.List;

/**
 *
 * @author Nick
 */
public class BruteAnagramTest extends TestCase {
    
    public static final int TESTREPS = 1;
    public static IntHashDictionary ihd;
    public static IndexDictionary id;
    
    
    public BruteAnagramTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        ihd = new IntHashDictionary();
        id = new IndexDictionary();
    }
    
    protected void tearDown() throws Exception {
    }
    public static final String LONG_WORD = "ULIIWOR";
    /**
     * Test of getAnagrams method, of class scrabblehelper.BruteAnagram.
     */
    public void testGetAnagrams() {
        System.out.println("getAnagrams");
        
        BruteAnagram ba = new BruteAnagram(new IndexDictionary());
        
        //List<Word> result = ba.getAnagramWords("THIEFAV");
        
        //for (Word w: result) System.out.println(w.toString());
    }
    
    public void testIndexDictionary() {
        long time = System.currentTimeMillis();
        System.out.println("testIndexDictionary");
        
        BruteAnagram ba = new BruteAnagram(id);
        
        List<Word> result = new ArrayList<Word>();
        
        for (int i = 0; i < TESTREPS; i++) {
            //result = ba.getAnagramWords(LONG_WORD);
        }
        
        //for (Word w: result) System.out.println(w.toString());
        System.out.println("-------------------------------------------");
        System.out.println(result.size());
        System.out.println("Time for Index Dict:  " + (System.currentTimeMillis() - time));
        System.out.println("-------------------------------------------");
    }
        
    public void testIntHashDictionary() {
        long time = System.currentTimeMillis();
        System.out.println("testIntHashDictionary");
        
        BruteAnagram ba = new BruteAnagram(ihd);
        
        List<Word> result = new ArrayList<Word>();
        
        for (int i = 0; i < TESTREPS; i++) {
            result = ba.getAnagramWords(LONG_WORD);
        }
        
        for (Word w: result) System.out.println(w.toString());
        System.out.println("-------------------------------------------");
        System.out.println(result.size());
        System.out.println("Time for Int Hash Dict:  " + (System.currentTimeMillis() - time));
        System.out.println("-------------------------------------------");
    }
}

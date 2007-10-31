/*
 * DictionaryTest.java
 * JUnit based test
 *
 * Created on September 11, 2007, 11:27 PM
 */

package scrabblehelper;

import junit.framework.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Nick
 */
public class DictionaryTest extends TestCase {
    
    IndexDictionary instance;
    
    public DictionaryTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        instance = new IndexDictionary();
    }
    
    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of read method, of class scrabblehelper.IndexDictionary.
     */
    public void testRead() {
        System.out.println("read");
        instance = new IndexDictionary();
        for (int i = 0; i < instance.indeces.length; i++) {
            System.out.println(instance.indeces[i][0]);
        }
    }
    
    public void testIsWord() {
        System.out.println("isWord");
        instance = new IndexDictionary();
        long startMillis = System.currentTimeMillis();
        boolean trueResult = true;
        int repeats = 20000;//old 5156 //new
        for (int i = 0; i < repeats; i++)
            trueResult = instance.isWord("QI".toUpperCase().toCharArray());
        boolean falseResult = instance.isWord("VARIATY".toUpperCase().toCharArray());
        System.out.println("Time in millis for " + repeats + " repeats:  " + (System.currentTimeMillis() - startMillis));
        System.out.println("True?? : " + trueResult);
        System.out.println("False?? : " + falseResult);
        assertEquals(true, trueResult);
        assertEquals(false, falseResult);
    }
    
    public void testIsWordTrue() {
        for (int i = 0; i < 10000; i++) {
            instance.isWord("SCLEROSIS".toCharArray());
        }
        
        if (!instance.isWord("SCLEROSIS".toCharArray())) fail();
    }
    
    public void testIsWordFalse() {
        for (int i = 0; i < 10000; i++) {
            instance.isWord("SCLEXOSTX".toCharArray());
        }
        
        if (instance.isWord("SCLEROSTX".toCharArray())) fail();
    }
}

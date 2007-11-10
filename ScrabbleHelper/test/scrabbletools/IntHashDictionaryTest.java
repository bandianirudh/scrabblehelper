/*
 * IntHashDictionaryTest.java
 * JUnit based test
 *
 * Created on September 28, 2007, 4:25 PM
 */

package scrabbletools;

import scrabbletools.IntHashDictionary;
import junit.framework.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Nick
 */
public class IntHashDictionaryTest extends TestCase {
    
    public IntHashDictionaryTest(String testName) {
        super(testName);
    }
    
    public static IntHashDictionary instance;
    
    public void setUp() {
        instance = new IntHashDictionary();
    }
    
    public void test1Read() {
        System.out.println("read");
        
        instance = new IntHashDictionary();
        
        if (instance.ranges.size() != instance.hashes.length) {
            throw new RuntimeException("Ranges and hashes don't match up (r/h): " +
                    instance.ranges.size() + "/" + instance.hashes.length);
        }
    }
    
    public void testIsWordTrue() {
        for (int i = 0; i < 10000; i++) {
            instance.isWord("SCLEROSIS".toCharArray());
        }
        
        if (!instance.isWord("ScLEROSIS".toCharArray())) fail();
    }
    
    public void testIsWordFalse() {
        for (int i = 0; i < 10000; i++) {
            instance.isWord("SCLEXOSTX".toCharArray());
        }
        
        if (instance.isWord("SCLEROSTX".toCharArray())) fail();
    }
    
}

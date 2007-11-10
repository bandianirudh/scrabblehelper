/*
 * IntHashDictionary.java
 *
 * Created on September 28, 2007, 3:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package scrabbletools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Nick
 */
public class IntHashDictionary implements Dictionary {

    public static final File DEFAULT_DICTIONARY_FILE = new File("dictionaries/twl06.txt");
    ArrayList<String> words = new ArrayList<String>();
    int[] hashes = new int[0];
    ArrayList<Range> ranges = new ArrayList<Range>();

    /** Creates a new instance of IntHashDictionary */
    public IntHashDictionary() {
        read("dictionaries/twl06.txt");
        generateIntHashes();
    }

    public IntHashDictionary(String resource) {
        read(resource);
        generateIntHashes();
    }
    
    public IntHashDictionary(URL url) {
        read(url);
        generateIntHashes();
    }

    public void read(String resource) {
        URL url = ClassLoader.getSystemClassLoader().getResource(resource);
        read(url);
    }
    
    public void read(URL url) {
        words = new ArrayList<String>(200000);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String word;
            int i = 0;
            while ((word = reader.readLine()) != null) {
                words.add(word);
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Collections.sort(words);
        System.out.println("Number of words in intHash dictionary: " + words.size());
    }

    public void generateIntHashes() {
        ArrayList<Integer> tempHashArray = new ArrayList<Integer>();

        String word;
        int oldHash = 0;
        int newHash = 0;

        Range currentRange = new Range(0, 0);
        for (int i = 0; i < words.size(); i++) {
            word = words.get(i);
            newHash = getIntHashValue(word);
            if (newHash == oldHash) {
                continue;
            } else {
                currentRange.endIndex = i - 1;
                currentRange = new Range(i, newHash);
                ranges.add(currentRange);
                tempHashArray.add(newHash);
                oldHash = newHash;
            }
        }
        currentRange.endIndex = words.size() - 1;

        Collections.sort(tempHashArray);
        Collections.sort(ranges);

        hashes = new int[tempHashArray.size()];
        for (int i = 0; i < tempHashArray.size(); i++) {
            hashes[i] = tempHashArray.get(i);
        }
    }

    public boolean isWord(char[] word) {
        int hash = getIntHashValue(word);
        int index = Arrays.binarySearch(hashes, hash);
        if (index < 0) {
            return false;
        } else {
            Range r = ranges.get(index);
            String stringWord = String.valueOf(word);
            for (int i = r.startIndex; i <= r.endIndex; i++) {
                if (words.get(i).equalsIgnoreCase(stringWord)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static int getIntHashValue(String word) {
        return getIntHashValue(word.toUpperCase().toCharArray());
    }

    public static int getIntHashValue(char[] word) {
        int result = 0;
        char c;
        for (int i = 0; i < word.length && i < 6; i++) {
            c = word[i];
            result += (LetterScores.getIntFromChar(c) + 1);
            result *= 27;
        }
        return result;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public class Range implements Comparable {

        public int startIndex;
        public int endIndex;
        public int hash;

        public Range(int startIndex, int hash) {
            this.startIndex = startIndex;
            this.hash = hash;
        }

        public int compareTo(Object o) {
            Range r = (Range) o;
            if (this.hash > r.hash) {
                return 1;
            }
            if (this.hash < r.hash) {
                return -1;
            }
            throw new RuntimeException("Equal hashes, for some reason: " + this.hash);
        }
    }
}

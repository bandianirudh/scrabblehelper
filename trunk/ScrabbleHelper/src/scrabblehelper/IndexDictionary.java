/*
 * IndexDictionary.java
 *
 * Created on September 10, 2007, 7:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scrabblehelper;

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
public class IndexDictionary implements Dictionary {
    
    public static final File DEFAULT_DICTIONARY_FILE = new File("C:\\twl06.txt");
    
    char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    ArrayList<Integer> hashes = new ArrayList<Integer>();
    List<char[]> words = new ArrayList<char[]>();
    
    public int[][] indeces = new int[27][];
    
    public int commonLetters = 0;
    
    /**
     * Creates a new instance of IndexDictionary
     */
    public IndexDictionary() {
        System.out.println("IndexDictionary");
        try {
            read(new BufferedReader(new FileReader(DEFAULT_DICTIONARY_FILE)));
            generateAlphabetIndeces();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void read(BufferedReader reader) {
        try {
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word.toCharArray());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(words.size());
    }
    
    private void generateAlphabetIndeces() {
        ListIterator<char[]> it = words.listIterator();
        int currentFirstIndex = 0;
        int currentSecondIndex = 0;
        
        for (int i = 0; i < indeces.length; i++) {
            indeces[i] = new int[27];
            Arrays.fill(indeces[i], -1);
        }
        
        int firstChar;
        int secondChar;
        for (int wordNum = 0; wordNum < words.size(); wordNum++) {
            char[] currentWord = words.get(wordNum);
            
            firstChar = LetterScores.getIntFromChar(currentWord[0]);
            secondChar = LetterScores.getIntFromChar(currentWord[1]);
            
            if (indeces[firstChar][secondChar] < 0) {
                indeces[firstChar][secondChar] = wordNum;
            }
        }
        
        indeces[26][0] = words.size();       
        
        for (int i = 0; i < 26; i++) {
            innerLetter: for (int i1 = 0; i1 < 26; i1++) {
                if (indeces[i + 1][i1] >= 0) {
                    indeces[i][26] = indeces[i + 1][i1];
                    break innerLetter;
                }
            }
        }
    }
    
    public boolean isWord(char[] word) {
        for (int i = 0; i < word.length; i++) {
            word[i] = Character.toUpperCase(word[i]);
        }
        if (word.length < 2) return false;
        
        int startIndex = indeces[LetterScores.getIntFromChar(word[0])][LetterScores.getIntFromChar(word[1])];
        if (startIndex < 0) return false;
        
        int endIndex = indeces[LetterScores.getIntFromChar(word[0])][LetterScores.getIntFromChar(word[1]) + 1];
        if (endIndex < 0) {
            int firstLetter = LetterScores.getIntFromChar(word[0]);
            int secondLetter = LetterScores.getIntFromChar(word[1]);
            for (int second = secondLetter + 1; second < 27; second++) {
                if (indeces[firstLetter][second] >= 0) {
                    endIndex = indeces[firstLetter][second];
                }
            }
            if (endIndex < 0) throw new RuntimeException("Couldn't find decent end index for " + String.valueOf(word));
        }
        
        for (int i = startIndex; i < endIndex; i++) {
            if (Arrays.equals(words.get(i), word)) return true;
        }
        
        return false;
    }
}

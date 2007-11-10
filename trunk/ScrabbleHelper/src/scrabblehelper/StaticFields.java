/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import scrabbletools.Dictionary;

/**
 *
 * @author Nick
 */
public class StaticFields {

    private static Dictionary dictionary;

    public static Dictionary getDictionary() {
        return dictionary;
    }

    public static void setDictionary(Dictionary aDictionary) {
        dictionary = aDictionary;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import scrabbletools.WordPlacement;

/**
 *
 * @author Nick
 */
public interface WordPlacementFilter {
    public boolean isValid(WordPlacement wp);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabbletools;

/**
 *
 * @author Nick
 */
public class SingleWordOnBoard {
        public int startRow;
        public int startCol;
        public boolean isAcross;
        
        public boolean[] occupiedTiles;
        public char[] word;
        
        public int score = 0;
        
        public SingleWordOnBoard(int startRow, int startCol, char[] word,
                boolean isAcross, boolean[] occupiedTiles) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.word = word;
            this.isAcross = isAcross;
            this.occupiedTiles = occupiedTiles;
        }
}

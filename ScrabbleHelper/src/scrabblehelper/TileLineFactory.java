/*
 * TileLineFactory.java
 *
 * Created on September 13, 2007, 5:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package scrabblehelper;

import java.util.ArrayList;
import scrabblehelper.BoardAnagramUtils.Square;

/**
 *
 * @author Nick
 */
public class TileLineFactory {

    Board board;
    /** Creates a new instance of TileLineFactory */

    public TileLineFactory(Board board) {
        this.board = board;
    }

    public ArrayList<TileLine> generateTileLines(Square[][] processedBoard) {
        ArrayList<TileLine> result = new ArrayList<TileLine>(100);

        // scan across
        for (int row = 0; row < processedBoard.length; row++) {  
            boolean rowConnected = false;
            check:
            for (int col = 0; col < processedBoard[0].length; col++) {  // scan row for any worthwhile connected spots
                if (processedBoard[row][col].isConnectedToRestOfBoard()) {
                    rowConnected = true;
                    break check;
                }
            }
            if (!rowConnected) {
                continue;
            }
            for (int startCol = 0; startCol < (processedBoard[0].length - 1); startCol++) {
                if (processedBoard[row][startCol].isOccupied()) {
                    continue;
                }
                int playedTiles = 1;
                boolean isConnected = false;
                endColSearch:  for (int endCol = startCol; endCol < processedBoard[row].length && playedTiles <= 7; endCol++) {
                    Square s = processedBoard[row][endCol];
                    if (s.isOccupied()) {
                        isConnected = true;
                        continue endColSearch;
                    }
                    playedTiles++;
                    if (s.hasAdjascent()) {
                        isConnected = true;
                        if(!s.horizontalAdjascent && s.verticalPossibilities.length == 0) {
                            break endColSearch;
                        }
                    }
                    if (isConnected) {
                        result.add(new TileLine(row, startCol, endCol - startCol + 1, true));
                    }
                }
            }
        }
        
        
        //scan vertically
        for (int col = 0; col < processedBoard[0].length; col++) {
            boolean colConnected = false;
            check:
            for (int row = 0; row < processedBoard.length; row++) {  // scan row for any worthwhile connected spots
                if (processedBoard[row][col].isConnectedToRestOfBoard()) {
                    colConnected = true;
                    break check;
                }
            }
            if (!colConnected) { //if it isn't worthwhile looking, don't
                continue;
            }
            for (int startRow = 0; startRow < (processedBoard.length - 1); startRow++) {
                if (processedBoard[startRow][col].isOccupied()) {
                    continue;
                }
                int playedTiles = 1;
                boolean isConnected = false;
                endRowSearch: for (int endRow = startRow; endRow < processedBoard.length && playedTiles <= 7; endRow++) {
                    Square s = processedBoard[startRow][col];
                    if (s.isOccupied()) {
                        isConnected = true;
                        continue;
                    }
                    playedTiles++;
                    if (s.hasAdjascent()) {
                        isConnected = true;
                        if(!s.verticalAdjascent && s.horizontalPossibilities.length == 0) {
                            break endRowSearch;
                        }
                    }
                    if (isConnected) {
                        result.add(new TileLine(startRow, col, endRow - startRow + 1, false));
                    }
                }
            }
        }

        return result;
    }

    public class TileLine {

        public int startRow;
        public int startCol;
        public int length;
        public boolean isAcross;

        public TileLine(int startRow, int startCol, int length, boolean isAcross) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.length = length;
            this.isAcross = isAcross;
        }
    }
}

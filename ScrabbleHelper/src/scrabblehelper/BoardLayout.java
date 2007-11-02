/*
 * Board.java
 *
 * Created on September 29, 2007, 6:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scrabblehelper;

/**
 *
 * @author Nick
 */


public class BoardLayout {
    public static final char EMPTY_CHARACTER = Character.MIN_VALUE;
    public static final char SINGLE_LETTER = '0';
    public static final char DOUBLE_LETTER = '2';
    public static final char TRIPLE_LETTER = '3';
    public static final char DOUBLE_WORD = '4';
    public static final char TRIPLE_WORD = '5';
    public static final String[] stringBoardValues = {
        "500200050002005",
        "040003000300040",
        "004000202000400",
        "200400020004002",
        "000040000040000",
        "030003000300030",
        "002000202000200",
        "500200040002005",
        "002000202000200",
        "030003000300030",
        "000040000040000",
        "200400020004002",
        "004000202000400",
        "040003000300040",
        "500200050002005"};
    
    
    
    /** Creates a new instance of Board */
    public BoardLayout() {
    }
}

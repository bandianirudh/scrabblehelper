/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

/**
 *
 * @author Administrator
 */
public class BoardFactory {

    public static Board getSampleBoard() {
        Board b = new Board();

        b.setCharLine(0, 1, "ENVISION".toCharArray(), false);
        b.setCharLine(0, 0, "DEFENESTRATE".toCharArray(), true);
        b.setCharLine(5, 0, "DINNER".toCharArray(), true);

        return b;
    }

    public static Board getSmallSample() {
        Board b = new Board();

        b.setCharLine(0, 0, "AXEL".toCharArray(), true);

        return b;
    }
    
    public static Board getRealBoard() {
        Board b = new Board();
        
        b.setCharLine(0, 0, "PLAGUY".toCharArray(), false);
        b.setCharLine(4, 0, "UNROOT".toCharArray(), true);
        b.setCharLine(3, 3, "CODED".toCharArray(), false);
        b.setCharLine(7, 0, "ACED".toCharArray(), true);
        b.setCharLine(7, 1, "CILIA".toCharArray(), false);
        b.setCharLine(11, 1, "AQUAS".toCharArray(), true);
        b.setCharLine(11, 2, "QAT".toCharArray(), false);
        b.setCharLine(11, 5, "SIZE".toCharArray(), false);
        b.setCharLine(14, 4, "SEWN".toCharArray(), true);
        b.setCharLine(8, 4, "VIVAS".toCharArray(), false);
        b.setCharLine(9, 3, "LITIGATE".toCharArray(), true);
        b.setCharLine(10, 8, "HILTED".toCharArray(), true);
        b.setCharLine(11, 10, "DORE".toCharArray(), true);
        b.setCharLine(12, 12, "AFT".toCharArray(), true);
        b.setCharLine(12, 14, "TWO".toCharArray(), false);
        b.setCharLine(7, 5, "JOT".toCharArray(), false);
        b.setCharLine(6, 6, "MAXI".toCharArray(), false);
        b.setCharLine(7, 5, "JAUPS".toCharArray(), true);
        b.setCharLine(3, 9, "BAKES".toCharArray(), false);
        b.setCharLine(3, 9, "BORNE".toCharArray(), true);
        b.setCharLine(3, 10, "OBI".toCharArray(), false);
        b.setCharLine(2, 11, "URARI".toCharArray(), false);
        b.setCharLine(1, 13, "ENERGY".toCharArray(), false);
        b.setCharLine(0, 14, "OF".toCharArray(), false);
        b.setCharLine(6, 14, "EH".toCharArray(), false);
        b.setCharLine(8, 0, "NIM".toCharArray(), false);
        
        
        return b;
    }
}

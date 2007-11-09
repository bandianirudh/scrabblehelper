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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Nick
 */
public class SavedBoard {
    private char[][] board;
    private String rack;
    
    public SavedBoard() {
    }
    
    public SavedBoard(char[][] charArray) {
        setBoard(charArray);
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
    
    public void storeBoard(OutputStream os) {
        XMLEncoder e = new XMLEncoder(os);
        e.writeObject(this);
        e.flush();
        e.close();
    }
    
    public static SavedBoard loadBoard(InputStream is) {
        XMLDecoder d = new XMLDecoder(is);
        Object o = d.readObject();
        if (o instanceof SavedBoard) {
            return (SavedBoard)o;
        } else {
            System.out.println("Load didn't work... returning null");
            return null;
        }
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }
}

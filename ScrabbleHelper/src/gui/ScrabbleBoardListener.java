/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

/**
 *
 * @author Nick
 */
public interface ScrabbleBoardListener {
    public void boardChanged();
    public void tileSelected(VisibleTile vt);
}

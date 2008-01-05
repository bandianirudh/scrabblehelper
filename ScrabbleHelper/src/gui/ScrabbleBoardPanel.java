/*
 * ScrabbleBoardPanel.java
 *
 * Created on November 10, 2007, 2:02 PM
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import scrabbletools.BoardLayout;
import scrabbletools.LetterScores;

/**
 *
 * @author  Nick
 */
public class ScrabbleBoardPanel extends javax.swing.JPanel {

    public static final int ROWS = 15;
    public static final int COLS = 15;
    VisibleTile[][] tiles;
    private boolean moveAcross = true;

    public boolean isMoveAcross() {
        return moveAcross;
    }

    public void setMoveAcross(boolean moveAcross) {
        this.moveAcross = moveAcross;
    }

    public enum Direction {

        LEFT, RIGHT, UP, DOWN
    }
    /** Creates new form ScrabbleBoardPanel */

    public ScrabbleBoardPanel() {
        initComponents();
        GridLayout gl = new GridLayout();
        gl.setColumns(15);
        gl.setRows(15);
        gl.setVgap(0);
        gl.setHgap(0);
        setLayout(gl);
        initializeTiles();
    }

    public void paint(Graphics g) {
        super.paint(g);
        int rowMult = getHeight() / ROWS;
        int colMult = getWidth() / COLS;
        g.setColor(Color.BLACK);
        for (int row = 0; row < (ROWS + 1); row++) {
            int currentY = row * rowMult;
            if (row == ROWS) {
                currentY--;
            }
            g.drawLine(0, currentY, getWidth(), currentY);
        }
        for (int col = 0; col <= COLS; col++) {
            int currentX = col * colMult;
            if (currentX == getWidth()) {
                currentX--;
            }
            g.drawLine(currentX, 0, currentX, getHeight());
        }
    }

    public void initializeTiles() {
        VisibleTile[][] tiles = new VisibleTile[((GridLayout) getLayout()).getRows()][];
        for (int row = 0; row < ((GridLayout) getLayout()).getRows(); row++) {
            tiles[row] = new VisibleTile[((GridLayout) getLayout()).getColumns()];
            for (int col = 0; col < ((GridLayout) getLayout()).getColumns(); col++) {
                tiles[row][col] = new VisibleTile(this,
                        BoardLayout.charBoardValues[row][col],
                        row, col);
                add(tiles[row][col]);
                tiles[row][col].letter = LetterScores.EMPTY_SQUARE;
            }
        }
        this.tiles = tiles;
    }

    public void letterDeleted(VisibleTile tile) {
        if (isMoveAcross()) {
            moveFocus(tile, Direction.LEFT);
        } else {
            moveFocus(tile, Direction.UP);
        }
    }

    public void letterSaved(VisibleTile tile) {

    }

    public void letterEnterred(VisibleTile tile, JTextField editor) {
        if (isMoveAcross()) {
            moveFocus(tile, Direction.RIGHT);
        } else {
            moveFocus(tile, Direction.DOWN);
        }
    }

    public void moveFocus(VisibleTile tile, Direction d) {
        int row = tile.row;
        int col = tile.col;

        if (d == Direction.DOWN) {
            if (!moveAcross) {
                if (row < (ROWS - 1)) {
                    row++;
                } else {
                    tile.stopEditing(null);
                    return;
                }
            } else {
                setMoveAcross(false);
                tile.repaint();
                return;
            }
        } else if (d == Direction.UP) {
            if (!moveAcross) {
                if (row > 0) {
                    row--;
                } else {
                    tile.stopEditing(null);
                    return;
                }
            } else {
                setMoveAcross(false);
                tile.repaint();
                return;
            }

        } else if (d == Direction.LEFT) {
            if (moveAcross) {
                if (col > 0) {
                    col--;
                } else {
                    tile.stopEditing(null);
                    return;
                }
            } else {
                setMoveAcross(true);
                tile.repaint();
                return;
            }
        } else if (d == Direction.RIGHT) {
            if (moveAcross) {
                if (col < (COLS - 1)) {
                    col++;
                } else {
                    tile.stopEditing(null);
                    return;
                }
            } else {
                setMoveAcross(true);
                tile.repaint();
                return;
            }
        }
        tile.stopEditing(null);
        tiles[row][col].edit();
    }

    public char[][] getCharArray() {
        char[][] result = new char[tiles.length][tiles[0].length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                result[row][col] = tiles[row][col].getLetter();
            }
        }
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(450, 450));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}

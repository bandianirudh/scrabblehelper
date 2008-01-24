/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import scrabbletools.BoardLayout;
import scrabbletools.LetterScores;

/**
 *
 * @author Nick
 */
public class VisibleTile extends JPanel implements MouseListener, FocusListener, KeyListener {

    JLabel letterLabel;
    char tileType;
    char letter;
    int col;
    int row;
    ScrabbleBoardPanel panel;
    private boolean editing = false;
    private boolean temporaryDisplay = false;

    public VisibleTile(ScrabbleBoardPanel panel, char tileType, int row, int col) {
        super();


        this.panel = panel;
        this.tileType = tileType;
        this.col = col;
        this.row = row;
        this.letter = LetterScores.EMPTY_SQUARE;

        addMouseListener(this);
        addFocusListener(this);
        addKeyListener(this);
        registerKeys();

        setFocusable(true);

        BorderLayout bl = new BorderLayout();
        setLayout(bl);

        setPreferredSize(new Dimension(15, 15));
        letterLabel = new JLabel(" ", SwingConstants.CENTER) {

            public String getText() {
                return Character.toString(getLetter());
            }
        };
        letterLabel.addMouseListener(this);
        letterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        letterLabel.setVerticalTextPosition(SwingConstants.CENTER);
        letterLabel.setFont(Font.decode("Arial 22"));

        add(letterLabel, BorderLayout.CENTER);
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
        repaint();
    }

    public void stopEditing() {
        setEditing(false);
        repaint();
        panel.letterEditStopped(this);
    }

    public void edit() {
        panel.letterEditStarted(this);
        requestFocus();
        setEditing(true);
        repaint();
        doLayout();
        Rectangle r = getBounds();
        getParent().repaint(r.x, r.y, r.width, r.height);
    }

    public void paintComponent(Graphics g) {
        //First draw the background color
        g.setColor(BoardLayout.getColorFromTileType(tileType));
        g.fillRect(1, 1, getWidth(), getHeight());
        if (isEditing()) {
            //Draw the box for editing
            g.setColor(Color.WHITE);
            g.fillRect(1, 1, getWidth(), getHeight());
            g.setColor(new Color(100, 100, 255, 200));
            g.fillRect(1, 1, getWidth(), getHeight());

            g.setColor(Color.BLACK);
            int lineLength = 5;
            if (panel.isMoveAcross()) {
                g.drawLine(1, getHeight() / 2, 5, getHeight() / 2);
                g.drawLine(getWidth() - lineLength, getHeight() / 2, getWidth(), getHeight() / 2);
            } else {
                g.drawLine(getWidth() / 2, 1, getWidth() / 2, lineLength);
                g.drawLine(getWidth() / 2, getHeight() - lineLength, getWidth() / 2, getHeight());
            }
            letterLabel.paint(g);
        } else if (letter != LetterScores.EMPTY_SQUARE) {
            //If there is a tile here, draw it like a tile.
            Color emptyColor;
            if (!isTemporaryDisplay()) {
                emptyColor = BoardLayout.getColorFromTileType(BoardLayout.SINGLE_LETTER);
            } else {
                emptyColor = new Color(0, 255, 0);  //Show that the tile is temporary...  green should do.
            }
            g.setColor(new Color(emptyColor.getRed(), emptyColor.getGreen(), emptyColor.getBlue(), 160));
            g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
            g.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
            g.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
            g.setColor(new Color(emptyColor.getRed(), emptyColor.getGreen(), emptyColor.getBlue(), 255));
            g.fillRect(3, 3, getWidth() - 5, getHeight() - 5);
            g.setColor(new Color(255, 255, 255, 90));
            g.fillRect(4, 4, getWidth() - 7, getHeight() - 7);
            letterLabel.paint(g);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Rectangle r = getBounds();
        r.setLocation(0, 0);
        if (r.contains(e.getPoint())) {
            edit();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            edit();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public boolean isEditing() {
        return editing;
    }

    private void setEditing(boolean editing) {
        this.editing = editing;
        repaint();
    }

    public void focusGained(FocusEvent e) {
        setEditing(true);
    }

    public void focusLost(FocusEvent e) {
        setEditing(false);
    }

    public void keyTyped(KeyEvent e) {
        char typed = e.getKeyChar();
        if (Character.isLetter(typed)) {
            if (e.isShiftDown()) {
                VisibleTile.this.setLetter(Character.toLowerCase(e.getKeyChar()));
            } else {
                VisibleTile.this.setLetter(Character.toUpperCase(e.getKeyChar()));
            }
            panel.letterEnterred(this);
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void registerKeys() {
        final ScrabbleBoardPanel sbp = this.panel;

        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
                "BACKSPACE");
        getActionMap().put("BACKSPACE", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                VisibleTile.this.setLetter(LetterScores.EMPTY_SQUARE);
                sbp.letterDeleted(VisibleTile.this);
            }
        });

        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
                "DELETE");
        getActionMap().put("DELETE", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                VisibleTile.this.setLetter(LetterScores.EMPTY_SQUARE);
                panel.letterEnterred(VisibleTile.this);
            }
        });

        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                "UP");
        getActionMap().put("UP", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.UP);
            }
        });

        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                "DOWN");
        getActionMap().put("DOWN", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.DOWN);
            }
        });


        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                "LEFT");
        getActionMap().put("LEFT", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.LEFT);
            }
        });

        getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                "RIGHT");
        getActionMap().put("RIGHT", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.RIGHT);
            }
        });
    }

    public boolean isTemporaryDisplay() {
        return temporaryDisplay;
    }

    public void setTemporaryDisplay(boolean temporaryDisplay) {
        this.temporaryDisplay = temporaryDisplay;
    }
}

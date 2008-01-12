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
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
    }

    public void edit() {
        requestFocus();
        setEditing(true);
        repaint();
        if (hasFocus()) {
            System.out.println("hasFocus");
        }

        /*
        editor.setDocument(new PlainDocument() {
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (Arrays.binarySearch(LetterScores.allLetters, str.charAt(0)) >= 0) {// || str.charAt(0) == LetterScores.EMPTY_SQUARE) {
        super.remove(0, super.getLength());
        if (Character.isUpperCase(str.charAt(0))) {
        super.insertString(0, str.toLowerCase(), a);
        } else {
        super.insertString(0, str.toUpperCase(), a);
        }
        if (editor.getText().length() > 0) {
        editor.setSelectionStart(0);
        editor.setSelectionEnd(1);
        }
        if (isEditing() &&
        Character.toUpperCase(str.charAt(0)) != Character.toUpperCase(getLetter())) {
        panel.letterEnterred(VisibleTile.this, editor);
        }
        }
        }; 
        public void removeUpdate(DefaultDocumentEvent chng) {
        VisibleTile.this.repaint();
        super.removeUpdate(chng);
        }
        });
        registerKeys(editor);
        editor.setFont(Font.decode("Arial 22"));
        if (Character.isUpperCase(getLetter())) {
        editor.setText(Character.toString(getLetter()).toLowerCase());
        //System.out.println("Upper");
        } else {
        editor.setText(Character.toString(getLetter()).toUpperCase());
        //System.out.println("Lower");
        }
        if (editor.getText().length() > 0) {
        editor.setSelectionStart(0);
        editor.setSelectionEnd(1);
        }
        editor.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e) {
        }
        public void focusLost(FocusEvent e) {
        if (editor.getText().length() > 0) {
        setLetter(editor.getText().charAt(0));
        }
        stopEditing(editor);
        }
        });
        editor.setHorizontalAlignment(SwingConstants.CENTER);
        editor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        stopEditing(editor);
        }
        });
        //editor.setForeground(new Color(125, 125, 125, 200));
        //editor.setForeground(new Color(125, 125, 125, 200));
        remove(letterLabel);
        add(editor, BorderLayout.CENTER);
        setEditing(true);
        editor.requestFocus();
         */
        repaint();
        doLayout();
        Rectangle r = getBounds();
        getParent().repaint(r.x, r.y, r.width, r.height);
    }

    public void paintComponent(Graphics g) {
        /*
        if (getComponentCount() > 0) {
        super.paintComponent(g);
        }
         */
        g.setColor(BoardLayout.getColorFromTileType(tileType));
        g.fillRect(1, 1, getWidth(), getHeight());
        if (isEditing()) {
            g.setColor(Color.WHITE);
            g.fillRect(1, 1, getWidth(), getHeight());
            letterLabel.paint(g);
            //g.setColor(BoardLayout.getColorFromTileType(tileType));
            //g.fillRect(1, 1, getWidth(), getHeight());
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
        } else if (letter != LetterScores.EMPTY_SQUARE) {
            letterLabel.paint(g);
            Color emptyColor = BoardLayout.getColorFromTileType(BoardLayout.SINGLE_LETTER);
            g.setColor(new Color(emptyColor.getRed(), emptyColor.getGreen(), emptyColor.getBlue(), 160));
            g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
            g.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
            g.fillRect(2, 2, getWidth() - 3, getHeight() - 3);
            g.setColor(new Color(emptyColor.getRed(), emptyColor.getGreen(), emptyColor.getBlue(), 255));
            g.fillRect(3, 3, getWidth() - 5, getHeight() - 5);
            g.setColor(new Color(255, 255, 255, 90));
            g.fillRect(4, 4, getWidth() - 7, getHeight() - 7);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    /*
    if (isEditing() && getComponent(0) != null && getComponent(0) instanceof JTextField) {
    g.setColor(Color.WHITE);
    g.fillRect(1, 1, getWidth(), getHeight());
    letterLabel.paint(g);
    //g.setColor(BoardLayout.getColorFromTileType(tileType));
    //g.fillRect(1, 1, getWidth(), getHeight());
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
    } else if (letter != LetterScores.EMPTY_SQUARE) {
    super.paint(g);
    } else {
    super.paint(g);
    }
     */
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        Rectangle r = getBounds();
        r.setLocation(0, 0);
        if (r.contains(e.getPoint())) {
            edit();
            requestFocus();
        }
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
                //stopEditing(editor);
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
}

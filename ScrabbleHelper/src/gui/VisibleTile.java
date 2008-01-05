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
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import scrabbletools.BoardLayout;
import scrabbletools.LetterScores;

/**
 *
 * @author Nick
 */
public class VisibleTile extends JPanel implements MouseListener {

    JLabel letterLabel;
    char tileType;
    char letter;
    int col;
    int row;
    ScrabbleBoardPanel panel;

    public VisibleTile(ScrabbleBoardPanel panel, char tileType, int row, int col) {
        super();

        addMouseListener(this);

        this.panel = panel;
        this.tileType = tileType;
        this.col = col;
        this.row = row;
        this.letter = LetterScores.EMPTY_SQUARE;

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
    }

    public void edit() {
        final ScrabbleBoardPanel sbp = panel;
        final JTextField editor = new JTextField(Character.toString(getLetter()), 1) {

            public void paint(Graphics g) {
            //g.setFont(Font.decode("Arial 22"));
            //g.drawString(String.valueOf(letter), 0, getHeight());
            }
        };

        editor.setDocument(new PlainDocument() {

            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (Arrays.binarySearch(LetterScores.allLetters, str.charAt(0)) >= 0 || str.charAt(0) == LetterScores.EMPTY_SQUARE) {
                    super.remove(0, super.getLength());
                    super.insertString(0, str.toUpperCase(), a);
                    if (editor.getText().length() > 0) {
                        editor.setSelectionStart(0);
                        editor.setSelectionEnd(1);
                    }
                    if (str.charAt(0) != getLetter()) {
                        panel.letterEnterred(VisibleTile.this, editor);
                    }
                }
            }

            public void removeUpdate(DefaultDocumentEvent chng) {
                VisibleTile.this.repaint();
                super.removeUpdate(chng);
            }
        });
        registerKeys(editor);
        editor.setFont(Font.decode("Arial 22"));
        editor.setText(Character.toString(getLetter()));
        if (editor.getText().length() > 0) {
            editor.setSelectionStart(0);
            editor.setSelectionEnd(1);
        }
        editor.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (editor.getText().length() > 0) {
                    setLetter(editor.getText().toUpperCase().charAt(0));
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
        editor.requestFocus();
        repaint();
        doLayout();
        Rectangle r = getBounds();
        getParent().repaint(r.x, r.y, r.width, r.height);
    }

    public void registerKeys(final JTextField editor) {
        final ScrabbleBoardPanel sbp = panel;

        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
                "BACKSPACE");
        editor.getActionMap().put("BACKSPACE", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                editor.setText(Character.toString(LetterScores.EMPTY_SQUARE));
                stopEditing(editor);
                sbp.letterDeleted(VisibleTile.this);
            }
        });

        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
                "DELETE");
        editor.getActionMap().put("DELETE", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                editor.setText(Character.toString(LetterScores.EMPTY_SQUARE));
            }
        });

        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                "UP");
        editor.getActionMap().put("UP", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.UP);
            }
        });

        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                "DOWN");
        editor.getActionMap().put("DOWN", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.DOWN);
            }
        });


        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                "LEFT");
        editor.getActionMap().put("LEFT", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.LEFT);
            }
        });

        editor.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                "RIGHT");
        editor.getActionMap().put("RIGHT", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                sbp.moveFocus(VisibleTile.this,
                        ScrabbleBoardPanel.Direction.RIGHT);
            }
        });
    }

    public void stopEditing(JTextField editor) {
        if (editor != null) {
            if (editor.getText().length() > 0 && editor.getText().charAt(0) != LetterScores.EMPTY_SQUARE) {
                char l = editor.getText().toUpperCase().charAt(0);
                setLetter(l);
                panel.letterSaved(this);
                for (FocusListener fl : editor.getFocusListeners()) {
                    editor.removeFocusListener(fl);
                }
            } else {
                setLetter(LetterScores.EMPTY_SQUARE);
            }
        }
        removeAll();
        add(letterLabel, BorderLayout.CENTER);
        doLayout();
        repaint();
        Rectangle r = getBounds();
        getParent().repaint(r.x, r.y, r.width, r.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BoardLayout.getColorFromTileType(tileType));
        g.fillRect(1, 1, getWidth(), getHeight());
    }

    public void paint(Graphics g) {
        if (getComponent(0) != null && getComponent(0) instanceof JTextField) {
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
        } else {
            letterLabel.paint(g);
            super.paint(g);
        }
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
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
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
    ScrabbleBoardPanel parent;
    
    JLabel letterLabel;
    char tileType;
    char letter;

    public VisibleTile(char tileType, char letter) {
        super();

        addMouseListener(this);

        this.tileType = tileType;
        this.letter = letter;
        /*
        GridLayout gl = new GridLayout(1, 1, 0, 0);
        setLayout(gl);*/
        
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
        final JTextField editor = new JTextField(Character.toString(getLetter()), 1);
        editor.setDocument(new PlainDocument() {

                    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                        if (Arrays.binarySearch(LetterScores.allLetters, str.charAt(0)) >= 0 || str.charAt(0) == LetterScores.EMPTY_SQUARE) {
                            super.remove(0, super.getLength());
                            super.insertString(0, str.toUpperCase(), a);
                            if (editor.getText().length() > 0) {
                                editor.setSelectionStart(0);
                                editor.setSelectionEnd(1);
                            }
                        }
                    }
                });
        editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
        editor.getActionMap().put("DOWN", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        registerKeyboardAction(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("WORKED");
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), WHEN_FOCUSED);
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
        remove(letterLabel);
        add(editor, BorderLayout.CENTER);
        editor.requestFocus();
        repaint();
        doLayout();
        Rectangle r = getBounds();
        getParent().repaint(r.x, r.y, r.width, r.height);
    }

    public void stopEditing(JTextField editor) {
        if (editor != null) {
            if (editor.getText().length() > 0) {
                char l = editor.getText().toUpperCase().charAt(0);
                setLetter(l);
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
        g.fillRect(0, 0, getWidth(), getHeight());
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

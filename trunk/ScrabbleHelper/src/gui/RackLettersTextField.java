/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.Segment;
import scrabbletools.LetterScores;

/**
 *
 * @author Nick
 */
public class RackLettersTextField extends JTextField implements FocusListener {

    public RackLettersTextField() {
        super.setDocument(new PlainDocument() {

            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                String upCase = str.toUpperCase();
                if (this.getLength() >= 7) {
                    return;
                }
                if (Character.isLetter(upCase.charAt(0)) || str.charAt(0) == LetterScores.UNUSED_BLANK) {
                    super.insertString(offs, upCase, a);
                }
            }
        });

        super.setFont(new java.awt.Font("Tahoma", 0, 42));
        super.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    }

    public void setDocument(Document d) {
    }

    public void setFont(Font f) {
    }

    public void setHorizontalAlignment(int i) {
    }

    public void focusGained(FocusEvent e) {
        repaint();
    }

    public void focusLost(FocusEvent e) {
        repaint();
    }
}

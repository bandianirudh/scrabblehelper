/*
 * ScrabbleWindow.java
 *
 * Created on November 10, 2007, 2:02 PM
 */

package gui;

import java.util.Collections;
import java.util.List;
import scrabblehelper.StaticFields;
import scrabbletools.Board;
import scrabbletools.BoardAnagramUtils;
import scrabbletools.WordPlacement;

/**
 *
 * @author  Nick
 */
public class ScrabbleWindow extends javax.swing.JFrame {
    BoardAnagramUtils utils = new BoardAnagramUtils(new Board());
    
    /** Creates new form ScrabbleWindow */
    public ScrabbleWindow() {
        initComponents();
        utils.setDictionary(StaticFields.getDictionary());
    }
    
    public void setLetters(char[][] letters) {
        utils.getBoard().setLetters(letters);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrabbleBoard = new gui.ScrabbleBoardPanel();
        rackLetterField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultDisplay = new javax.swing.JTextArea();
        anagramButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rackLetterField.setFont(new java.awt.Font("Tahoma", 0, 36));

        jButton1.setText("Find all possiblities ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        resultDisplay.setColumns(20);
        resultDisplay.setRows(5);
        jScrollPane1.setViewportView(resultDisplay);

        anagramButton.setText("Anagram");
        anagramButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anagramButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrabbleBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(anagramButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(rackLetterField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rackLetterField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(anagramButton))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addComponent(scrabbleBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          setLetters(scrabbleBoard.getCharArray());
          utils.setLetters(rackLetterField.getText().toUpperCase().toCharArray());
          long startTime = System.currentTimeMillis();
          List<WordPlacement> words = utils.findAllBoardPossibilities();
          long time = System.currentTimeMillis() - startTime;
          Collections.sort(words);
          resultDisplay.setText("");
          for (WordPlacement wp : words) {
              resultDisplay.append(wp.getLine().toString() + "\n");
              resultDisplay.append(wp.toString() + "\n\n");
          }
          resultDisplay.append("\n\n\nTime to process (in millis):  " + time);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void anagramButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anagramButtonActionPerformed
        new AnagramWindow().setVisible(true);        
}//GEN-LAST:event_anagramButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScrabbleWindow().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anagramButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField rackLetterField;
    private javax.swing.JTextArea resultDisplay;
    private gui.ScrabbleBoardPanel scrabbleBoard;
    // End of variables declaration//GEN-END:variables
    
}
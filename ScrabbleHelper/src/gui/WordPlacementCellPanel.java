/*
 * WordPlacementCellPanel.java
 *
 * Created on January 12, 2008, 8:44 PM
 */
package gui;

import java.awt.Dimension;
import javax.swing.JList;
import scrabbletools.SingleWordOnBoard;
import scrabbletools.TileLine;
import scrabbletools.WordPlacement;

/**
 *
 * @author  Nick
 */
public class WordPlacementCellPanel extends javax.swing.JPanel {

    public static final int HEIGHT_PER_LABEL = 19;
    private boolean selected;
    private WordPlacement wordPlacement;
    /** Creates new form WordPlacementCellPanel */
    private JList list;

    public WordPlacementCellPanel() {
        initComponents();
        setOpaque(true);
    }

    public void setWordPlacement(WordPlacement wp, boolean selected) {
        this.wordPlacement = wp;
        setSelected(selected);
        
        reset();
        
        setWord(String.valueOf(wp.getDisplayedPlacedLetters()));

        TileLine tl = wp.getLine();
        setLocation(tl.startCol + 1, tl.startRow + 1, tl.getDirection());
        setScore(wp.getScore());

        if (isSelected()) {
            int i = 0;
            for (SingleWordOnBoard sw : wp.getWords()) {
                addNewLabel("Word #" + i++ + ": ", String.valueOf(sw.word));
            }
        }

        adjustHeight();
    }

    public void reset() {
        while (getComponentCount() > 3) {
            remove(3);
        }
        adjustHeight();
    }

    public void setScore(int score) {
        scoreLabel.setLabelValue(Integer.toString(score));
    }

    public void setWord(String word) {
        wordLabel.setLabelValue(word);
    }

    public void setLocation(int x, int y, String direction) {
        locationLabel.setLabelValue("(" + x + ", " + y + ") going " + direction);
    }

    public void addNewLabel(String name, String value) {
        //add(new TwoJLabelPanel(name, value));
        //adjustHeight();
    }

    public void adjustHeight() {
        int newHeight = HEIGHT_PER_LABEL * getComponentCount();
        setSize(getWidth(), newHeight);
        setPreferredSize(new Dimension(getWidth(), newHeight));
        setMinimumSize(new Dimension(getWidth(), newHeight));
        doLayout();
    }

    /*public void paintComponents(Graphics g) {
    if (isSelected()) {
    g.setColor(Color.BLUE);
    g.fillRect(0, 0, getWidth(), getHeight());
    }
    super.paintComponents(g);
    }*/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        twoJLabelPanel1 = new gui.TwoJLabelPanel();
        wordLabel = new gui.TwoJLabelPanel(" Word:", "");
        locationLabel = new gui.TwoJLabelPanel(" Location:", "");
        scoreLabel = new gui.TwoJLabelPanel(" Score:", "");

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.GridLayout(0, 1));
        add(wordLabel);
        add(locationLabel);
        add(scoreLabel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.TwoJLabelPanel locationLabel;
    private gui.TwoJLabelPanel scoreLabel;
    private gui.TwoJLabelPanel twoJLabelPanel1;
    private gui.TwoJLabelPanel wordLabel;
    // End of variables declaration//GEN-END:variables
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(getList() != null ? list.getSelectionBackground() : super.getBackground());
            setForeground(getList() != null ? list.getSelectionForeground() : super.getForeground());
        } else {
            setBackground(getList() != null ? list.getBackground() : super.getBackground());
            setForeground(getList() != null ? list.getForeground() : super.getForeground());
        }
        repaint();
    }

    public JList getList() {
        return list;
    }

    public void setList(JList list) {
        this.list = list;
    }

    public WordPlacement getWordPlacement() {
        return wordPlacement;
    }
}

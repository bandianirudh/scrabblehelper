/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import scrabbletools.WordPlacement;

/**
 *
 * @author Nick
 */
public class WordPlacementList extends javax.swing.JList {

    public WordPlacementList() {
        super(new WordPlacementListModel());
        setCellRenderer(new WordPlacementListCellRenderer());
    }

    public WordPlacementListModel getModel() {
        return (WordPlacementListModel) super.getModel();
    }

    public class WordPlacementListCellRenderer implements ListCellRenderer {

        WordPlacementCellPanel panel = new WordPlacementCellPanel();

        public WordPlacementListCellRenderer() {
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof WordPlacement) {

                panel.setList(list);
                WordPlacement wp = (WordPlacement) value;
                panel.setWordPlacement(wp, isSelected);

                return panel;
            } else {
                return new JLabel("Invalid value");
            }
        }
    }
}

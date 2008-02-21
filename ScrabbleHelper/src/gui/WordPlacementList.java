/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import scrabbletools.WordPlacement;

/**
 *
 * @author Nick
 */
public class WordPlacementList extends javax.swing.JList implements ScrabbleBoardListener, ListDataListener {

    public WordPlacementList() {
        super(new WordPlacementListModel());
        setModel(new WordPlacementListModel());
        setCellRenderer(new WordPlacementListCellRenderer());
    }
    
    public WordPlacementList(ListModel alm) {
        this();
        if (alm instanceof WordPlacementListModel) {
            setModel(alm);
        }
    }

    public WordPlacementListModel getModel() {
        return (WordPlacementListModel) super.getModel();
    }
    
    public void setModel(ListModel lm) {
        if(lm instanceof WordPlacementListModel) {
            super.setModel(lm);
            getModel().addListDataListener(this);
        }
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

    public void boardChanged() {
        getModel().clear();
    }

    public void tileSelected(VisibleTile vt) {
        clearSelection();
    }

    public void intervalAdded(ListDataEvent e) {
    }

    public void intervalRemoved(ListDataEvent e) {
    }

    public void contentsChanged(ListDataEvent e) {
        clearSelection();
    }
}

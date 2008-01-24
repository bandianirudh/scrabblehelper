package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import scrabbletools.WordPlacement;

public class WordPlacementListModel implements ListModel {

    public WordPlacementListModel() {
        super();
    }
    private ArrayList<Integer> visibleIndeces = new ArrayList<Integer>();
    private ArrayList<WordPlacement> words = new ArrayList<WordPlacement>();
    private ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();
    private ArrayList<WordPlacementFilter> filters = new ArrayList<WordPlacementFilter>();
    private int maxNumber = 0;

    public int getSize() {
        if (getMaxNumber() > 0) {
            return Math.min(visibleIndeces.size(), getMaxNumber());
        } else {
            return visibleIndeces.size();
        }
    }

    public WordPlacement getElementAt(int index) {
        return words.get(visibleIndeces.get(index));
    }
    
    public void addFilter(WordPlacementFilter wpf) {
        filters.add(wpf);
    }
    
    public void fireCellChange(int index) {
        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index));
        }
    }
    
    public void removeFilters() {
        filters.clear();
    }

    public boolean add(WordPlacement wp) {
        boolean result = words.add(wp);
        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1));
        }
        return result;
    }

    public boolean addAll(List<WordPlacement> wps) {
        int oldSize = getSize();
        boolean result = words.addAll(wps);
        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, oldSize - 1, getSize() - 1));
        }
        applyFilters();
        return result;
    }

    public void applyFilters() {
        visibleIndeces.clear();
        for (int i = 0; i < words.size(); i++) {
            if (isVisibleWordPlacement(words.get(i))) {
                visibleIndeces.add(i);
            }
        }

        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize() - 1));
        }
    }

    private boolean isVisibleWordPlacement(WordPlacement wp) {
        for (WordPlacementFilter wpf : filters) {
            if (!wpf.isValid(wp)) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        int oldSize = words.size();
        visibleIndeces.clear();
        words.clear();
        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, 0, oldSize - 1));
        }
    }

    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
        
        for (ListDataListener ldl : listeners) {
            ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
        }
    }
}

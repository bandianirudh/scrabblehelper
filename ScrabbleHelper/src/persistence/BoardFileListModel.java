/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Nick
 */
public class BoardFileListModel extends DefaultComboBoxModel {

    private List<File> files = null;

    public int getSize() {
        fillList();
        return getFiles().size();
    }

    public Object getElementAt(int index) {
        if (getFiles() == null) {
            return "Click to Load...";
        }
        File f = new File(getFiles().get(index).getPath()) {
            public String toString() {
                return getName();
            }
        };
        return f;
    }
    
    public File getFileAt(int index) {
        return getFiles().get(index);
    }

    public void fillList() {
        setFiles(BoardLoaderAndSaver.getSavedBoards());
    }

    List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
        fireContentsChanged(this, 0, files.size());
    }
}

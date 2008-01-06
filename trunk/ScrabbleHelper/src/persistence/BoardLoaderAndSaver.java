/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class BoardLoaderAndSaver {

    public static List<File> getSavedBoards() {
        File dir = new File("").getAbsoluteFile();
        File[] boards = dir.listFiles(new FileFilter() {

            public boolean accept(File pathname) {
                return pathname.getPath().endsWith(".brd");
            }
            });
        return Arrays.asList(boards);
    }

    public static SavedBoard loadBoard(File f) {
        try {
            return SavedBoard.loadBoard(new BufferedInputStream(new FileInputStream(f)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoardLoaderAndSaver.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void saveBoard(String saveName, SavedBoard sb) {
        try {
            String saveFile = saveName + ".brd";
            File f = new File(saveFile);
            sb.storeBoard(new BufferedOutputStream(new FileOutputStream(f)));
        } catch (Exception ex) {
            Logger.getLogger(BoardLoaderAndSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

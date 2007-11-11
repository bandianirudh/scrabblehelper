/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scrabblehelper;

import gui.ScrabbleWindow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrabbletools.IntHashDictionary;

/**
 *
 * @author Nick
 */
public class Startup {
    public static final String DICTIONARIES_FOLDER = "dictionaries";
    
    public static void startUp() {
        loadResources();
        new ScrabbleWindow().setVisible(true);
    }
    
    public static void loadResources() {
        try {
            URL folder = ClassLoader.getSystemClassLoader().getResource(DICTIONARIES_FOLDER);
            BufferedReader br = new BufferedReader(new InputStreamReader(folder.openStream()));
            ArrayList<String> dictNames = new ArrayList<String>();
            String temp;
            while((temp = br.readLine()) != null) {
                dictNames.add(DICTIONARIES_FOLDER + "/" + temp);
            }
            
            IntHashDictionary d = new IntHashDictionary(dictNames.get(1));
            StaticFields.setDictionary(d);
        } catch (IOException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

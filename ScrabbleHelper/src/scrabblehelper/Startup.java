/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblehelper;

import gui.ErrorWindow;
import gui.LoadingWindow;
import gui.ScrabbleWindow;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import scrabbletools.IntHashDictionary;

/**
 *
 * @author Nick
 */
public class Startup {

    public static final String DICTIONARIES_FOLDER = "dictionaries";

    public Startup() {

    }

    public static void startUp() {
        //new FileTester().setVisible(true);
        try {
            loadResources();
        } catch (Exception ex) {
            new ErrorWindow(ex);
        }

        try {
            new ScrabbleWindow().setVisible(true);
        } catch (Exception ex) {
            new ErrorWindow(ex);
        }
    }

    public static void loadResources() {
        try {
            /*
            InputStream stream =
            ClassLoader.getSystemClassLoader().getResourceAsStream(DICTIONARIES_FOLDER);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            ArrayList<String> dictNames = new ArrayList<String>();
            String temp;
            while ((temp = br.readLine()) != null) {
            dictNames.add(DICTIONARIES_FOLDER + "/" + temp);
            }*/
            try {
                URL url = ClassLoader.getSystemClassLoader().getResource("dictionaries/TWL06.txt");
                LoadingWindow lw = new LoadingWindow();
                lw.setVisible(true);
                IntHashDictionary d = new IntHashDictionary(url);
                lw.dispose();
                StaticFields.setDictionary(d);
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                new ErrorWindow(sw.toString());
            }
        //IntHashDictionary d = new IntHashDictionary(dictNames.get(1));
        //IntHashDictionary d = new IntHashDictionary(new File("C:\\TWL06.txt").toURI().toURL());
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            new ErrorWindow(sw.toString());
        }
    }
}

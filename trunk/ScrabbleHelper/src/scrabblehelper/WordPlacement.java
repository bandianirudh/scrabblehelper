package scrabblehelper;

import java.util.List;

public class WordPlacement {

    int score;
    private TileLine line;
    private List<char[]> words;

    public WordPlacement(TileLine line, List<char[]> words) {
        super();
        this.line = line;
        this.words = words;
    }

    public List<char[]> getWords() {
        return words;
    }

    public void setWords(List<char[]> words) {
        this.words = words;
    }

    public TileLine getLine() {
        return line;
    }

    public void setLine(TileLine line) {
        this.line = line;
    }
}

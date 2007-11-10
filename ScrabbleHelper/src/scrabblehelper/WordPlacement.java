package scrabblehelper;

import java.util.List;

public class WordPlacement implements Comparable {

    int score;
    List<SingleWordOnBoard> words;
    TileLine line;

    public WordPlacement(TileLine line, List<SingleWordOnBoard> words, int score) {
        this.line = line;
        this.words = words;
        this.score = score;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < words.size(); i++) {
            sb.append((i + 1) + ":  ");
            sb.append(words.get(i).word);
            sb.append("\n");
        }
        sb.append("\tScore:  " + score);
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof WordPlacement)) {
            return -1;
        }
        WordPlacement other = (WordPlacement)o;
        int otherScore = other.score;
        if (score < otherScore) {
            return -1;            
        } else if (score > otherScore) {
            return 1;
        } else {
            return 0;
        }
    }
}

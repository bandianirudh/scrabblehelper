package scrabbletools;

import java.util.List;

public class WordPlacement implements Comparable {

    private int score;
    private List<SingleWordOnBoard> words;
    private TileLine line;

    public WordPlacement(TileLine line, List<SingleWordOnBoard> words, int score) {
        this.line = line;
        this.words = words;
        this.score = score;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getWords().size(); i++) {
            sb.append((i + 1) + ":  ");
            sb.append(getWords().get(i).word);
            sb.append("\n");
        }
        sb.append("\tScore:  " + getScore());
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof WordPlacement)) {
            return -1;
        }
        WordPlacement other = (WordPlacement)o;
        int otherScore = other.getScore();
        if (getScore() < otherScore) {
            return -1;            
        } else if (getScore() > otherScore) {
            return 1;
        } else {
            return 0;
        }
    }

    public TileLine getLine() {
        return line;
    }

    public void setLine(TileLine line) {
        this.line = line;
    }

    public

    int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<SingleWordOnBoard> getWords() {
        return words;
    }

    public void setWords(List<SingleWordOnBoard> words) {
        this.words = words;
    }
}

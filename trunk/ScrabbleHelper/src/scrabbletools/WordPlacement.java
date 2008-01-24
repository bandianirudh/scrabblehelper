package scrabbletools;

import java.util.List;

public class WordPlacement implements Comparable {

    private int score;
    private char[] placedLetters;
    private char[] occupied;
    private List<SingleWordOnBoard> words;
    private TileLine line;

    public WordPlacement(TileLine line, char[] occupied, char[] placedLetters, List<SingleWordOnBoard> words, int score) {
        this.line = line;
        this.words = words;
        this.score = score;
        this.placedLetters = placedLetters;
        this.occupied = occupied;
    }
    
    public char[] getDisplayedPlacedLetters() {
        char[] result = new char[placedLetters.length];
        for (int i = 0; i < placedLetters.length; i++) {
            if (LetterScores.isValidLetter(getOccupied()[i])) {
                result[i] = '_';
            } else {
                result[i] = placedLetters[i];
            }
        }
        return result;
    }
    

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Letters Placed:  ");
        sb.append(getDisplayedPlacedLetters());
        sb.append("\n");
        sb.append(line.toString());
        sb.append("\n\nResulting words:\n");
        for (int i = 0; i < getWords().size(); i++) {
            sb.append("      " + (i + 1) + ":  ");
            sb.append(getWords().get(i).word);
            sb.append(" (" + getWords().get(i).score + ")");
            sb.append("\n");
        }
        sb.append("\nTotal score:  " + getScore());
        sb.append("\n.............................................................." +
                "............................................");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof WordPlacement)) {
            return -1;
        }
        WordPlacement other = (WordPlacement)o;
        int otherScore = other.getScore();
        if (getScore() > otherScore) {
            return -1;            
        } else if (getScore() < otherScore) {
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

    public char[] getPlacedLetters() {
        return placedLetters;
    }

    public void setPlacedLetters(char[] placedLetters) {
        this.placedLetters = placedLetters;
    }

    public char[] getOccupied() {
        return occupied;
    }

    public void setOccupied(char[] occupied) {
        this.occupied = occupied;
    }
}

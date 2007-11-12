package scrabbletools;

public class TileLine {

    public int startRow;
    public int startCol;
    public int length;
    public boolean isAcross;

    public TileLine(int startRow, int startCol, int length, boolean isAcross) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.length = length;
        this.isAcross = isAcross;
    }

    public int getRow(int length) {
        int result = startRow;
        if (!isAcross) {
            result += length;
        }
        return result;
    }

    public int getCol(int length) {
        int result = startCol;
        if (isAcross) {
            result += length;
        }
        return result;
    }

    @Override
    public String toString() {
        //String result = "startRow = " + startRow + "; startCol = " + startCol + "; length = " + length + "; isAcross" + isAcross;
        StringBuffer sb = new StringBuffer();
        if (isAcross) {
            sb.append("Going across, ");
        } else {
            sb.append("Going down, ");
        }
        sb.append("starting at:\n");
        sb.append("      Row: " + (startRow + 1));
        sb.append("\n      Column: " + (startCol + 1));
        return sb.toString();
    }
}

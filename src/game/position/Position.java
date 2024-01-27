package game.position;

import game.constant.Constant;

public class Position {

    private int row;
    private int col;

    Position rookPos = null;

    public Position(int row, int col) {
        setRow(row);
        setCol(col);
    }

    public Position(String s) {
        if (s.length() != 2 || 
            !Character.isLetter(s.charAt(0)) || 
            !Character.isDigit(s.charAt(1))) {
            throw new IllegalArgumentException("Invalid position format. Expected format: [a-h][1-8]");
        }

        int colIndex = s.charAt(0) - 'a';
        int rowIndex = s.charAt(1) - '1';

        setRow(rowIndex);
        setCol(colIndex);

        validatePosition();
    }

    public Position(int x, int y, int rX, int rY) {
        this(x, y);
        rookPos = new Position(rX, rY);
    }

    public Position(String s, String r) { // For Castling
        this(s);
        setRookPos(new Position(r));
    }

    private void validatePosition() {
        if (col < 0 || col >= Constant.COL || row < 0 || row >= Constant.ROW) {
            throw new IllegalArgumentException("Position out of bounds.");
        }
    }

    public String toString() {
        return "" + (char) (col + 'a') + (char) (row + '1');
    }

    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Position other = (Position) obj;
        return row == other.getRow() && col == other.getCol();

    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public Position getRookPos() {
        return this.rookPos;
    }

    public void setRookPos(Position rookPos) {
        this.rookPos = rookPos;
    }

}

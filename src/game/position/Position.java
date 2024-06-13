package game.position;

import game.util.Constant;

import java.util.Objects;

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

        Position p = new Position(rowIndex, colIndex);

        setRow(p.getRow());
        setCol(p.getCol());
    }

    public Position(int row, int col, int rRow, int rCol) {
        this(row, col);
        rookPos = new Position(rRow, rCol);
    }

    public Position(String s, String r) { // For Castling
        this(s);
        setRookPos(new Position(r));
    }

    public String toString() {
        return "" + (char) (col + 'a') + (char) (row + '1');
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Position p) {
            return p.row == row && p.col == col;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
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

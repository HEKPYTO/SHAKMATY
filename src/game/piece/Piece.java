package game.piece;

import java.util.ArrayList;

import game.board.Board;
import game.position.Position;

public abstract class Piece {

    private boolean white;
    protected Position pos;
    protected Board board;
    protected ArrayList<Position> legalMove;

    public Piece(boolean isWhite, Position pos) {
        legalMove = new ArrayList<Position>();
        setWhite(isWhite);
        setPos(pos);
    }

    public Piece(boolean isWhite, Position pos, Board board) {
        this(isWhite, pos);
        setBoard(board);
        board.placePiece(this);
    }

    public abstract void legalMove();

    public abstract String toString();

    public boolean isMovable() {
        return !legalMove.isEmpty();
    }

    public boolean isWhite() {
        return this.white;
    }

    public boolean getWhite() {
        return this.white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }


    public Position getPos() {
        return this.pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Position> getLegalMove() {
        return this.legalMove;
    }

    public void setLegalMove(ArrayList<Position> legalMove) {
        this.legalMove = legalMove;
    }

}
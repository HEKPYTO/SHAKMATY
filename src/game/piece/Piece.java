package game.piece;

import game.board.Board;
import game.position.Position;

import java.util.Set;

public abstract class Piece {

    protected boolean white;
    protected boolean moved = false;
    protected Position position;
    protected Board board;

    public Piece(boolean white, Position position, Board board) {
        this.white = white;
        setPosition(position);
        setBoard(board);

        board.placePiece(this, position);
    }

    public abstract Set<Position> getLegalMove();

    public abstract Object deepCopy();

    public void moveHandle(Position to) {
        hadMoved();
        setPosition(to);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + position + ")";
    }

    public void hadMoved() {
        this.moved = true;
    }

    public boolean isMoved() {
        return this.moved;
    }

    public boolean isWhite() {
        return white;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
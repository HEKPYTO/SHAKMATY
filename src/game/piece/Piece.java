package game.piece;

import game.Board.Board;
import game.position.Position;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    protected final boolean white;
    protected boolean moved = false;
    protected Position position;
    protected Board board;
    protected Set<Position> legalMove = new HashSet<Position>();

    public Piece(boolean white, Position position, Board board) {
        this.white = white;
        setPosition(position);
        setBoard(board);

        board.placePiece(this, position);
    }

    public abstract void calculateLegalMove();

    public abstract Object deepCopy();

    public boolean getColor() {
        return white;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece piece) {
            return position.equals(piece.position);
        }

        return false;
    }

    public Set<Position> getNextLegalMove() {
        clearLegalMove();
        calculateLegalMove();

        return legalMove;
    }

    public void moveHandle(Position to) {
        hasMoved();
        setPosition(to);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + position + ")";
    }

    public void hasMoved() {
        this.moved = true;
    }

    public boolean isMoved() {
        return this.moved;
    }

    public boolean isWhite() {
        return white;
    }

    public Position getPosition() {
        return position;
    }

    public Set<Position> getLegalMove() {
        return legalMove;
    }

    public void setLegalMove(Set<Position> legalMove) {
        this.legalMove = legalMove;
    }

    public void clearLegalMove() {legalMove.clear();}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
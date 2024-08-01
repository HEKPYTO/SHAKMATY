package game.piece;


import game.board.Board;
import game.position.Position;
import game.util.Constant;
import game.util.Movement;

import java.util.Set;

public class Pawn extends Piece {

    private boolean passantCaptured = false;

    public Pawn(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement moves = new Movement(position, board);
        moves.singlePawnMove();
        moves.doublePawnMove();
        moves.pawnCaptureMove();
        moves.enPassantMove();

        return moves.getMoves();
    }

    @Override
    public Object deepCopy() {
        Pawn pawn = new Pawn(white, position, board);
        if (moved) hadMoved();
        pawn.setPassantCaptured(canPassantCaptured());

        return pawn;
    }

    public boolean canPassantCaptured() {
        return passantCaptured;
    }

    public void setPassantCaptured(boolean passantCapture) {
        this.passantCaptured = passantCapture;
    }

    public boolean canPromote() {
        return !isWhite() ? position.getRow() == 0 : position.getRow() == Constant.COL - 1;
    }
}

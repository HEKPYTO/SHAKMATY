package game.piece;

import game.board.Board;
import game.constant.Constant;
import game.position.Position;
import game.util.Movement;

public class Pawn extends Piece {

    private boolean passnt = false;

    public Pawn(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    public Pawn(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);
        moves.singlePawnMove();
        moves.doublePawnMove();
        moves.PawnCaptureMove();
        moves.enPassantMove();

        setLegalMove(moves.getMoves());

    }

    @Override
    public String toString() {
        return "Pawn " + pos.toString();
    }

    public boolean isMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isPassant() {
        return this.passnt;
    }

    public void setPassant(boolean passnt) {
        this.passnt = passnt;
    }

    public boolean canPromote() {
        return !isWhite() ? pos.getRow() == 0 : pos.getRow() == Constant.COL - 1;
    }
}

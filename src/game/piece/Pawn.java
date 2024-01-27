package game.piece;

import game.board.Board;
import game.constant.Constant;
import game.position.Position;
import game.util.Movement;

public class Pawn extends Piece {

    private boolean moved = false;
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

        if ((isWhite() && pos.getY() == 1) ||
            (!isWhite() && pos.getY() == Constant.COL - 1)) moves.doublePawnMove();

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

    public boolean isPassnt() {
        return this.passnt;
    }

    public void setPassnt(boolean passnt) {
        this.passnt = passnt;
    }

    public boolean canPromote() {
        return isWhite() ? pos.getY() == 0: pos.getY() == Constant.COL - 1;
    }
}

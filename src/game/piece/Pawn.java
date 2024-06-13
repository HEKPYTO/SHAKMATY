package game.piece;


import game.Board.Board;
import game.position.Position;
import game.util.Constant;
import game.util.Movement;

public class Pawn extends Piece {

    private boolean passantCapture = false;

    public Pawn(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement moves = new Movement(position, board);
        moves.singlePawnMove();
        moves.doublePawnMove();
        moves.pawnCaptureMove();
        moves.enPassantMove();

        setLegalMove(moves.getMoves());

    }

    @Override
    public Object deepCopy() {
        Pawn pawn = new Pawn(white, position, board);
        pawn.setLegalMove(legalMove);
        if (moved) hasMoved();
        pawn.setPassantCapture(canPassantCapture());

        return pawn;
    }

    public boolean canPassantCapture() {
        return passantCapture;
    }

    public void setPassantCapture(boolean passantCapture) {
        this.passantCapture = passantCapture;
    }

    public boolean canPromote() {
        return !isWhite() ? position.getRow() == 0 : position.getRow() == Constant.COL - 1;
    }
}

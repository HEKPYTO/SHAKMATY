package game.piece;

import game.position.Position;
import game.util.Movement;

public class Pawn extends Piece {

    private boolean moved = false;
    private boolean passnt = false;
    
    public Pawn(boolean isWhite, Position pos) {
        super(isWhite, pos);
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


}

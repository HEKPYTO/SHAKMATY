package game.piece;

import game.position.Position;
import game.util.Movement;

public class Bishop extends Piece {
    
    public Bishop(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.crossMove();

        setLegalMove(moves.getMoves());
    }
}

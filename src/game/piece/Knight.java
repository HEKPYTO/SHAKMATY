package game.piece;

import game.position.Position;
import game.util.Movement;

public class Knight extends Piece {
    
    public Knight(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.lShapeMove();

        setLegalMove(moves.getMoves());
    }
}

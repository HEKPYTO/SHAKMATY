package game.piece;

import game.position.Position;
import game.util.Movement;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.diagonalMove();
        moves.crossMove();

        setLegalMove(moves.getMoves());
    }
    
}

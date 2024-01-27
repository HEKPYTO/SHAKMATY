package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

public class Knight extends Piece {
    
    public Knight(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    public Knight(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.lShapeMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public String toString() {
        return "Knight " + pos.toString();
    }
}

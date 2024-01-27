package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    public Queen(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.diagonalMove();
        moves.crossMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public String toString() {
        return "Queen " + pos.toString();
    }
    
}

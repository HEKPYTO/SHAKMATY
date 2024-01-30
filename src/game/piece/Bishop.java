package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.diagonalMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public String toString() {
        return "Bishop " + pos.toString();
    }
}

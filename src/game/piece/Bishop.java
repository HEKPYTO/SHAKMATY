package game.piece;

import game.Board.Board;
import game.position.Position;
import game.util.Movement;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement moves = new Movement(position, board);

        moves.diagonalMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public Object deepCopy() {
        Bishop bishop = new Bishop(white, position, board);
        bishop.setLegalMove(legalMove);
        if (moved) hasMoved();

        return bishop;
    }

}

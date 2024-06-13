package game.piece;

import game.Board.Board;
import game.position.Position;
import game.util.Movement;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement moves = new Movement(position, board);

        moves.plusMove();
        moves.diagonalMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public Object deepCopy() {
        Queen queen = new Queen(white, position, board);
        queen.setLegalMove(legalMove);
        if (moved) hasMoved();

        return queen;
    }
}

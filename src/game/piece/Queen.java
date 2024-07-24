package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

import java.util.Set;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement moves = new Movement(position, board);

        moves.plusMove();
        moves.diagonalMove();

        return moves.getMoves();
    }

    @Override
    public Object deepCopy() {
        Queen queen = new Queen(white, position, board);
        if (moved) hadMoved();

        return queen;
    }
}

package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

import java.util.Set;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement moves = new Movement(position, board);

        moves.diagonalMove();

        return moves.getMoves();
    }

    @Override
    public Object deepCopy() {
        Bishop bishop = new Bishop(white, position, board);
        if (moved) hadMoved();

        return bishop;
    }

}

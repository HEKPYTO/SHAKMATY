package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

import java.util.Set;

public class Knight extends Piece {

    public Knight(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement moves = new Movement(position, board);

        moves.lShapeMove();

        return moves.getMoves();
    }

    @Override
    public Object deepCopy() {
        Knight knight = new Knight(white, position, board);
        if (moved) hadMoved();

        return knight;
    }

}

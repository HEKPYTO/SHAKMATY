package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

import java.util.Set;

public class Rook extends Piece {

    public Rook(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement moves = new Movement(position, board);

        moves.plusMove();

        return moves.getMoves();
        
    }

    @Override
    public Object deepCopy() {
        Rook rook = new Rook(white, position, board);
        if (moved) hadMoved();

        return rook;
    }


}

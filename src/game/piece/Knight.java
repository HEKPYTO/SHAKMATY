package game.piece;

import game.Board.Board;
import game.position.Position;
import game.util.Movement;

public class Knight extends Piece {

    public Knight(boolean isWhite, Position pos, Board board) {
        super(isWhite, pos, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement moves = new Movement(position, board);

        moves.lShapeMove();

        setLegalMove(moves.getMoves());
    }

    @Override
    public Object deepCopy() {
        Knight knight = new Knight(white, position, board);
        knight.setLegalMove(legalMove);
        if (moved) hasMoved();

        return knight;
    }

}

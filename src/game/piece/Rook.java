package game.piece;

import game.Board.Board;
import game.position.Position;
import game.util.Movement;

public class Rook extends Piece {

    public Rook(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement moves = new Movement(position, board);

        moves.plusMove();

        setLegalMove(moves.getMoves());
        
    }

    @Override
    public Object deepCopy() {
        Rook rook = new Rook(white, position, board);
        setLegalMove(legalMove);
        if (moved) hasMoved();

        return rook;
    }


}

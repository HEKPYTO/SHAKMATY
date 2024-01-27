package game.piece;

import game.board.Board;
import game.position.Position;
import game.util.Movement;

public class Rook extends Piece {

    private boolean moved = false;
    
    public Rook(boolean isWhite, Position pos) {
        super(isWhite, pos);
    }

    public Rook(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.squareMove();

        setLegalMove(moves.getMoves());
        
    }

    @Override
    public String toString() {
        return "Rook " + pos.toString();
    }

    public boolean isMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}

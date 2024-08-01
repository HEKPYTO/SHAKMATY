package game.piece;

import java.util.HashSet;
import java.util.Set;

import game.board.Board;
import game.position.Position;
import game.position.TransPosition;
import game.util.Movement;

public class King extends Piece {

    public King(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public Set<Position> getLegalMove() {

        Movement movement = new Movement(position, board);

        movement.squareMove();

        if (!isMoved()) {
            movement.shortCastleMove();
            movement.longCastleMove();
        }

        Set<Position> legalize = new HashSet<Position>();

        for (Position possibleMove: movement.getMoves()) {
            Board vBoard = board.copyBoard();

            vBoard.movePiece(new TransPosition(position, possibleMove));

            Movement checked = new Movement(possibleMove, vBoard);
            if (!checked.isInCheck(isWhite())) legalize.add(possibleMove);
        }

        return legalize;
    }

    @Override
    public Object deepCopy() {
        King king = new King(white, position, board);
        if (moved) hadMoved();

        return king;
    }

    public boolean isInCheck() {
        Movement check = new Movement(position, board);

        return check.isInCheck(isWhite());
    }
}

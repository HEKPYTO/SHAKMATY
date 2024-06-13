package game.piece;

import java.util.HashSet;
import java.util.Set;

import game.Board.Board;
import game.position.Position;
import game.util.Movement;

public class King extends Piece {

    public King(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void calculateLegalMove() {

        Movement move = new Movement(position, board);

        move.squareMove();

        if (!moved) {
            move.shortCastleMove();
            move.longCastleMove();
        }

        Set<Position> legalize = new HashSet<Position>();

        for (Position possibleMove: move.getMoves()) {
            Board virtualBoard = board.copyBoard();
            virtualBoard.movePiece(position, possibleMove);

            Movement checked = new Movement(possibleMove, virtualBoard);
            if (!checked.isInCheck()) legalize.add(possibleMove);
        }

        setLegalMove(legalize);
    }

    @Override
    public Object deepCopy() {
        King king = new King(white, position, board);
        king.setLegalMove(legalMove);
        if (moved) hasMoved();

        return king;
    }

    public boolean isInCheck() {
        Movement check = new Movement(position, board);

        return check.isInCheck();
    }


}

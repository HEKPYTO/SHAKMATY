package game.piece;

import java.util.ArrayList;

import game.board.Board;
import game.position.Position;
import game.util.Checked;
import game.util.Movement;

public class King extends Piece {

    private boolean moved = false;

    public King(boolean isWhite, Position position, Board board) {
        super(isWhite, position, board);
    }

    @Override
    public void legalMove() {

        Movement moves = new Movement(pos, board);

        moves.squareMove();

        if (!moved) {
            moves.shortCastleMove();
            moves.longCastleMove();
        }

        ArrayList<Position> legalize = new ArrayList<Position>();

        for (Position move: moves.getMoves()) {

            Checked check = new Checked(move, board, isWhite());

            if (check.getCheckedPosition().size() == 0) legalize.add(move);
        }

        System.out.println(moves);

        setLegalMove(legalize);
    }

    public ArrayList<Position> getCheckedMove() {
        Checked check = new Checked(pos, board, isWhite());

        return check.getChecked();
    }

    @Override
    public String toString() {
        return "King " + pos.toString();
    }

    public boolean isInChecked() {
        Checked check = new Checked(pos, board, isWhite());

        return check.isInChekced();
    }

    public boolean isInChecked(Position p) {
        Checked check = new Checked(p, board, isWhite());

        return check.isInChekced();
    }

    public boolean isMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }


}

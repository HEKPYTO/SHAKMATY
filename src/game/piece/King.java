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

        ArrayList<Position> legalize = new ArrayList<Position>();

        for (Position p: moves.getMoves()) {
            Checked check = new Checked(p, board);
            if (!check.willBeChecked(p)) legalize.add(p);
        }

        setLegalMove(legalize);
    }

    @Override
    public String toString() {
        return "King " + pos.toString();
    }

    public boolean inChecked() {
        Checked check = new Checked(pos, board);

        return check.getChecked().size() == 0;
    }

    public boolean inChecked(Position p) {
        Checked check = new Checked(p, board);

        return check.getChecked().size() == 0;
    }

    public boolean isMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }


}

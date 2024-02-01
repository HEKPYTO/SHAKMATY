package game.util;

import java.util.ArrayList;

import game.board.Board;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Queen;
import game.piece.Rook;
import game.position.Position;

public class Checked extends Movement {

    private ArrayList<Position> checked;

    public Checked(Position current, Board board) {
        super(current, board);

        checked = new ArrayList<Position>();

        getCheckedMove();
    }

    private void getCheckedMove() {

        Movement moves = new Movement(current, board);

        moves.PawnCaptureMove();
        for (Position p: moves.getMoves()) {
            Piece x = board.getPiece(p);
            if ((x instanceof Pawn) && 
                isDifferentColor(p, current)) checked.add(p);
        }
        moves.clearMoves();

        moves.diagonalMove();
        for (Position p: moves.getMoves()) {
            Piece x = board.getPiece(p);
            if (((x instanceof Rook) || 
                (x instanceof Queen)) &&
                isDifferentColor(p, current)) checked.add(p);
        }
        moves.clearMoves();

        moves.diagonalMove();
        for (Position p: moves.getMoves()) {
            Piece x = board.getPiece(p);

            if (((x instanceof Bishop) ||
                (x instanceof Queen)) &&
                isDifferentColor(p, current)) checked.add(p);
        }
        moves.clearMoves();

        moves.lShapeMove();
        for (Position p: moves.getMoves()) {
            Piece k = board.getPiece(p);

            if ((k instanceof Knight) &&
                isDifferentColor(p, current)) checked.add(p);
        }
        moves.clearMoves();

        moves.squareMove();
        for (Position p: moves.getMoves()) {
            Piece k = board.getPiece(p);

            if ((k instanceof King)) checked.add(p); // there should be only one king
        }
        moves.clearMoves();
    }
    
    public boolean willBeChecked(Position next) {
        Board b = getBoard();
        if (b.getPiece(current) instanceof King) {

            boolean doesMoved = b.movePiece(current, next);

            if (!doesMoved) return false;

            Checked check = new Checked(next, b);
            check.getCheckedMove();

            return check.getChecked().size() == 0;
        }
        return false;
    }


    public Position getCurrent() {
        return this.current;
    }

    public void setCurrent(Position current) {
        this.current = current;
    }

    public ArrayList<Position> getChecked() {
        return this.checked;
    }

    public void setChecked(ArrayList<Position> checked) {
        this.checked = checked;
    }

}

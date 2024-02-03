package game.util;

import java.util.ArrayList;

import game.board.Board;
import game.constant.Constant;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Queen;
import game.piece.Rook;
import game.position.Position;

public class Checked extends Movement {

    private ArrayList<Position> checked;
    private boolean isWhite;

    public Checked(Position current, Board board) {
        super(current, board);
        
        setIsWhite(board.getPiece(current).isWhite());

        clearChecked();

        getChecked();
    }

    public Checked(Position current, Board board, boolean isWhite) {
        super(current, board);
        setIsWhite(isWhite);

        clearChecked();

        getCheckedMove();
    }

    private boolean canGetPieceWithDifferentColor(Position p) {
        return isInBound(p) &&
                board.getPiece(p) != null &&
                board.getPiece(p).isWhite() != this.isWhite;
    }

    private void pawnCheck() {

        int col = current.getCol();
        int row = current.getRow();

        int singleMove = isWhite ? 1 : -1;

        Position pLeft = new Position(row + singleMove, col - 1);
        Position pRight = new Position(row + singleMove, col + 1);

        if (canGetPieceWithDifferentColor(pLeft) &&
                board.getPiece(pLeft) instanceof Pawn)
            checked.add(pLeft);

        if (canGetPieceWithDifferentColor(pRight) &&
                board.getPiece(pRight) instanceof Pawn)
            checked.add(pRight);
    }

    private void diagonalCheck() {

        int row = current.getRow();
        int col = current.getCol();

        for (int s = 0; s < 4; s++) {

            int dx = s < 2 ? 1 : -1;
            int dy = s % 2 == 0 ? 1 : -1;

            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(row + dy * i, col + dx * i);

                if (isInBound(p) &&
                        !isVacantPosition(p)) {

                    if (canGetPieceWithDifferentColor(p) && 
                            (board.getPiece(p) instanceof Bishop ||
                            board.getPiece(p) instanceof Queen))
                        checked.add(p);

                    break;
                }

            }
        }
    }

    private void plusCheck() {

        int row = current.getRow();
        int col = current.getCol();

        for (int s = 0; s < 4; s++) {

            int dx = (int) Math.cos(s * Math.PI / 2);
            int dy = (int) Math.sin(s * Math.PI / 2);

            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(row + dy * i, col + dx * i);

                if (isInBound(p) &&
                        !isVacantPosition(p)) {

                    if (canGetPieceWithDifferentColor(p) && (board.getPiece(p) instanceof Rook ||
                            board.getPiece(p) instanceof Queen))
                        checked.add(p);

                    break;
                }
            }
        }
    }

    private void lCheck() {

        int row = current.getRow();
        int col = current.getCol();

        int[][] kMoves = {
                { -2, -1 }, { -2, 1 },
                { -1, -2 }, { -1, 2 },
                { 1, -2 }, { 1, 2 },
                { 2, -1 }, { 2, 1 }
        };

        for (int[] move : kMoves) {

            Position p = new Position(row + move[0], col + move[1]);

            if (isInBound(p) &&
                    !isVacantPosition(p) &&
                    canGetPieceWithDifferentColor(p) &&
                    board.getPiece(p) instanceof Knight)
                checked.add(p);

        }
    }

    private void squareCheck() {
        int row = current.getRow();
        int col = current.getCol();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {

                Position p = new Position(i, j);

                if (isInBound(p) &&
                        !p.equals(current) &&
                        !isVacantPosition(p) &&
                        canGetPieceWithDifferentColor(p) &&
                        board.getPiece(p) instanceof King)
                    checked.add(p);
            }
        }
    }

    private ArrayList<Position> getCheckedMove() {

        pawnCheck();
        plusCheck();
        diagonalCheck();
        lCheck();
        squareCheck();

        return checked;
    }

    public ArrayList<Position> getCheckedPosition() {
        setChecked(new ArrayList<Position>());
        return getCheckedMove();
    }

    public boolean isInChekced() {
        return getCheckedMove().size() != 0;
    }

    public boolean willBeChecked(Position p) {

        setCurrent(p);

        return getCheckedMove().size() == 0;
    }

    public void clearChecked() {
        checked = new ArrayList<Position>();
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

    public boolean isWhite() {
        return this.isWhite;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

}

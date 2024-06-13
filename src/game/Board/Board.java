package game.Board;

import game.piece.*;
import game.position.Position;
import game.util.Constant;

public class Board {

    private final Piece[][] board;

    public Board() {
        board = new Piece[Constant.COL][Constant.ROW];
    }

    private void setPiece(Piece piece, Position position) {
        board[position.getRow()][position.getCol()] = piece;
    }

    public boolean placePiece(Piece piece, Position position) {
        if (!isEmpty(position)) return false;
        setPiece(piece, position);
        return true;
    }

    public Piece getPiece(Position position) {
        return board[position.getRow()][position.getCol()];
    }

    public boolean movePiece(Position from, Position to) {

        if (!isInBound(from) || !isInBound(to)) {
            return false;
        }

        if (!isSameColor(from, to) || isEmpty(to)) {
            Piece piece = getPiece(from);
            setPiece(piece, to);
            piece.moveHandle(to);
            setPiece(null, from);

            return true;
        }

        return false;
    }

    public boolean isSameColor(Position position1, Position position2) {
        return !isEmpty(position1) && !isEmpty(position2) && getPiece(position1).getColor() == getPiece(position2).getColor();
    }

    public boolean isEmpty(Position position) {
        return board[position.getRow()][position.getCol()] == null;
    }

    public boolean isInBound(Position position) {
        return position.getRow() >= 0 && position.getRow() < Constant.COL && position.getCol() >= 0 && position.getCol() < Constant.COL;
    }

    public String displayBoard() {
        StringBuilder showBoard = new StringBuilder();
        for (int i = Constant.ROW - 1; i >= 0; i--) {

            showBoard.append(i + 1).append(" ");

            for (int j = 0; j < Constant.COL; j++) {
                Piece piece = getPiece(new Position(i, j));

                int icon = 0;
                switch (piece) {
                    case Pawn _ -> icon = 1;
                    case Knight _ -> icon = 2;
                    case Bishop _ -> icon = 3;
                    case Rook _ -> icon = 4;
                    case Queen _ -> icon = 5;
                    case King _ -> icon = 6;

                    case null -> {
                        showBoard.append(Constant.ICONS[(i + j) % 2][icon]);
                        showBoard.append(" ");
                        continue;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + piece);
                }

                showBoard.append(Constant.ICONS[piece.getColor() ? 1: 0][icon]);

                showBoard.append(" ");
            }

            showBoard.append("\n");
        }
        showBoard.append("  a b c d e f g h");

        return showBoard.toString();
    }

    public Board copyBoard() {
        Board newBoard = new Board();
        for (int i = 0; i < Constant.COL; i++) {
            for (int j = 0; j < Constant.ROW; j++) {
                Position position = new Position(i, j);
                Piece piece = getPiece(position);

                if (piece == null) continue;

                Piece newPiece = (Piece) piece.deepCopy();

                newBoard.setPiece(newPiece, position);
                newPiece.setBoard(newBoard);
            }
        }

        return newBoard;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board other) {
            for (int i = 0; i < Constant.COL; i++) {
                for (int j = 0; j < Constant.ROW; j++) {
                    Position position = new Position(i, j);
                    if (getPiece(position) != other.getPiece(position)) return false;
                }
            }
        }

        return true;
    }

    public Piece[][] getBoard() {
        return board;
    }
}

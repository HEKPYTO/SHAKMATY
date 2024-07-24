package game.util;

import game.board.Board;
import game.piece.King;
import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;
import game.position.TransPosition;

public class Checker {
    private Board board;

    public Checker(Board board) {
        setBoard(board);
    }

    public boolean isCheck(boolean color) {
        for (int row = 0; row < Constant.ROW; row++) {
            for (int col = 0; col < Constant.COL; col++) {
                Position position = new Position(row, col);
                if (board.getPiece(position) instanceof King k && k.isWhite() == color && k.isInCheck()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isMate(boolean color) {
        boolean check = isCheck(color);
        if (!check) return false;

        for (int row = 0; row < Constant.ROW; row++) {
            for (int col = 0; col < Constant.COL; col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPiece(position);
                if (piece != null && piece.isWhite() == color) {
                    for (Position nextPos: piece.getLegalMove()) {
                        Board vBoard = board.copyBoard();
                        vBoard.movePiece(new TransPosition(piece.getPosition(), nextPos));
                        if (!(new Checker(vBoard)).isCheck(color)) return false;
                    }
                }
            }
        }

        return true;
    }

    public void disablePassant(boolean color) {
        for (int row = 0; row < Constant.ROW; row++) {
            for (int col = 0; col < Constant.COL; col++) {
                Position position = new Position(row, col);
                if (board.getPiece(position) instanceof Pawn pawn && pawn.isWhite() == color) {
                    pawn.setPassantCapture(false);
                }
            }
        }
    }

    //! IS DRAWN

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

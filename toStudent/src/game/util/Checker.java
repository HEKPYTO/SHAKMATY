package game.util;

import game.board.Board;
import game.piece.*;
import game.position.Position;
import game.position.TransPosition;

import java.util.Iterator;
import java.util.Set;

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
                if (board.isEmpty(position)) continue;
                if (piece.isWhite() == color) {
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
                    pawn.setPassantCaptured(false);
                }
            }
        }
    }

    public boolean isStaleMate(boolean color) {
        for (int row = 0; row < Constant.ROW; row++) {
            for (int col = 0; col < Constant.COL; col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPiece(position);

                if (board.isEmpty(position) || piece.isWhite() != color) continue;

                Set<Position> legalMoves = piece.getLegalMove();

                if (legalMoves.isEmpty()) continue;

                Iterator<Position> iterator = legalMoves.iterator();
                while (iterator.hasNext()) {
                    Position nextPos = iterator.next();
                    Board vBoard = board.copyBoard();
                    vBoard.movePiece(new TransPosition(position, nextPos));
                    if ((new Checker(vBoard)).isCheck(color)) {
                        iterator.remove();
                    }
                }

                if (!legalMoves.isEmpty()) return false;
            }
        }

        return true;
    }

    public boolean isDrawn() {
        int bishopCount = 0;
        int knightCount = 0;
        for (int row = 0; row < Constant.ROW; row++) {
            for (int col = 0; col < Constant.COL; col++) {
                Position position = new Position(row, col);

                if (board.isEmpty(position)) continue;

                Piece piece = board.getPiece(position);

                if (piece instanceof Pawn || piece instanceof Queen || piece instanceof Rook) return false;

                else if (piece instanceof Bishop) bishopCount++;

                else if (piece instanceof Knight) knightCount++;

                if (bishopCount > 1 || knightCount > 1) return false;
            }
        }

        return true;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

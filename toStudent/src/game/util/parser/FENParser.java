package game.util.parser;

import game.board.Board;
import game.piece.*;
import game.position.Position;
import game.util.Constant;

import java.util.Arrays;

import static java.lang.Character.toUpperCase;

public class FENParser {

    private static boolean whiteTurn = true;

    public static String exportToFEN(Board board) { // Not Entirely Correct, but good enough
        StringBuilder fen = new StringBuilder();
        int emptySquareCount = 0;

        for (int i = Constant.ROW - 1; i >= 0; i--) {
            for (int j = 0; j < Constant.COL; j++) {
                Piece piece = board.getPiece(new Position(i, j));

                if (piece == null) emptySquareCount++;
                else {
                    if (emptySquareCount > 0) {
                        fen.append(emptySquareCount);
                        emptySquareCount = 0;
                    }
                    fen.append(getFENPieceSymbol(piece));
                }
            }

            if (emptySquareCount > 0) {
                fen.append(emptySquareCount);
                emptySquareCount = 0;
            }

            if (i > 0) {
                fen.append("/");
            }
        }

        // other attributes
        fen.append(" w - - 0 1"); // simplicity

        return fen.toString();
    }

    protected static char getFENPieceSymbol(Piece piece) {
        char text;
        switch (piece) {
            case Pawn _ -> text = 'p';
            case Knight _ -> text = 'n';
            case Bishop _ -> text = 'b';
            case Rook _ -> text = 'r';
            case Queen _ -> text = 'q';
            case King _ -> text = 'k';

            default -> throw new IllegalStateException("Invalid Piece Type: " + piece);
        }

        return piece.isWhite() ? toUpperCase(text): text;
    }

    public static Board importFromFEN(String fen) {

        Board board = new Board();

        String[] fenParts = fen.split(" ");

        if (fenParts.length < 1) throw new IllegalArgumentException("Insufficient FEN Parts");

        String[] rows = fenParts[0].split("/");

        if (rows.length != Constant.ROW) throw new IllegalArgumentException("row exceed maximum board limits");

        for (int i = 0; i < Constant.ROW; i++) {
            int col = 0;
            for (char c: rows[Constant.ROW - 1 - i].toCharArray()) {
                if (Character.isDigit(c)) {
                    col += Character.getNumericValue(c);
                } else {
                    Position position = new Position(i, col);
                    Piece piece = readPiece(c, i, col, board);

                    if (piece == null) throw new IllegalArgumentException("Wrong piece type");

                    board.placePiece(piece, position);
                    col++;
                }
            }
            if (col != Constant.COL) throw new IllegalArgumentException("column exceed maximum board limits");
        }

        if (fenParts.length >= 2) whiteTurn = fenParts[1].equals("w");

        return board;
    }

    private static Piece readPiece(char c, int row, int col, Board board) {
        Position p = new Position(row, col);
        return switch (c) {
            case 'P' -> new Pawn(true, p, board);
            case 'R' -> new Rook(true, p, board);
            case 'K' -> new King(true, p, board);
            case 'Q' -> new Queen(true, p, board);
            case 'B' -> new Bishop(true, p, board);
            case 'N' -> new Knight(true, p, board);
            case 'p' -> new Pawn(false, p, board);
            case 'r' -> new Rook(false, p, board);
            case 'k' -> new King(false, p, board);
            case 'q' -> new Queen(false, p, board);
            case 'b' -> new Bishop(false, p, board);
            case 'n' -> new Knight(false, p, board);
            default -> null;
        };
    }

    public static Board defaultStartBoard() {
        return importFromFEN(Constant.STARTPOS);
    }

    public static boolean isWhiteTurn() {
        return whiteTurn;
    }

    public static void setWhiteTurn(boolean whiteTurn) {
        FENParser.whiteTurn = whiteTurn;
    }
}

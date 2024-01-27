package game.util;

import game.board.Board;
import game.constant.Constant;
import game.position.Position;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Queen;
import game.piece.Rook;

public class FENConverter {

    private static final int ROW = Constant.ROW;
    private static final int COL = Constant.COL;

    public static String exportToFEN(Board board) {
        StringBuilder fen = new StringBuilder();
        int emptySquareCount = 0;
    
        for (int i = ROW - 1; i >= 0; i--) {
            for (int j = 0; j < COL; j++) {
                Piece p = board.getPiece(new Position(i, j));
    
                if (p == null) {
                    emptySquareCount++;
                } else {
                    if (emptySquareCount > 0) {
                        fen.append(emptySquareCount);
                        emptySquareCount = 0;
                    }
                    fen.append(getFENSymbolForPiece(p));
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
    
        // Add other FEN components (active color, castling rights, en passant square, etc.)
        // For simplicity, let's assume initial values
        fen.append(" w - - 0 1");
    
        return fen.toString();
    }

    private static char getFENSymbolForPiece(Piece p) {
        if (p instanceof Pawn) {
            return p.isWhite() ? 'P' : 'p';
        } else if (p instanceof Bishop) {
            return p.isWhite() ? 'B' : 'b';
        } else if (p instanceof Knight) {
            return p.isWhite() ? 'N' : 'n';
        } else if (p instanceof Rook) {
            return p.isWhite() ? 'R' : 'r';
        } else if (p instanceof Queen) {
            return p.isWhite() ? 'Q' : 'q';
        } else if (p instanceof King) {
            return p.isWhite() ? 'K' : 'k';
        } else {
            throw new IllegalArgumentException("Invalid piece type: " + p.getClass().getSimpleName());
        }
    }

    public static boolean importFromFEN(Board board, String fen) {
        String[] fenParts = fen.split(" ");
    
        if (fenParts.length < 1) {
            return false; // Invalid FEN format
        }
    
        String[] rows = fenParts[0].split("/");
    
        if (rows.length != ROW) {
            return false; // Incorrect number of rows in FEN
        }
    
        for (int i = 0; i < ROW; i++) {
            int col = 0;
            for (char c : rows[ROW - 1 - i].toCharArray()) { // Loop through reversed rows
                if (Character.isDigit(c)) {
                    col += Character.getNumericValue(c);
                } else {
                    Position pos = new Position(i, col); // Use the original row index
                    Piece piece = readPiece(c, i, col, board);
                    if (piece == null) {
                        return false; // Invalid piece symbol in FEN
                    }
                    board.setPiece(pos, piece);
                    col++;
                }
            }
            if (col != COL) {
                return false; // Incorrect number of columns in FEN row
            }
        }
    
        return true;
    }

    private static Piece readPiece(char c, int row, int col, Board board) {
        Position p = new Position(row, col);
        switch (c) {
            case 'P':
                return new Pawn(true, p, board);
            case 'R':
                return new Rook(true, p, board);
            case 'K':
                return new King(true, p, board);
            case 'Q':
                return new Queen(true, p, board);
            case 'B':
                return new Bishop(true, p, board);
            case 'N':
                return new Knight(true, p, board);

            case 'p':
                return new Pawn(false, p, board);
            case 'r':
                return new Rook(false, p, board);
            case 'k':
                return new King(false, p, board);
            case 'q':
                return new Queen(false, p, board);
            case 'b':
                return new Bishop(false, p, board);
            case 'n':
                return new Knight(false, p, board);
            default:
                return null; 
        }
    }
}


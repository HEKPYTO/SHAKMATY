package game.board;

import java.util.ArrayList;

import game.position.Position;
import game.constant.Constant;
import game.constant.PositionString;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Queen;
import game.piece.Rook;

public class Board {

    private static final int ROW = 8;
    private static final int COL = 8;
    private ArrayList<ArrayList<Piece>> board;
    private ArrayList<Position> moves;

    public Board() { // Initialize ROW * COL board
        board = new ArrayList<>(ROW);
        for (int i = 0; i < ROW; i++) {
            ArrayList<Piece> row = new ArrayList<Piece>(COL);
            for (int j = 0; j < COL; j++) {
                row.add(null); // No piece
            }
            board.add(row);
        }
    }

    public static void setValue(ArrayList<ArrayList<Piece>> arr, int row, int col, Piece value) {
        arr.get(ROW - row - 1).set(col, value);
    }

    public void setDefaultPosition() {
        importBoardFromString(PositionString.initPos);
    }

    public boolean canBasicMoveTo(Position pos) {
        if (isValidPosition(pos)) {
            Piece piece = getPiece(pos);

            return piece != null;
        }
        return false;
    }

    public boolean movePiece(Position from, Position to) {
        Piece p = getPiece(from);
        p.legalMove();

        if (p.getLegalMove().contains(to)) {
            if (!isVacantPosition(to)) capture(to);
            setPieceTo(p, to);
            p.legalMove(); // get next legal moves
            return true;
        }

        return false;

    }

    public void capture(Position to) { // TBD
        moves.add(to);
    }

    public boolean movePiece(Piece p, Position to) {

        return movePiece(p.getPos(), to);

    }

    public boolean isValidPosition(Position pos) { // Is in chess square
        return 0 <= pos.getX() && 
            pos.getX() <= ROW &&
            0 <= pos.getY() &&
            pos.getY() <= COL;
    }

    public boolean isVacantPosition(Position pos) { // Is that space free
        return isValidPosition(pos) && getPiece(pos) == null;
    }

    public boolean isSameColorPiece(Position a, Position b) { // Is same Color Piece
        return getPiece(a) != null && getPiece(b) != null && getPiece(a) == getPiece(b);
    } 

    public void placePiece(Piece p) {
        
        setValue(board, p.getPos().getX(), p.getPos().getY(), p);
    }

    public void setPieceTo(Piece p, Position to) {

        int x = p.getPos().getX();
        int y = p.getPos().getY();

        if (p.getBoard() == null) p.setBoard(this);

        setValue(board, x, y, p);
        setValue(board, to.getX(), to.getY(), p);
        setValue(board, x, y, null);
    }

    public Piece getPiece(Position pos) {
        return board.get(pos.getX()).get(pos.getY());
    }

    public Board cloneBoard() {
        Board b = new Board();
    
        b.importFEN(this.exportFEN());

        return b;
    }

    public String exportFEN() {
        StringBuilder fen = new StringBuilder();

        for (int i = ROW - 1; i >= 0; i--) {
            int emptyCount = 0;

            for (int j = 0; j < COL; j++) {
                Piece p = getPiece(new Position(i, j));

                if (p == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }

                    if (p instanceof Pawn) {
                        fen.append(p.isWhite() ? "P" : "p");
                    } else if (p instanceof Bishop) {
                        fen.append(p.isWhite() ? "B" : "b");
                    } else if (p instanceof Knight) {
                        fen.append(p.isWhite() ? "N" : "n");
                    } else if (p instanceof Rook) {
                        fen.append(p.isWhite() ? "R" : "r");
                    } else if (p instanceof Queen) {
                        fen.append(p.isWhite() ? "Q" : "q");
                    } else if (p instanceof King) {
                        fen.append(p.isWhite() ? "K" : "k");
                    }
                }

                if (j == COL - 1 && emptyCount > 0) {
                    fen.append(emptyCount);
                }
            }

            if (i > 0) {
                fen.append('/');
            }
        }

        fen.append(" w - - 0 1");

        return fen.toString();
    }

    public void importFEN(String fen) {
        board = new ArrayList<>(ROW);
        for (int i = 0; i < ROW; i++) {
            ArrayList<Piece> row = new ArrayList<>(COL);
            for (int j = 0; j < COL; j++) {
                row.add(null);
            }
            board.add(row);
        }

        String[] fenParts = fen.split("\\s+");
        String piecePlacement = fenParts[0];

        int rank = ROW - 1;
        int file = 0;

        for (char c : piecePlacement.toCharArray()) {
            if (Character.isDigit(c)) {
                file += Character.getNumericValue(c);
            } else if (c == '/') {
                rank--;
                file = 0;
            } else {
                boolean isWhite = Character.isUpperCase(c);
                Piece piece = createPieceFromFENCharacter(c, isWhite, rank, file);
                setValue(board, rank, file, piece);
                file++;
            }
        }
    }

    private Piece createPieceFromFENCharacter(char c, boolean isWhite, int rank, int file) {
        Position position = new Position(rank, file);
        
        switch (Character.toLowerCase(c)) {
            case 'p':
                return new Pawn(isWhite, position, this);
            case 'n':
                return new Knight(isWhite, position, this);
            case 'b':
                return new Bishop(isWhite, position, this);
            case 'r':
                return new Rook(isWhite, position, this);
            case 'q':
                return new Queen(isWhite, position, this);
            case 'k':
                return new King(isWhite, position, this);
            default:
                return null;
        }
    }

    public String exportBoardToString() {

        StringBuilder s = new StringBuilder();

        for (int i = ROW - 1; i >= 0; i--) {

            for (int j = 0; j < COL; j++) {
                Piece p = getPiece(new Position(i, j));

                if (p instanceof Pawn) {
                    s.append(p.isWhite() ? "P" : "p");
                } else if (p instanceof Bishop) {
                    s.append(p.isWhite() ? "B" : "b");
                } else if (p instanceof Knight) {
                    s.append(p.isWhite() ? "N" : "n");
                } else if (p instanceof Rook) {
                    s.append(p.isWhite() ? "R" : "r");
                } else if (p instanceof Queen) {
                    s.append(p.isWhite() ? "Q" : "q");
                } else if (p instanceof King) {
                    s.append(p.isWhite() ? "K" : "k");
                } else {
                    s.append(".");
                }
            }
        }
    
        return s.toString();
    }

    public boolean importBoardFromString(String s) {
        if (s.length() != ROW * COL) return false;

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                char c = s.charAt(i * COL + j);
                
                Piece p = readPiece(c, i, j);

                if (p == null) continue;
                
                placePiece(p);
            }
        }

        return true;
    }

    private Piece readPiece(char c, int i, int j) {
        Position p = new Position(i, j);
        switch (c) {
            case 'P':
                return new Pawn(true, p, this);
            case 'R':
                return new Rook(true, p, this);
            case 'K':
                return new King(true, p, this);
            case 'Q':
                return new Queen(true, p, this);
            case 'B':
                return new Bishop(true, p, this);
            case 'N':
                return new Knight(true, p, this);

            case 'p':
                return new Pawn(false, p, this);
            case 'r':
                return new Rook(false, p, this);
            case 'k':
                return new King(false, p, this);
            case 'q':
                return new Queen(false, p, this);
            case 'b':
                return new Bishop(false, p, this);
            case 'n':
                return new Knight(false, p, this);
            case '.':
                return null; // Placeholder for an empty square
            default:
                throw new IllegalArgumentException("Invalid character: " + c);
        }
    }

    public String displayBoard() {
        StringBuilder s = new StringBuilder();

        for (int i = ROW - 1; i >= 0; i--) {

            for (int j = 0; j < COL; j++) {
                Piece p = getPiece(new Position(i, j));

                if (p instanceof Pawn) {
                    s.append(p.isWhite() ? "♟" : "♙");
                } else if (p instanceof Bishop) {
                    s.append(p.isWhite() ? "♝" : "♗");
                } else if (p instanceof Knight) {
                    s.append(p.isWhite() ? "♞" : "♘");
                } else if (p instanceof Rook) {
                    s.append(p.isWhite() ? "♜" : "♖");
                } else if (p instanceof Queen) {
                    s.append(p.isWhite() ? "♛" : "♕");
                } else if (p instanceof King) {
                    s.append(p.isWhite() ? "♚" : "♔");
                } else {
                    s.append((i + j) % 2 == 0 ? "□": "■");
                }

                s.append(" ");
            }

            s.append("\n");
        }
    
        return s.toString();
    }


    public ArrayList<ArrayList<Piece>> getBoard() {
        return this.board;
    }

    // public void setBoard(ArrayList<ArrayList<Piece>> board) {
    //     this.board = board;
    // }

    public ArrayList<Position> getMoves() {
        return this.moves;
    }

    public void setMoves(ArrayList<Position> moves) {
        this.moves = moves;
    }

}

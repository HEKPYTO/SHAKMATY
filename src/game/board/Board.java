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

    private static final int ROW = Constant.COL;
    private static final int COL = Constant.ROW;
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
        setMoves(new ArrayList<Position>());
    }

    public void setDefaultPosition() {
        importBoardFromString(PositionString.initPos);
    }

    public void setPiece(int row, int col, Piece piece) {
        board.get(row).set(col, piece);
        if (piece == null)
            return;

        piece.move(new Position(row, col));
    }

    public void setPiece(Position pos, Piece piece) {
        setPiece(pos.getRow(), pos.getCol(), piece);
    }

    public void setPiece(Piece piece) {
        if (piece == null)
            return;
        setPiece(piece.getPos(), piece);
    }

    public Piece getPiece(Position pos) {
        return board.get(pos.getRow()).get(pos.getCol());
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

        if (p.getLegalMove().contains(to)) {
            if (!isVacantPosition(to))
                capture(to);
            else {
                setPieceTo(p, to);
                moves.add(to);
            }


            p.move(to);
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

    private boolean isValidPosition(Position pos) { // Is in chess square
        return 0 <= pos.getCol() &&
                pos.getCol() <= ROW &&
                0 <= pos.getRow() &&
                pos.getRow() <= COL;
    }

    private boolean isVacantPosition(Position pos) { // Is that space free
        return isValidPosition(pos) && getPiece(pos) == null;
    }

    private boolean isSameColorPiece(Position a, Position b) { // Is same Color Piece
        return getPiece(a) != null && getPiece(b) != null && getPiece(a) == getPiece(b);
    }

    public void setPieceTo(Piece p, Position to) {

        int col = p.getPos().getCol();
        int row = p.getPos().getRow();

        if (p.getBoard() == null) {
            p.setBoard(this);
            return;
        }
        setPiece(to, p);
        setPiece(new Position(row, col), null);
    }

    public Board copyBoard() {
        Board b = new Board();

        for (int i = 0; i < Constant.ROW; i++) {
            for (int j = 0; j < Constant.COL; j++) {
                b.setPiece(getPiece(new Position(j, i)));
            }
        }

        return b;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
    
        Board otherBoard = (Board) o;
    
        for (int i = 0; i < Constant.ROW; i++) {
            for (int j = 0; j < Constant.COL; j++) {
                Position position = new Position(j, i);
    
                Piece thisP = getPiece(position);
                Piece otherP = otherBoard.getPiece(position);
    
                if ((thisP == null && otherP != null) || (thisP != null && !thisP.equals(otherP))) {
                    return false;
                }
            }
        }
    
        return true;
    }

    public String exportBoardToString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < ROW; i++) {
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
        if (s.length() != ROW * COL)
            return false;

        int index = 0;
        for (int i = ROW - 1; i >= 0; i--) {
            for (int j = 0; j < COL; j++) {
                char c = s.charAt(index++);
                Piece p = readPiece(c, i, j);

                if (p == null)
                    continue;

                setPiece(p);
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
                    s.append((i + j) % 2 != 0 ? "□" : "■");
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

    public ArrayList<Position> getMoves() {
        return this.moves;
    }

    public void setMoves(ArrayList<Position> moves) {
        this.moves = moves;
    }

}

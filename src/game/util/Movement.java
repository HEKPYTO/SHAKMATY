package game.util;

import java.util.ArrayList;

import game.board.Board;
import game.constant.Constant;
import game.piece.King;
import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;

public class Movement { // Pawn Rook King have "moved" 

    protected Position current;
    protected ArrayList<Position> moves;
    protected Board board;

    public Movement(Position current, Board board) {
        setCurrent(current);
        setBoard(board);

        clearMoves(); // usable anyway
    }

    private boolean isInBound(Position pos) {
        return 0 <= pos.getX() && 
            pos.getX() <= Constant.COL &&
            0 <= pos.getY() &&
            pos.getY() <= Constant.ROW;
    }

    private boolean isSameColorPiece(Position a, Position b) { 
        return board.getPiece(a) != null && 
            board.getPiece(b) != null && 
            board.getPiece(a).isWhite() == board.getPiece(b).isWhite();
    } 

    private boolean isVacantPosition(Position pos) {
        return board.getPiece(pos) == null && isInBound(pos);
    }

    protected boolean isDifferentColor(Position a, Position b) {
        return board.getPiece(a) != null && 
            board.getPiece(b) != null &&
            board.getPiece(a).isWhite() != board.getPiece(b).isWhite();
    }

    public void singlePawnMove() {

        int x = current.getX();
        int y = current.getY();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position p = new Position(x, y + singleMove);

        if (!isInBound(p)) return;

        if (isVacantPosition(p)) moves.add(p);
        
    }

    public void doublePawnMove() {

        int x = current.getX();
        int y = current.getY();

        int doubleMove = board.getPiece(current).isWhite() ? 2: -2;

        Position p = new Position(x, y + doubleMove);

        if (!isInBound(p)) return;

        if (isVacantPosition(p)) moves.add(p);

    }

    public void PawnCaptureMove() {

        int x = current.getX();
        int y = current.getY();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position pLeft = new Position(x - 1, y + singleMove);
        Position pRight = new Position(x + 1, y + singleMove);

        if (isInBound(pLeft) && isDifferentColor(pLeft, current)) moves.add(pLeft);

        if (isInBound(pRight) && isDifferentColor(pRight, current)) moves.add(pRight);

    }
    
    public void crossMove() { // + sign movement
        
        int x = current.getX();
        int y = current.getY();

        for (int s = 0; s < 4; s++) {
            
            int dx = (int) Math.cos(s * Math.PI);
            int dy = (int) Math.sin(s * Math.PI);

            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(x + dx * i, y + dy * i);

                if (!isInBound(p)) break;

                if (isVacantPosition(p) || !isSameColorPiece(current, p)) moves.add(p);
                
            }
        }
    }

    public void diagonalMove() { // x sign movement 

        int x = current.getX();
        int y = current.getY();

        for (int s = 0; s < 4; s++) {

            int dx = s < 2 ? 1: -1;
            int dy = s % 2 == 0 ? 1: -1;
            
            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(x + s * dx * i, y + s * dy * i);

                if (!isInBound(p)) break;

                if (isVacantPosition(p) ||
                    !isSameColorPiece(current, p)) moves.add(p);

            }
        }
    }

    public void lShapeMove() { // L sign movement
        
        int x = current.getX();
        int y = current.getY();

        int[][] kMoves = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
        };

        for (int[] move : kMoves) {

            Position p = new Position(x + move[0], y + move[1]);
    
            if (!isInBound(p)) continue;

            if (isVacantPosition(p) || 
                !isSameColorPiece(current, p)) moves.add(p);

        }
    }

    public void squareMove() {

        int x = current.getX();
        int y = current.getY();

        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {

                Position p = new Position(i, j);

                if (!isInBound(p) || p.equals(current)) continue;

                if (isVacantPosition(p) || 
                    !isSameColorPiece(current, p)) moves.add(p);

            }
        }
    }

    // Castling move depends on King, 

    public void shortCastleMove() {

        int assumeRookX = Constant.COL - 1;
        int backRankY = board.getPiece(current).isWhite() ? 0: Constant.ROW - 1;

        Position assumeRook = new Position(assumeRookX, backRankY);

        if (board.getPiece(current) instanceof King && 
            board.getPiece(assumeRook) instanceof Rook) return;

        King k = (King) board.getPiece(current);
        Rook r = (Rook) board.getPiece(assumeRook);

        if (k.isMoved() || r.isMoved()) return;

        int kingX = k.getPos().getX();
        int rookX = r.getPos().getX();

        for (int i = kingX + 1; i < rookX; i++) {
            if (!board.isVacantPosition(new Position(i, backRankY))) return;
        }

        int kingPlacementX = Constant.COL - 2;
        int rookPlacementX = Constant.COL - 3;

        moves.add(new Position(kingPlacementX, backRankY, rookPlacementX, backRankY));
    }

    public void longCastleMove() {

        int assumeRookX = 0;
        int backRankY = board.getPiece(current).isWhite() ? 0: Constant.COL - 1;

        Position assumeRook = new Position(assumeRookX, backRankY);

        if (board.getPiece(current) instanceof King &&
            board.getPiece(assumeRook) instanceof Rook) return;

        King k = (King) board.getPiece(current);
        Rook r = (Rook) board.getPiece(assumeRook);

        if (k.isMoved() || r.isMoved()) return;

        int kingX = k.getPos().getX();
        int rookX = r.getPos().getX();

        for (int i = rookX + 1; i < kingX; i++) {
            if (!board.isVacantPosition(new Position(i, backRankY))) return;
        }

        int kingPlacementX = 3;
        int rookPlacementX = 4;

        moves.add(new Position(kingPlacementX, backRankY, rookPlacementX, backRankY));
    }

    public void enPassantMove() {

        int x = current.getX();
        int y = current.getY();

        int enPos = board.getPiece(current).isWhite() ? Constant.ROW - 2: 2; // +2 -2 of border

        if (y != enPos) return;

        Position lPos = new Position(x - 1, enPos);
        Position rPos = new Position(x + 1, enPos);

        if (isInBound(lPos) && 
            !isSameColorPiece(current, lPos) && 
            board.getPiece(lPos) instanceof Pawn &&
            ((Pawn) board.getPiece(lPos)).isPassnt()) moves.add(lPos);

        if (isInBound(rPos) && 
            !isSameColorPiece(current, rPos) && 
            board.getPiece(rPos) instanceof Pawn &&
            ((Pawn) board.getPiece(rPos)).isPassnt()) moves.add(rPos);

    }

    public void clearMoves() {
        setMoves(new ArrayList<Position>());
    }


    public Position getCurrent() {
        return this.current;
    }

    public void setCurrent(Position current) {
        this.current = current;
    }

    public ArrayList<Position> getMoves() {
        return this.moves;
    }

    public void setMoves(ArrayList<Position> moves) {
        this.moves = moves;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}

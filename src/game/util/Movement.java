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
        return 0 <= pos.getCol() && 
            pos.getCol() <= Constant.COL - 1 &&
            0 <= pos.getRow() &&
            pos.getRow() <= Constant.ROW - 1;
    }

    private boolean isSameColorPiece(Position a, Position b) { 
        return board.getPiece(a) != null && 
            board.getPiece(b) != null && 
            board.getPiece(a).isWhite() == board.getPiece(b).isWhite();
    } 

    private boolean isVacantPosition(Position pos) {
        return isInBound(pos) && board.getPiece(pos) == null;
    }

    protected boolean isDifferentColor(Position a, Position b) {
        return board.getPiece(a) != null && 
            board.getPiece(b) != null &&
            board.getPiece(a).isWhite() != board.getPiece(b).isWhite();
    }

    public void singlePawnMove() {

        int col = current.getCol();
        int row = current.getRow();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position p = new Position(row + singleMove, col);

        if (isInBound(p) && isVacantPosition(p)) moves.add(p);
        
    }

    public void doublePawnMove() {

        int col = current.getCol();
        int row = current.getRow();

        boolean isWhite = board.getPiece(current).isWhite();

        if ((isWhite && row != 1) || (!isWhite && row != Constant.COL - 2)) return;

        int singleMove = isWhite ? 1: -1;

        Position p1 = new Position(row + singleMove, col);

        if (!(isInBound(p1) && isVacantPosition(p1))) return;

        int doubleMove = isWhite ? 2: -2;

        Position p2 = new Position(row + doubleMove, col);

        if (isInBound(p2) && isVacantPosition(p2)) moves.add(p2);

    }

    public void PawnCaptureMove() {

        int col = current.getCol();
        int row = current.getRow();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position pLeft = new Position(row + singleMove, col - 1);
        Position pRight = new Position(row + singleMove, col + 1);

        if (isInBound(pLeft) && isDifferentColor(pLeft, current)) moves.add(pLeft);

        if (isInBound(pRight) && isDifferentColor(pRight, current)) moves.add(pRight);

    }
    
    public void plusMove() { // + sign movement
            
        int row = current.getRow();
        int col = current.getCol();

        for (int s = 0; s < 4; s++) {

            int dx = (int) Math.cos(s * Math.PI / 2);
            int dy = (int) Math.sin(s * Math.PI / 2);

            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(row + dy * i, col + dx * i);

                if (!isInBound(p)) break;

                if (isVacantPosition(p)) {

                    moves.add(p);

                } else {
                    
                    if (isDifferentColor(p, current)) moves.add(p);

                    break;
                }

            }
        }
    }

    public void diagonalMove() { // x sign movement 

        int row = current.getRow();
        int col = current.getCol();

        for (int s = 0; s < 4; s++) {

            int dx = s < 2 ? 1: -1;
            int dy = s % 2 == 0 ? 1: -1;
            
            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(row + dy * i, col + dx * i);

                if (!isInBound(p)) break;

                if (isVacantPosition(p)) {

                    moves.add(p);

                } else {
                    
                    if (isDifferentColor(p, current)) moves.add(p);

                    break;
                }

            }
        }
    }

    public void lShapeMove() { // L sign movement
        
        int row = current.getRow();
        int col = current.getCol();

        int[][] kMoves = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
        };

        for (int[] move : kMoves) {

            Position p = new Position(row + move[0], col + move[1]);

            if (isInBound(p) && 
                (isVacantPosition(p) || 
                !isSameColorPiece(current, p))) moves.add(p);

        }
    }

    public void squareMove() {

        int row = current.getRow();
        int col = current.getCol();

        for (int i = col - 1; i < col + 1; i++) {
            for (int j = row - 1; j < row + 1; j++) {

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

        int kingX = k.getPos().getCol();
        int rookX = r.getPos().getCol();

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

        int kingX = k.getPos().getCol();
        int rookX = r.getPos().getCol();

        for (int i = rookX + 1; i < kingX; i++) {
            if (!board.isVacantPosition(new Position(i, backRankY))) return;
        }

        int kingPlacementX = 3;
        int rookPlacementX = 4;

        moves.add(new Position(kingPlacementX, backRankY, rookPlacementX, backRankY));
    }

    public void enPassantMove() {

        int col = current.getCol();
        int row = current.getRow();

        int enPos = board.getPiece(current).isWhite() ? Constant.ROW - 4: 3; 
        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        if (row != enPos) return;

        Position lPos = new Position(enPos, col - 1);
        Position rPos = new Position(enPos, col + 1);

        Position afterLPos = new Position(enPos + singleMove, col - 1);
        Position afterRPos = new Position(enPos + singleMove, col + 1);

        if (isInBound(lPos) && 
            !isSameColorPiece(current, lPos) && 
            board.getPiece(lPos) instanceof Pawn &&
            ((Pawn) board.getPiece(lPos)).isPassant() &&
            isInBound(afterLPos)) moves.add(afterLPos);

        if (isInBound(rPos) && 
            !isSameColorPiece(current, rPos) && 
            board.getPiece(rPos) instanceof Pawn &&
            ((Pawn) board.getPiece(rPos)).isPassant() &&
            isInBound(afterRPos)) moves.add(afterRPos);

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

package game.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import game.Board.Board;
import game.piece.King;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Rook;
import game.position.Position;

public class Movement {

    protected Position current;
    protected Set<Position> moves = new HashSet<Position>();
    protected Board board;

    public Movement(Position current, Board board) {
        setCurrent(current);
        setBoard(board);
    }

    public void singlePawnMove() {
        int col = current.getCol();
        int row = current.getRow();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position p = new Position(row + singleMove, col);

        if (board.isInBound(p) && board.isEmpty(p)) moves.add(p);
    }

    public void doublePawnMove() {
        int col = current.getCol();
        int row = current.getRow();

        if (board.getPiece(current).isMoved()) return;

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position p1 = new Position(row + singleMove, col);

        if (!(board.isInBound(p1) && board.isEmpty(p1))) return;

        int doubleMove = board.getPiece(current).isWhite() ? 2: -2;

        Position p2 = new Position(row + doubleMove, col);

        if (board.isInBound(p2) && board.isEmpty(p2)) moves.add(p2);
    }

    public void pawnCaptureMove() {

        int col = current.getCol();
        int row = current.getRow();

        int singleMove = board.getPiece(current).isWhite() ? 1: -1;

        Position pLeft = new Position(row + singleMove, col - 1);
        Position pRight = new Position(row + singleMove, col + 1);

        if (board.isInBound(pLeft) && !board.isEmpty(pLeft) && !board.isSameColor(current, pLeft)) moves.add(pLeft);

        if (board.isInBound(pRight) && !board.isEmpty(pRight) && !board.isSameColor(current, pRight)) moves.add(pRight);
    }
    
    public void plusMove() { // + sign movement
            
        int row = current.getRow();
        int col = current.getCol();

        for (int s = 0; s < 4; s++) {

            int dx = (int) Math.cos(s * Math.PI / 2);
            int dy = (int) Math.sin(s * Math.PI / 2);

            for (int i = 1; i < Math.max(Constant.ROW, Constant.COL); i++) {

                Position p = new Position(row + dy * i, col + dx * i);

                if (!board.isInBound(p)) break;

                if (board.isEmpty(p)) moves.add(p);
                else {
                    if (!board.isSameColor(current, p)) moves.add(p);
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

                if (!board.isInBound(p)) break;

                if (board.isEmpty(p)) moves.add(p);
                else {
                    
                    if (!board.isSameColor(current, p)) moves.add(p);
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

            if (board.isInBound(p) && (board.isEmpty(p) || !board.isSameColor(current, p))) moves.add(p);

        }
    }

    public void squareMove() {

        int row = current.getRow();
        int col = current.getCol();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {

                Position p = new Position(i, j);

                if (!board.isInBound(p) || p.equals(current)) continue;

                if (board.isEmpty(p) || !board.isSameColor(current, p)) moves.add(p);
            }
        }
    }

    public boolean isInCheck() {

        Movement checked = new Movement(current, board);

        if (!(board.getPiece(current) instanceof King)) return false;

        checked.plusMove();
        checked.diagonalMove();
        checked.squareMove();
        checked.lShapeMove();

        Set<Position> checkedMoves = checked.getMoves();

        for (Position p : checkedMoves) {
            if (!board.isEmpty(p) && !board.isSameColor(current, p)) {
                Piece enemy = board.getPiece(p);

                if (enemy instanceof King) {
                    Movement enemyKMovement = new Movement(p, board);
                    enemyKMovement.squareMove();

                    for (Position kp: enemyKMovement.getMoves()) {
                        if (kp.equals(current)) return true;
                    }

                    return false;
                }

                for (Position enemyLegalMove : enemy.getNextLegalMove()) {
                    if (current.equals(enemyLegalMove)) return true;
                }
            }
        }

        return false;
    }

    // Castling move depends on King, 

    public void shortCastleMove() {

        int assumeRookX = Constant.COL - 1;
        int backRankY = board.getPiece(current).isWhite() ? 0: Constant.ROW - 1;

        Position assumeRook = new Position(backRankY, assumeRookX);

        if (!(board.getPiece(assumeRook) instanceof Rook r && board.getPiece(current) instanceof King k)) return;

        if (k.isMoved() || r.isMoved()) return;

        int kingX = k.getPosition().getCol();
        int rookX = r.getPosition().getCol();

        for (int i = kingX + 1; i < rookX; i++) {
            Position p = new Position(backRankY, i);

            if (!board.isEmpty(p) || new Movement(p, board).isInCheck()) return;
        }

        int kingPlacementX = Constant.COL - 2;
        int rookPlacementX = Constant.COL - 3;

        moves.add(new Position(backRankY, kingPlacementX, backRankY, rookPlacementX));
    }

    public void longCastleMove() {

        int assumeRookX = 0;
        int backRankY = board.getPiece(current).isWhite() ? 0: Constant.COL - 1;

        Position assumeRook = new Position(backRankY, assumeRookX);

        if (!(board.getPiece(assumeRook) instanceof Rook r && board.getPiece(current) instanceof King k)) return;

        if (k.isMoved() || r.isMoved()) return;

        int kingX = k.getPosition().getCol();
        int rookX = r.getPosition().getCol();

        for (int i = rookX + 1; i < kingX; i++) {
            Position p = new Position(backRankY, i);

            if (!board.isEmpty(p) || new Movement(p, board).isInCheck()) return;
        }

        int kingPlacementX = 2;
        int rookPlacementX = 3;

        moves.add(new Position(backRankY, kingPlacementX, backRankY, rookPlacementX));
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

        if (board.isInBound(lPos) &&
            !board.isSameColor(current, lPos) &&
            board.getPiece(lPos) instanceof Pawn &&
            ((Pawn) board.getPiece(lPos)).canPassantCapture() &&
            board.isInBound(afterLPos)) moves.add(afterLPos);

        if (board.isInBound(rPos) &&
            !board.isSameColor(current, rPos) &&
            board.getPiece(rPos) instanceof Pawn &&
            ((Pawn) board.getPiece(rPos)).canPassantCapture() &&
            board.isInBound(afterRPos)) moves.add(afterRPos);

    }

    public Position getCurrent() {
        return this.current;
    }

    public void setCurrent(Position current) {
        this.current = current;
    }

    public Set<Position> getMoves() {
        return this.moves;
    }

    public void clearMoves() {
        moves.clear();
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}

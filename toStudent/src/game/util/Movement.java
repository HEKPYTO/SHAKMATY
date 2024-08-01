package game.util;

import java.util.HashSet;
import java.util.Set;


import game.board.Board;
import game.piece.King;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Rook;
import game.position.Position;

public class Movement {

    protected Position current;
    protected Set<Position> moves = new HashSet<>();
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

        if (board.isInBound(pLeft) && !board.isEmpty(pLeft) && board.isSameColor(current, pLeft)) moves.add(pLeft);

        if (board.isInBound(pRight) && !board.isEmpty(pRight) && board.isSameColor(current, pRight)) moves.add(pRight);
    }

    // WOW YOU MADE IT HERE, HOW IS Q1 & Q2 ? 
    // START OF OWN IMPLEMENTATION OF THIS CLASS. GOOD LUCK ! :0
    
    public void plusMove() { // + sign movement
            
        // TODO: Implement this method
        // Adds moves in all four orthogonal directions (up, down, left, right). Handles obstacles and captures.
        // Checks if the single-square move is available (inBound and Empty).

        // If the position is out of bounds, then stop and go through the next direction.
        // If the position is empty add that position to “moves” Set.
        // Then     if it is not empty, check that the non-empty position is different color than the current position “board.isSameColor”
        //          if it is different add the position
        // Stop and go to the next direction
        // Warnings: Piece SHOULD NOT able to capture another piece with SAME color.
    }

    public void diagonalMove() { // x sign movement 

        // TODO: Implement this method
        // Adds moves diagonally (4 directions: NE, NW, SE, SW) in all four directions. Handles obstacles and captures.
        // Checks if the single-square move is available (inBound and Empty).

        // If the position is out of bounds, then stop and go through the next direction.
        // If the position is empty add that position to “moves” Set.
        // Then     if it is not empty, check that the non-empty position is different color than the current position “board.isSameColor”
        //          if it is different add the position
        // Stop and go to the next direction
        // Warnings: Piece SHOULD NOT able to capture another piece with SAME color.
    }

    public void lShapeMove() { // L sign movement
        
        // TODO: Implement this method
        // Adds L-shaped moves. Checks if the destination is within bounds and not occupied by a piece of the same color.
        
        // Simply, Count 2 units outwards and select squares left and right. just like L-shaped. (One up and Both Left and Right)
        // Warnings: Piece SHOULD NOT able to capture another piece with SAME color.
    }

    public void squareMove() {

        // TODO: Implement this method
        // Adds moves in all eight directions (adjacent square from current include 1-unit distance)

        // Make sure the move selected in the square does not include the current position. 
        // And check that the square isInBound. And the position is empty, or not the same color as the current pierce in the current position.

        // Warnings: Piece SHOULD NOT able to capture another piece with SAME color.

    }

    public boolean isInCheck(boolean color) {

        // TODO: Implement this method
        // Determines if the position is under attack by checking possible moves of all enemy pieces.
        // This method is dependent on the color given as a parameter.

        // change return logic (boolean)

        return true;

    }

    // END OF IMPLEMENTATION METHOD
    // DON'T TOUCH WHAT IS BELOW. OK ? :)

    public void shortCastleMove() {
        int backRank = board.getPiece(current).isWhite() ? 0 : Constant.ROW - 1;

        if (!(board.getPiece(current) instanceof King king) || king.isMoved() || king.isInCheck()) return;

        int kingCol = king.getPosition().getCol();

        Rook castle = null;

        for (int col = kingCol + 1; col < Constant.COL; col++) {
            Position p = new Position(backRank, col);

            if (board.getPiece(p) instanceof Rook rook
                    && rook.isWhite() == king.isWhite()
                    && !rook.isMoved()) {

                if (castle == null) castle = rook;
                else return;
            } else if (!board.isEmpty(p) || (new Movement(p, board)).isInCheck(king.isWhite())) return;

            if (castle != null && p.getCol() >= Constant.COL - 2) break;
        }

        if (castle != null) moves.add(castle.getPosition());
    }

    public void longCastleMove() {
        int backRank = board.getPiece(current).isWhite() ? 0 : Constant.ROW - 1;

        if (!(board.getPiece(current) instanceof King king) || king.isMoved() || king.isInCheck()) return;

        int kingCol = king.getPosition().getCol();

        Rook castle = null;

        for (int col = kingCol - 1; col >= 0; col--) {
            Position p = new Position(backRank, col);

            if (board.getPiece(p) instanceof Rook rook
                && rook.isWhite() == king.isWhite()
                && !rook.isMoved()) {

                if (castle == null) castle = rook;
                else return;
            } else if (!board.isEmpty(p) || (new Movement(p, board)).isInCheck(king.isWhite())) return;

            if (castle != null && p.getCol() < 2) break;
        }

        if (castle != null) moves.add(castle.getPosition());
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
                board.isSameColor(current, lPos) &&
            board.getPiece(lPos) instanceof Pawn &&
            ((Pawn) board.getPiece(lPos)).canPassantCaptured() &&
            board.isInBound(afterLPos)) moves.add(afterLPos);

        if (board.isInBound(rPos) &&
                board.isSameColor(current, rPos) &&
            board.getPiece(rPos) instanceof Pawn &&
            ((Pawn) board.getPiece(rPos)).canPassantCaptured() &&
            board.isInBound(afterRPos)) moves.add(afterRPos);

    }

    public void setCurrent(Position current) {
        this.current = current;
    }

    public Set<Position> getMoves() {
        return this.moves;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}

package game.control;

import java.util.ArrayList;

import game.board.Board;
import game.piece.King;
import game.piece.Piece;
import game.position.Position;

public class GameLogic {

    private Board current;
    private ArrayList<Board> history;
    private ArrayList<King> wKing;
    private ArrayList<King> bKing;
    
    public GameLogic(Board board, boolean isWhite) {
        current = board;
        history = new ArrayList<>();

        findKing();
    }

    private void findKing() {
        for (ArrayList<Piece> col: current.getBoard()) {
            for (Piece piece: col) {

                if (piece instanceof King && piece.isWhite()) {
                    wKing.add((King) piece);
                }
                
                if (piece instanceof King && !piece.isWhite()) {
                    bKing.add((King) piece);
                }
            }
        }
    }

    public void move(Position from, Position to) {
        
    }

    public boolean isInCheck() {
        
    }

    public boolean isCheckMate() {

    }

    public Board getCurrentBoard() {
        return this.current;
    }

    public void setCurrentBoard(Board current) {
        this.current = current;
    }

    public ArrayList<Board> getHistory() {
        return this.history;
    }

    public void setHistory(ArrayList<Board> history) {
        this.history = history;
    }

}

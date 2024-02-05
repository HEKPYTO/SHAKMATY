package game.control;

import java.util.ArrayList;

import game.board.Board;
import game.position.Position;

public class GameLogic {

    private Board current;
    private ArrayList<Board> hist;
    
    public GameLogic(Board board) {
        current = board;
        hist = new ArrayList<>();
    }

    // private ArrayList<Position> findKing() {

    // }


    public Board getCurrentBoard() {
        return this.current;
    }

    public void setCurrentBoard(Board current) {
        this.current = current;
    }

    public ArrayList<Board> getHist() {
        return this.hist;
    }

    public void setHist(ArrayList<Board> hist) {
        this.hist = hist;
    }

}

package game.main;

import game.board.Board;

public class Main {
    
    public static void main(String[] args) {
        Board b = new Board();

        System.err.println(b.exportBoardToString());
    }


}

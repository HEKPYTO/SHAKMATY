package game.main;

import game.board.Board;

public class Main {
    
    public static void main(String[] args) {
        Board b = new Board();

        b.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w - - 0 1");

        System.out.println(b.exportBoardToString());

        System.out.println(b.exportFEN());
    }


}

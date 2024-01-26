package game.main;

import game.board.Board;
import game.piece.Bishop;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {
        Board b = new Board();
        Bishop bi = new Bishop(true, new Position(null));

        b.placePiece(bi);

        System.err.println(b.exportBoardToString());
    }
}

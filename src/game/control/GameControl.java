package game.control;

import game.board.Board;
import game.piece.Bishop;
import game.position.Position;

public class GameControl {
    
    public GameControl() {
        Board b = new Board();
        Bishop bi = new Bishop(true, new Position(0, 1));

        b.placePiece(bi);

        System.err.println(b.exportBoardToString());

    }
}

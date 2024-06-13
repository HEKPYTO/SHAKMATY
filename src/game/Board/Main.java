package game.Board;

import game.piece.King;
import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;
import game.util.Movement;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        King k1 = new King(true, new Position("e4"), b);

        System.out.println(b.displayBoard());
    }
}

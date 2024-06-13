package game.Board;

import game.piece.King;
import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;
import game.util.Movement;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        King k1 = new King(true, new Position("d5"), b);
        new Rook(false, new Position("e1"), b);
        new Rook(false, new Position("e4"), b);

        Movement checked = new Movement(k1.getPosition(), b);

        System.out.println(k1.isInCheck());
        System.out.println(b.displayBoard());
        System.out.println(k1.getNextLegalMove());
    }
}

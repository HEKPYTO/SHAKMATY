package game.main;

import game.board.Board;
import game.piece.King;
import game.piece.Knight;
import game.piece.Rook;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {

        Board b = new Board();

        King k1 = new King(true, new Position("e1"), b);

        Rook rR = new Rook(true, new Position("h1"), b);

        System.out.println(k1.getLegalMove());

    }


}

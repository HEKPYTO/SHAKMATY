package game.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.board.Board;
import game.piece.Pawn;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {

        Board b = new Board();

        Position h7 = new Position("h7");
        Pawn pw4 = new Pawn(false, h7, b);

        System.out.println(b.displayBoard());
        pw4.legalMove();
        System.out.println(pw4.getLegalMove());

    }


}

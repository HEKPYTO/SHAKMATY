package game.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.board.Board;
import game.piece.Knight;
import game.piece.Pawn;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {

        Board b = new Board();

        Position h7 = new Position("h7");
        Knight k = new Knight(true, h7, b);

        System.out.println(k.isMoved());

    }


}

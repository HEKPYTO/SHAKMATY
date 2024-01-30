package test.PieceTest;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.position.Position;

public class QueenTest {
    
    Board b;
    Position qW;
    Position qB;

    @Before
    public void setup() {
        b = new Board();

        qW = new Position("d1");
        qB = new Position("d8");
    }

    @Test
    public void 
}

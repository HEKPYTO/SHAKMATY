package test.PieceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Rook;
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
    public void QueenConstructorTest() {

        Rook wRook = new Rook(true, qW, b);

        assertTrue(!wRook.isMoved());
        assertEquals(qW, wRook.getPos());
        assertEquals(b, wRook.getBoard());

        Rook bRook = new Rook(false, qB, b);

        assertTrue(!bRook.isMoved());
        assertEquals(qB, bRook.getPos());
        assertEquals(b, bRook.getBoard());

    }
}

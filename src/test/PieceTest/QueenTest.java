package test.PieceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Queen;
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

        Queen wRook = new Queen(true, qW, b);

        assertTrue(!wRook.isMoved());
        assertEquals(qW, wRook.getPos());
        assertEquals(b, wRook.getBoard());

        Queen bRook = new Queen(false, qB, b);

        assertTrue(!bRook.isMoved());
        assertEquals(qB, bRook.getPos());
        assertEquals(b, bRook.getBoard());

    }

    @Test
    public void testRookQueenStatus() {
        Rook wRook = new Rook(true, wRL, b);
        assertFalse(wRook.isMoved());

        wRook.move(new Position("a3"));
        assertTrue(wRook.isMoved());
    }
}

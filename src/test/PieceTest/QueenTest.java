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
    public void queenConstructorTest() {
        Queen wQueen = new Queen(true, qW, b);
        assertTrue(!wQueen.isMoved());
        assertEquals(qW, wQueen.getPos());
        assertEquals(b, wQueen.getBoard());

        Queen bQueen = new Queen(false, qB, b);
        assertTrue(!bQueen.isMoved());
        assertEquals(qB, bQueen.getPos());
        assertEquals(b, bQueen.getBoard());
    }

    @Test
    public void testQueenMovedStatus() {
        Queen wQueen = new Queen(true, qW, b);
        assertFalse(wQueen.isMoved());

        wQueen.move(new Position("e2"));
        assertTrue(wQueen.isMoved());
    }


}

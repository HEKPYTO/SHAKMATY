package test.PieceTest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Knight;
import game.position.Position;

public class KnightTest {

    Board b;
    Position wKL;
    Position wKR;

    Position bKL;
    Position bKR;

    @Before
    public void setup() {
        b = new Board();

        wKL = new Position("g1");
        wKR = new Position("f1");

        bKL = new Position("g8");
        bKR = new Position("f8");
    }
    
    @Test
    public void knightConstructorTest() {

        Knight k = new Knight(true, wKL);

        assertTrue(!k.isMoved());
        assertEquals(wKL, k.getPos());
        assertNull(k.getBoard());

        Knight k1 = new Knight(true, bKL);

        assertTrue(!k1.isMoved());
        assertEquals(bKL, k1.getPos());
        assertNull(k1.getBoard());

    }

    @Test
    public void knightConstructorWithBoardTest() {

        Knight k1 = new Knight(true, wKL, b);

        assertTrue(!k1.isMoved());
        assertEquals(wKL, k1.getPos());
        assertEquals(b, k1.getBoard());

        Knight k2 = new Knight(false, bKL, b);

        assertTrue(!k2.isMoved());
        assertEquals(bKL, k2.getPos());
        assertEquals(b, k2.getBoard());
        
    }
}

package test.PieceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;

public class RookTest {
    
    Board b;
    Position wRL;
    Position bRL;
    Position wRR;
    Position bRR;

    @Before
    public void setup() {
        b = new Board();

        wRL = new Position("a1");
        bRL = new Position("a8");
        wRR = new Position("h1");
        bRR = new Position("h8");
    }

    @Test
    public void rookConstructorWithBoardTest() {

        Rook wRook = new Rook(true, wRR, b);

        assertTrue(!wRook.isMoved());
        assertEquals(wRR, wRook.getPos());
        assertEquals(b, wRook.getBoard());

        Rook bRook = new Rook(false, bRL, b);

        assertTrue(!bRook.isMoved());
        assertEquals(bRL, bRook.getPos());
        assertEquals(b, bRook.getBoard());
    }

    @Test
    public void testRookMovedStatus() {
        Rook wRook = new Rook(true, wRL, b);
        assertFalse(wRook.isMoved());

        wRook.move(new Position("a3"));
        assertTrue(wRook.isMoved());
    }

    @Test
    public void rookMoveStartingPositonTest() { // 7 vertical + 7 horizontal

        Rook wRook = new Rook(true, wRL, b); 

        assertEquals(14, wRook.getLegalMove().size());

        Rook bRook = new Rook(false, bRR, b);
        
        assertEquals(14, bRook.getLegalMove().size());
    }

    @Test
    public void rookAtCenterTest() {

        Rook wRook = new Rook(true, new Position("e4"), b);
        
        assertEquals(14, wRook.getLegalMove().size());

        assertTrue(wRook.getLegalMove().contains(new Position("d4")));
        assertTrue(wRook.getLegalMove().contains(new Position("f4")));
        assertTrue(wRook.getLegalMove().contains(new Position("e3")));
        assertTrue(wRook.getLegalMove().contains(new Position("e5")));

        Rook bRook = new Rook(false, new Position("d6"), b);
        
        assertEquals(14, bRook.getLegalMove().size());

        assertTrue(bRook.getLegalMove().contains(new Position("d7")));
        assertTrue(bRook.getLegalMove().contains(new Position("d5")));
        assertTrue(bRook.getLegalMove().contains(new Position("c6")));
        assertTrue(bRook.getLegalMove().contains(new Position("e6")));
    }

    @Test
    public void rookMoveObstructed() {

        Rook wRook = new Rook(true, new Position("a1"), b);
        
        new Rook(true, new Position("a5"), b);
        new Rook(true, new Position("c1"), b);
        
        assertEquals(4, wRook.getLegalMove().size());

        Rook bRook = new Rook(false, new Position("h8"), b);
        
        new Rook(false, new Position("h1"), b);
        new Rook(false, new Position("d8"), b);
        
        assertEquals(9, bRook.getLegalMove().size());
    }

    @Test
    public void rookMoveCapturable() {

        Rook wRook = new Rook(true, new Position("a1"), b);
        
        new Rook(false, new Position("a5"), b);
        new Rook(false, new Position("c1"), b);
        
        assertTrue(wRook.getLegalMove().contains(new Position("a5")));
        assertTrue(wRook.getLegalMove().contains(new Position("c1")));
        assertEquals(6, wRook.getLegalMove().size());

        Rook bRook = new Rook(false, new Position("h8"), b);
        
        new Rook(true, new Position("h6"), b);
        new Rook(true, new Position("b8"), b);

        assertTrue(bRook.getLegalMove().contains(new Position("h6")));
        assertTrue(bRook.getLegalMove().contains(new Position("b8")));
        
        assertEquals(8, bRook.getLegalMove().size());
    }

    @Test
    public void rookFullyObstructed() {

        Rook rook = new Rook(true, new Position("e4"), b);

        new Pawn(true, new Position("e3"), b);
        new Pawn(true, new Position("e5"), b);
        new Pawn(true, new Position("d4"), b);
        new Pawn(true, new Position("f4"), b);

        assertEquals(0, rook.getLegalMove().size());

        Rook rook2 = new Rook(false, new Position("c7"), b);

        new Pawn(true, new Position("c6"), b);
        new Pawn(true, new Position("c8"), b);
        new Pawn(true, new Position("b7"), b);
        new Pawn(true, new Position("d7"), b);

        assertEquals(4, rook2.getLegalMove().size());
    }
}

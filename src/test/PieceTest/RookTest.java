package test.PieceTest;


import game.Board.Board;
import org.junit.Before;
import org.junit.Test;

import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;

import static org.junit.Assert.*;

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
    public void rookConstructorTest() {

        Rook wRook = new Rook(true, wRR, b);

        assertFalse(wRook.isMoved());
        assertEquals(wRR, wRook.getPosition());
        assertEquals(b, wRook.getBoard());

        Rook bRook = new Rook(false, bRL, b);

        assertFalse(bRook.isMoved());
        assertEquals(bRL, bRook.getPosition());
        assertEquals(b, bRook.getBoard());
    }

    @Test
    public void rookMoveStartingPositionTest() { // 7 vertical + 7 horizontal

        Rook wRook = new Rook(true, wRL, b); 

        assertEquals(14, wRook.getNextLegalMove().size());

        Rook bRook = new Rook(false, bRR, b);
        
        assertEquals(14, bRook.getNextLegalMove().size());
    }

    @Test
    public void rookAtCenterTest() { // 7 vertical + 7 horizontal

        Rook wRook = new Rook(true, new Position("e4"), b);
        
        assertEquals(14, wRook.getNextLegalMove().size());

        assertTrue(wRook.getNextLegalMove().contains(new Position("d4")));
        assertTrue(wRook.getNextLegalMove().contains(new Position("f4")));
        assertTrue(wRook.getNextLegalMove().contains(new Position("e3")));
        assertTrue(wRook.getNextLegalMove().contains(new Position("e5")));

        Rook bRook = new Rook(false, new Position("d6"), b);
        
        assertEquals(14, bRook.getNextLegalMove().size());

        assertTrue(bRook.getNextLegalMove().contains(new Position("d7")));
        assertTrue(bRook.getNextLegalMove().contains(new Position("d5")));
        assertTrue(bRook.getNextLegalMove().contains(new Position("c6")));
        assertTrue(bRook.getNextLegalMove().contains(new Position("e6")));
    }

    @Test
    public void rookMoveObstructed() {

        Rook wRook = new Rook(true, new Position("a1"), b);
        
        new Rook(true, new Position("a5"), b);
        new Rook(true, new Position("c1"), b);
        
        assertEquals(4, wRook.getNextLegalMove().size());

        Rook bRook = new Rook(false, new Position("h8"), b);
        
        new Rook(false, new Position("h1"), b);
        new Rook(false, new Position("d8"), b);
        
        assertEquals(9, bRook.getNextLegalMove().size());
    }

    @Test
    public void rookMoveCapture() {

        Rook wRook = new Rook(true, new Position("a1"), b);
        
        new Rook(false, new Position("a5"), b);
        new Rook(false, new Position("c1"), b);
        
        assertTrue(wRook.getNextLegalMove().contains(new Position("a5")));
        assertTrue(wRook.getNextLegalMove().contains(new Position("c1")));
        assertEquals(6, wRook.getNextLegalMove().size());

        Rook bRook = new Rook(false, new Position("h8"), b);
        
        new Rook(true, new Position("h6"), b);
        new Rook(true, new Position("b8"), b);

        assertTrue(bRook.getNextLegalMove().contains(new Position("h6")));
        assertTrue(bRook.getNextLegalMove().contains(new Position("b8")));
        
        assertEquals(8, bRook.getNextLegalMove().size());
    }

    @Test
    public void rookFullyObstructed() {

        Rook rook = new Rook(true, new Position("e4"), b);

        new Pawn(true, new Position("e3"), b);
        new Pawn(true, new Position("e5"), b);
        new Pawn(true, new Position("d4"), b);
        new Pawn(true, new Position("f4"), b);

        assertEquals(0, rook.getNextLegalMove().size()); // same color block

        Rook rook2 = new Rook(false, new Position("c7"), b);

        new Pawn(true, new Position("c6"), b);
        new Pawn(true, new Position("c8"), b);
        new Pawn(true, new Position("b7"), b);
        new Pawn(true, new Position("d7"), b);

        assertEquals(4, rook2.getNextLegalMove().size()); // can capture different color
    }

    @Test 
    public void rookSomeObstructed() {

        Rook rook = new Rook(true, new Position("f3"), b);

        new Pawn(true, new Position("f4"), b);
        new Pawn(true, new Position("f2"), b);
        new Pawn(false, new Position("g3"), b);
        new Pawn(false, new Position("e3"), b);

        assertEquals(2, rook.getNextLegalMove().size());

        Rook rook2 = new Rook(false, new Position("c7"), b);

        new Pawn(true, new Position("c6"), b);
        new Pawn(true, new Position("c8"), b);
        new Pawn(true, new Position("b7"), b);
        new Pawn(false, new Position("d7"), b);

        assertEquals(3, rook2.getNextLegalMove().size());
    }
}

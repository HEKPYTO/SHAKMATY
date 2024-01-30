package test.PieceTest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Knight;
import game.piece.Pawn;
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

        wKL = new Position("b1");
        wKR = new Position("g1");

        bKL = new Position("b8");
        bKR = new Position("g8");
    }
    
    @Test
    public void knightConstructorTest() {

        Knight k = new Knight(true, wKL);

        assertTrue(!k.isMoved());
        assertEquals(wKL, k.getPos());
        assertNull(k.getBoard());

        Knight k1 = new Knight(false, bKL);

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

    @Test
    public void testKnightMovedStatus() {
        Knight k = new Knight(true, new Position("b1"));
        assertFalse(k.isMoved());

        k.move(new Position("c3"));
        assertTrue(k.isMoved());
    }

    @Test
    public void knightMoveStartingPositonTest() {

        Knight kWL = new Knight(true, wKL, b);
        
        assertEquals(3, kWL.getLegalMove().size());

        assertTrue(kWL.getLegalMove().contains(new Position("a3")));
        assertTrue(kWL.getLegalMove().contains(new Position("c3")));
        assertTrue(kWL.getLegalMove().contains(new Position("d2")));

        Knight kWR = new Knight(true, wKR, b);
        
        assertEquals(3, kWR.getLegalMove().size());

        assertTrue(kWR.getLegalMove().contains(new Position("f3")));
        assertTrue(kWR.getLegalMove().contains(new Position("h3")));
        assertTrue(kWR.getLegalMove().contains(new Position("e2")));

        Knight kBL = new Knight(false, bKL, b);
        
        assertEquals(3, kBL.getLegalMove().size());

        assertTrue(kBL.getLegalMove().contains(new Position("a6")));
        assertTrue(kBL.getLegalMove().contains(new Position("c6")));
        assertTrue(kBL.getLegalMove().contains(new Position("d7")));

        Knight kBR = new Knight(false, bKR, b);
        
        assertEquals(3, kBR.getLegalMove().size());

        assertTrue(kBR.getLegalMove().contains(new Position("f6")));
        assertTrue(kBR.getLegalMove().contains(new Position("h6")));
        assertTrue(kBR.getLegalMove().contains(new Position("e7")));

    }

    @Test
    public void knightAtConerMove() {

        Knight kW = new Knight(true, new Position("a1"), b);
        
        assertEquals(2, kW.getLegalMove().size());

        assertTrue(kW.getLegalMove().contains(new Position("b3")));
        assertTrue(kW.getLegalMove().contains(new Position("c2")));


        Knight kB = new Knight(false, new Position("h8"), b);

        assertEquals(2, kB.getLegalMove().size());

        assertTrue(kB.getLegalMove().contains(new Position("g6")));
        assertTrue(kB.getLegalMove().contains(new Position("f7")));

    }

    @Test
    public void knightMoveObstructed() {

        Knight kW = new Knight(true, new Position("a1"), b);
        Pawn p = new Pawn(true, new Position("b3"), b);
        
        assertEquals(1, kW.getLegalMove().size());

        assertFalse(kW.getLegalMove().contains(new Position("b3")));
        assertTrue(kW.getLegalMove().contains(new Position("c2")));


        Knight kB = new Knight(false, new Position("h8"), b);
        Pawn pB1 = new Pawn(false, new Position("g6"), b);
        Pawn pB2 = new Pawn(false, new Position("f7"), b); 

        assertEquals(0, kB.getLegalMove().size());

        assertFalse(kB.getLegalMove().contains(new Position("g6")));
        assertFalse(kB.getLegalMove().contains(new Position("f7")));

    }

    @Test
    public void knightMoveAtBoardCenter() {

        Knight kW = new Knight(true, new Position("e4"), b);
        
        assertEquals(8, kW.getLegalMove().size());

        Knight kB = new Knight(false, new Position("f6"), b);
        
        assertEquals(8, kB.getLegalMove().size());
    } 

    @Test
    public void knightCapturableOpposePiece() {

        Knight kW = new Knight(true, new Position("e4"), b);
        Pawn pB1 = new Pawn(false, new Position("d2"), b);
        Pawn pW1 = new Pawn(true, new Position("c3"), b); 
        
        assertEquals(7, kW.getLegalMove().size());

        Pawn pW2 = new Pawn(false, new Position("c5"), b);
        Pawn pW3 = new Pawn(false, new Position("d6"), b); 

        assertEquals(7, kW.getLegalMove().size());

        Knight kB = new Knight(false, new Position("b7"), b);

        assertEquals(2, kB.getLegalMove().size());
    }

    @Test
    public void knightCapturableOpposeKnight() {

        Knight kW = new Knight(true, new Position("d4"), b);
        Knight kB = new Knight(false, new Position("c6"), b);

        assertTrue(kW.getLegalMove().contains(new Position("c6")));
        assertTrue(kB.getLegalMove().contains(new Position("d4")));
    }

    @Test
    public void knightCantMoveSurroundedByOwnPieces() {
        Knight kW = new Knight(true, new Position("e4"), b);
        Pawn pW1 = new Pawn(true, new Position("d6"), b);
        Pawn pW2 = new Pawn(true, new Position("f6"), b);
        Pawn pW3 = new Pawn(true, new Position("d2"), b);
        Pawn pW4 = new Pawn(true, new Position("f2"), b);
        Pawn pW5 = new Pawn(true, new Position("c3"), b);
        Pawn pW6 = new Pawn(true, new Position("g3"), b);
        Pawn pW7 = new Pawn(true, new Position("c5"), b);
        Pawn pW8 = new Pawn(true, new Position("g5"), b);

        assertEquals(0, kW.getLegalMove().size());
    }

    @Test
    public void knightCantMoveSurroundedByOpposePieces() {
        Knight kW = new Knight(false, new Position("e4"), b);
        Pawn pW1 = new Pawn(true, new Position("d6"), b);
        Pawn pW2 = new Pawn(true, new Position("f6"), b);
        Pawn pW3 = new Pawn(true, new Position("d2"), b);
        Pawn pW4 = new Pawn(true, new Position("f2"), b);
        Pawn pW5 = new Pawn(true, new Position("c3"), b);
        Pawn pW6 = new Pawn(true, new Position("g3"), b);
        Pawn pW7 = new Pawn(true, new Position("c5"), b);
        Pawn pW8 = new Pawn(true, new Position("g5"), b);
        
        assertEquals(8, kW.getLegalMove().size());
    }
}

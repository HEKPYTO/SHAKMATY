package test.PieceTest;

import game.Board.Board;
import org.junit.Before;
import org.junit.Test;

import game.piece.Knight;
import game.piece.Pawn;
import game.position.Position;

import static org.junit.Assert.*;

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
    public void knightConstructorWithBoardTest() {

        Knight k1 = new Knight(true, wKL, b);

        assertFalse(k1.isMoved());
        assertEquals(wKL, k1.getPosition());
        assertEquals(b, k1.getBoard());

        Knight k2 = new Knight(false, bKL, b);

        assertFalse(k2.isMoved());
        assertEquals(bKL, k2.getPosition());
        assertEquals(b, k2.getBoard());
        
    }

    @Test
    public void knightMoveStartingPositonTest() {

        Knight kWL = new Knight(true, wKL, b);

        assertEquals(3, kWL.getNextLegalMove().size());

        assertTrue(kWL.getNextLegalMove().contains(new Position("a3")));
        assertTrue(kWL.getNextLegalMove().contains(new Position("c3")));
        assertTrue(kWL.getNextLegalMove().contains(new Position("d2")));

        Knight kWR = new Knight(true, wKR, b);
        
        assertEquals(3, kWR.getNextLegalMove().size());

        assertTrue(kWR.getNextLegalMove().contains(new Position("f3")));
        assertTrue(kWR.getNextLegalMove().contains(new Position("h3")));
        assertTrue(kWR.getNextLegalMove().contains(new Position("e2")));

        Knight kBL = new Knight(false, bKL, b);
        
        assertEquals(3, kBL.getNextLegalMove().size());

        assertTrue(kBL.getNextLegalMove().contains(new Position("a6")));
        assertTrue(kBL.getNextLegalMove().contains(new Position("c6")));
        assertTrue(kBL.getNextLegalMove().contains(new Position("d7")));

        Knight kBR = new Knight(false, bKR, b);
        
        assertEquals(3, kBR.getNextLegalMove().size());

        assertTrue(kBR.getNextLegalMove().contains(new Position("f6")));
        assertTrue(kBR.getNextLegalMove().contains(new Position("h6")));
        assertTrue(kBR.getNextLegalMove().contains(new Position("e7")));

    }

    @Test
    public void knightAtCornerMove() {

        Knight kW = new Knight(true, new Position("a1"), b);
        
        assertEquals(2, kW.getNextLegalMove().size());

        assertTrue(kW.getNextLegalMove().contains(new Position("b3")));
        assertTrue(kW.getNextLegalMove().contains(new Position("c2")));


        Knight kB = new Knight(false, new Position("h8"), b);

        assertEquals(2, kB.getNextLegalMove().size());

        assertTrue(kB.getNextLegalMove().contains(new Position("g6")));
        assertTrue(kB.getNextLegalMove().contains(new Position("f7")));

    }

    @Test
    public void knightMoveObstructed() {

        Knight kW = new Knight(true, new Position("a1"), b);
        new Pawn(true, new Position("b3"), b);
        
        assertEquals(1, kW.getNextLegalMove().size());

        assertFalse(kW.getNextLegalMove().contains(new Position("b3")));
        assertTrue(kW.getNextLegalMove().contains(new Position("c2")));


        Knight kB = new Knight(false, new Position("h8"), b);
        new Pawn(false, new Position("g6"), b);
        new Pawn(false, new Position("f7"), b); 

        assertEquals(0, kB.getNextLegalMove().size());

        assertFalse(kB.getNextLegalMove().contains(new Position("g6")));
        assertFalse(kB.getNextLegalMove().contains(new Position("f7")));

    }

    @Test
    public void knightMoveAtBoardCenter() {

        Knight kW = new Knight(true, new Position("e4"), b);
        
        assertEquals(8, kW.getNextLegalMove().size());

        Knight kB = new Knight(false, new Position("f6"), b);
        
        assertEquals(8, kB.getNextLegalMove().size());
    } 

    @Test
    public void knightCapturableOpposePiece() {

        Knight kW = new Knight(true, new Position("e4"), b);
        new Pawn(false, new Position("d2"), b);
        new Pawn(true, new Position("c3"), b); 
        
        assertEquals(7, kW.getNextLegalMove().size());

        new Pawn(false, new Position("c5"), b);
        new Pawn(false, new Position("d6"), b); 

        assertEquals(7, kW.getNextLegalMove().size());

        Knight kB = new Knight(false, new Position("b7"), b);

        assertEquals(2, kB.getNextLegalMove().size());
    }

    @Test
    public void knightCapturableOpposeKnight() {

        Knight kW = new Knight(true, new Position("d4"), b);
        Knight kB = new Knight(false, new Position("c6"), b);

        assertTrue(kW.getNextLegalMove().contains(new Position("c6")));
        assertTrue(kB.getNextLegalMove().contains(new Position("d4")));
    }

    @Test
    public void knightCantMoveSurroundedByOwnPieces() {
        Knight kW = new Knight(true, new Position("e4"), b);
        new Pawn(true, new Position("d6"), b);
        new Pawn(true, new Position("f6"), b);
        new Pawn(true, new Position("d2"), b);
        new Pawn(true, new Position("f2"), b);
        new Pawn(true, new Position("c3"), b);
        new Pawn(true, new Position("g3"), b);
        new Pawn(true, new Position("c5"), b);
        new Pawn(true, new Position("g5"), b);

        assertEquals(0, kW.getNextLegalMove().size());
    }

    @Test
    public void knightCantMoveSurroundedByOpposePieces() {
        Knight kW = new Knight(false, new Position("e4"), b);
        new Pawn(true, new Position("d6"), b);
        new Pawn(true, new Position("f6"), b);
        new Pawn(true, new Position("d2"), b);
        new Pawn(true, new Position("f2"), b);
        new Pawn(true, new Position("c3"), b);
        new Pawn(true, new Position("g3"), b);
        new Pawn(true, new Position("c5"), b);
        new Pawn(true, new Position("g5"), b);
        
        assertEquals(8, kW.getNextLegalMove().size());
    }
}

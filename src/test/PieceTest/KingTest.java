package test.PieceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Rook;
import game.position.Position;

public class KingTest {

    Board b;
    Position kW = new Position("e1");
    Position kB = new Position("e8");

    @Before
    public void setup() {
        b = new Board();

        kW = new Position("e1");
        kB = new Position("e8");
    }

    @Test
    public void kingConstructorWithBoardTest() {

        King wK = new King(true, kW, b);

        assertFalse(wK.isMoved());
        assertEquals(wK.getPos(), kW);
        assertEquals(wK.getBoard(), b);

        King bK = new King(false, kB, b);

        assertFalse(bK.isMoved());
        assertEquals(bK.getPos(), kB);
        assertEquals(bK.getBoard(), b);

    }

    @Test
    public void kingMoveStatusTest() {

        King wK = new King(true, kW, b);

        assertFalse(wK.isMoved());
        assertEquals(kW, wK.getPos());

        wK.move(new Position("d1"));
        assertTrue(wK.isMoved());

    }

    @Test
    public void kingVacantBoardMoveTest() {

        King k1 = new King(true, new Position("e4"), b);

        assertEquals(k1.getLegalMove().size(), 8);

        assertTrue(k1.getLegalMove().contains(new Position("d3")));
        assertTrue(k1.getLegalMove().contains(new Position("e3")));
        assertTrue(k1.getLegalMove().contains(new Position("f3")));
        assertTrue(k1.getLegalMove().contains(new Position("d4")));
        assertTrue(k1.getLegalMove().contains(new Position("f4")));
        assertTrue(k1.getLegalMove().contains(new Position("d5")));
        assertTrue(k1.getLegalMove().contains(new Position("e5")));
        assertTrue(k1.getLegalMove().contains(new Position("f5")));

        King k2 = new King(false, kB, b);

        assertEquals(5, k2.getLegalMove().size());

        assertTrue(k2.getLegalMove().contains(new Position("d8")));
        assertTrue(k2.getLegalMove().contains(new Position("d7")));
        assertTrue(k2.getLegalMove().contains(new Position("e7")));
        assertTrue(k2.getLegalMove().contains(new Position("f8")));
        assertTrue(k2.getLegalMove().contains(new Position("f7")));
    }

    @Test
    public void kingCapturePieceTest() {

        King k1 = new King(true, kW, b);
        new Pawn(false, new Position("e2"), b);

        assertEquals(3, k1.getLegalMove().size());

        assertTrue(k1.getLegalMove().contains(new Position("e2")));

        King k2 = new King(false, kB, b);
        new Rook(true, new Position("d8"), b);

        assertEquals(4, k2.getLegalMove().size());

        assertTrue(k2.getLegalMove().contains(new Position("d8")));

        King k3 = new King(false, new Position("e4"), b);
        new Rook(true, new Position("d5"), b);
        
        assertEquals(3, k3.getLegalMove().size()); // rook cant be capture as it was defended by rook d8

        assertFalse(k3.getLegalMove().contains(new Position("d5")));

    }

    @Test
    public void kingMoveObstructedTest() {

        King k1 = new King(true, new Position("a1"), b);
        new Pawn(true, new Position("b2"), b);

        assertEquals(k1.getLegalMove().size(), 2);

        assertTrue(k1.getLegalMove().contains(new Position("a2")));
        assertTrue(k1.getLegalMove().contains(new Position("b1")));

        King k2 = new King(true, new Position("d1"), b);
        new Pawn(true, new Position("c2"), b);
        new Pawn(true, new Position("e2"), b);

        assertEquals(k2.getLegalMove().size(), 3);

        assertTrue(k2.getLegalMove().contains(new Position("c1")));
        assertTrue(k2.getLegalMove().contains(new Position("d2")));
        assertTrue(k2.getLegalMove().contains(new Position("e1")));

        King k3 = new King(false, new Position("e8"), b);
        new Pawn(false, new Position("e7"), b);

        assertEquals(k3.getLegalMove().size(), 4);
    }

    @Test
    public void kingInCheckTest() {

        King k1 = new King(true, new Position("a1"), b);
        new Pawn(false, new Position("b2"), b);

        assertTrue(k1.isInChecked());

        assertTrue(k1.getLegalMove().contains(new Position("b2")));

        King k2 = new King(false, new Position("e8"), b);
        new Pawn(true, new Position("d7"), b);
        new Pawn(true, new Position("f7"), b);

        assertTrue(k2.isInChecked());

        assertTrue(k2.getLegalMove().contains(new Position("d7")));
        assertTrue(k2.getLegalMove().contains(new Position("f7")));
    }

    @Test
    public void kingBlockCheckTest() {

        King k1 = new King(true, new Position("e1"), b);

        new Rook(false, new Position("e8"), b);

        assertTrue(k1.isInChecked());

        new Rook(true, new Position("e2"), b);

        assertFalse(k1.isInChecked());
    }

    @Test
    public void kingNoMoveTest() {

        King k1 = new King(true, new Position("a1"), b);

        new Rook(false, new Position("b3"), b);
        new Rook(false, new Position("c2"), b);

        assertTrue(k1.getLegalMove().isEmpty());

        King k2 = new King(false, new Position("h8"), b);

        new Knight(true, new Position("f6"), b);
        new Bishop(true, new Position("f8"), b);

        assertTrue(k2.getLegalMove().isEmpty());
    }

    @Test
    public void castlingMoveWhiteTest() {

        King k1 = new King(true, new Position("e1"), b);
        assertFalse(k1.isMoved());

        Rook rR = new Rook(true, new Position("h1"), b);
        assertFalse(rR.isMoved());

        assertTrue(k1.getLegalMove().contains(new Position("g1"))); // short castle

        Rook rL = new Rook(true, new Position("a1"), b);
        assertFalse(rL.isMoved());

        assertTrue(k1.getLegalMove().contains(new Position("c1"))); // long castle
    }

    @Test
    public void castlingMoveBlackTest() {

        King k1 = new King(false, new Position("e8"), b);
        assertFalse(k1.isMoved());

        Rook rR = new Rook(false, new Position("h8"), b);
        assertFalse(rR.isMoved());

        assertTrue(k1.getLegalMove().contains(new Position("g8"))); // short castle

        Rook rL = new Rook(false, new Position("a8"), b);
        assertFalse(rL.isMoved());

        assertTrue(k1.getLegalMove().contains(new Position("c8"))); // long castle
    }
}

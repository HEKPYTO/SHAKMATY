package test.PieceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.King;
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

        assertEquals(k1.getLegalMove().size(), 5);

        assertTrue(k1.getLegalMove().contains(new Position("e2")));

        King k2 = new King(false, kB, b);
        new Rook(true, new Position("d8"), b);

        assertEquals(k2.getLegalMove().size(), 5);

        assertTrue(k2.getLegalMove().contains(new Position("d8")));

        King k3 = new King(false, new Position("e4"), b);
        new Rook(true, new Position("d5"), b);

        assertEquals(k3.getLegalMove().size(), 8);

        assertTrue(k3.getLegalMove().contains(new Position("d5")));

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

        assertEquals(k1.inChecked(), 0);

        assertTrue(k1.inChecked());
    }
}

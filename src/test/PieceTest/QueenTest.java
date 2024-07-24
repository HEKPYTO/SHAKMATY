package test.PieceTest;

import game.board.Board;
import org.junit.Before;
import org.junit.Test;

import game.piece.Pawn;
import game.piece.Queen;
import game.position.Position;

import static org.junit.Assert.*;

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
        assertFalse(wQueen.isMoved());
        assertTrue(wQueen.isWhite());
        assertEquals(qW, wQueen.getPosition());
        assertEquals(b, wQueen.getBoard());

        Queen bQueen = new Queen(false, qB, b);
        assertFalse(bQueen.isMoved());
        assertFalse(bQueen.isWhite());
        assertEquals(qB, bQueen.getPosition());
        assertEquals(b, bQueen.getBoard());
    }

    @Test
    public void queenMoveStartingPositionTest() {
        Queen wQueen = new Queen(true, qW, b);

        assertEquals(21, wQueen.getLegalMove().size());
        assertTrue(wQueen.getLegalMove().contains(new Position("h1")));
        assertTrue(wQueen.getLegalMove().contains(new Position("h5")));
        assertTrue(wQueen.getLegalMove().contains(new Position("d8")));
        assertTrue(wQueen.getLegalMove().contains(new Position("a4")));
        assertTrue(wQueen.getLegalMove().contains(new Position("a1")));

        Queen bQueen = new Queen(false, qB, b);

        assertEquals(21, bQueen.getLegalMove().size());
        assertTrue(bQueen.getLegalMove().contains(new Position("a8")));
        assertTrue(bQueen.getLegalMove().contains(new Position("a5")));
        assertTrue(bQueen.getLegalMove().contains(new Position("d1")));
        assertTrue(bQueen.getLegalMove().contains(new Position("h4")));
        assertTrue(bQueen.getLegalMove().contains(new Position("h8")));
    }

    @Test
    public void queenInCenterOfBoardTest() {

        Queen wQ = new Queen(true, new Position("e5"), b);

        assertEquals(27, wQ.getLegalMove().size());
        assertTrue(wQ.getLegalMove().contains(new Position("a5"))); // end points
        assertTrue(wQ.getLegalMove().contains(new Position("b8")));
        assertTrue(wQ.getLegalMove().contains(new Position("e8")));
        assertTrue(wQ.getLegalMove().contains(new Position("h8")));
        assertTrue(wQ.getLegalMove().contains(new Position("h5")));
        assertTrue(wQ.getLegalMove().contains(new Position("h2")));
        assertTrue(wQ.getLegalMove().contains(new Position("e1")));
        assertTrue(wQ.getLegalMove().contains(new Position("a1")));

        Queen bQ = new Queen(true, new Position("c2"), b);

        assertEquals(23, bQ.getLegalMove().size());
        assertTrue(bQ.getLegalMove().contains(new Position("a2"))); // end points
        assertTrue(bQ.getLegalMove().contains(new Position("a4")));
        assertTrue(bQ.getLegalMove().contains(new Position("c8")));
        assertTrue(bQ.getLegalMove().contains(new Position("h7")));
        assertTrue(bQ.getLegalMove().contains(new Position("h2")));
        assertTrue(bQ.getLegalMove().contains(new Position("d1")));
        assertTrue(bQ.getLegalMove().contains(new Position("c1")));
        assertTrue(bQ.getLegalMove().contains(new Position("b1")));
    }

    @Test
    public void queenMoveObstructed() {

        Queen q = new Queen(true, new Position("a1"), b); // corner

        new Pawn(true, new Position("a2"), b);
        new Pawn(true, new Position("b1"), b);
        new Pawn(true, new Position("b2"), b);

        assertEquals(0, q.getLegalMove().size());

        Queen q2 = new Queen(true, new Position("c8"), b); // edge
        new Pawn(true, new Position("c7"), b);
        new Pawn(true, new Position("b8"), b);
        new Pawn(true, new Position("d8"), b);
        new Pawn(true, new Position("b7"), b);
        new Pawn(true, new Position("d7"), b);

        assertEquals(0, q2.getLegalMove().size());

        Queen q3 = new Queen(true, new Position("h4"), b);
        new Pawn(true, new Position("h5"), b);
        new Pawn(true, new Position("h3"), b);
        new Pawn(true, new Position("g3"), b);
        new Pawn(true, new Position("g4"), b);
        new Pawn(true, new Position("g5"), b);

        assertEquals(0, q3.getLegalMove().size());
    }

    @Test
    public void queenMoveSomeObstructed() {

        Queen q = new Queen(true, new Position("a1"), b); // expose one diagonal

        new Pawn(true, new Position("a2"), b);
        new Pawn(true, new Position("b1"), b);

        assertEquals(7, q.getLegalMove().size());

        Queen q1 = new Queen(true, new Position("f1"), b); // expose one diagonal

        new Pawn(true, new Position("e2"), b);

        assertEquals(14, q1.getLegalMove().size());

        Queen q2 = new Queen(false, new Position("c5"), b); // expose only cross

        new Pawn(false, new Position("a5"), b);
        new Pawn(false, new Position("b4"), b);
        new Pawn(false, new Position("c3"), b);
        new Pawn(false, new Position("d4"), b);
        new Pawn(false, new Position("e5"), b);
        new Pawn(false, new Position("d6"), b);
        new Pawn(false, new Position("c7"), b);
        new Pawn(false, new Position("b6"), b);

        assertEquals(4, q2.getLegalMove().size());
    }

    @Test
    public void queenSomeCapturable() {

        Queen q1 = new Queen(true, new Position("h1"), b);

        new Pawn(false, new Position("h2"), b);
        new Pawn(false, new Position("g1"), b);

        assertEquals(9, q1.getLegalMove().size());

        Queen q2 = new Queen(true, new Position("h8"), b);

        new Pawn(false, new Position("h7"), b);
        new Pawn(true, new Position("g8"), b);

        assertEquals(8, q2.getLegalMove().size());

        Queen q3 = new Queen(false, new Position("c5"), b); // expose only cross

        new Pawn(true, new Position("b4"), b);
        new Pawn(true, new Position("b6"), b);
        new Pawn(true, new Position("d4"), b);
        new Pawn(true, new Position("d6"), b);
        new Pawn(true, new Position("b5"), b);
        new Pawn(true, new Position("c6"), b);
        new Pawn(false, new Position("d5"), b);
        new Pawn(false, new Position("c4"), b);

        assertEquals(6, q3.getLegalMove().size());

        Board b1 = new Board();

        Queen q4 = new Queen(true, new Position("a5"), b1);

        new Pawn(false, new Position("b6"), b1);
        new Pawn(true, new Position("b5"), b1);
        new Pawn(false, new Position("b4"), b1);
        new Pawn(false, new Position("a3"), b1);

        assertEquals(7, q4.getLegalMove().size());
    }

    @Test
    public void queenFullyObstructed() {

        Queen q = new Queen(false, new Position("a1"), b); // non capturable

        new Pawn(false, new Position("a2"), b);
        new Pawn(false, new Position("b1"), b);
        new Queen(false, new Position("b2"), b);

        assertEquals(0, q.getLegalMove().size());

        Queen q1 = new Queen(true, new Position("h4"), b); // one side all capturable

        new Pawn(false, new Position("h5"), b);
        new Pawn(false, new Position("h3"), b);
        new Queen(false, new Position("g5"), b);
        new Queen(false, new Position("g4"), b);
        new Queen(false, new Position("g3"), b);

        assertEquals(5, q1.getLegalMove().size());

        Queen q2 = new Queen(true, new Position("c5"), b); // center obstructed

        new Pawn(false, new Position("b4"), b);
        new Pawn(false, new Position("b5"), b);
        new Pawn(false, new Position("b6"), b);
        new Pawn(false, new Position("d6"), b);
        new Pawn(false, new Position("d5"), b);
        new Pawn(false, new Position("d4"), b);
        new Pawn(false, new Position("c6"), b);
        new Pawn(false, new Position("c4"), b);

        assertEquals(8, q2.getLegalMove().size());
    }
}

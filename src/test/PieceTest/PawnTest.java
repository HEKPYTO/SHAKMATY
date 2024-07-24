package test.PieceTest;

import game.board.Board;
import org.junit.Before;
import org.junit.Test;

import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;

import static org.junit.Assert.*;

public class PawnTest {

    Position posWhite;
    Position posBlack;
    Board b;

    @Before
    public void setUp() {
        posWhite = new Position("d2");
        posBlack = new Position("d7");

        b = new Board();
    }

    @Test
    public void pawnConstructorTest() {

        Piece p2 = new Pawn(false, posBlack, b);

        assertFalse(p2.isWhite());
        assertEquals(posBlack, p2.getPosition());
        assertEquals(p2.getBoard(), b);
        assertFalse(p2.isMoved());

    }

    @Test
    public void pawnLegalMoveTest() {

        Pawn p1 = new Pawn(true, posWhite, b); // d2 -> d3, d4

        assertTrue(p1.getLegalMove().contains(new Position("d3")));
        assertTrue(p1.getLegalMove().contains(new Position("d4")));
        assertEquals(2, p1.getLegalMove().size());

        Position e3 = new Position("e3");
        Pawn p2 = new Pawn(true, e3, b); // e3 -> e4
        p2.hadMoved();

        assertTrue(p2.getLegalMove().contains(new Position("e4")));
        assertEquals(1, p2.getLegalMove().size());

        Pawn p3 = new Pawn(false, posBlack, b); // d7 -> d6, d5

        assertTrue(p3.getLegalMove().contains(new Position("d6")));
        assertTrue(p3.getLegalMove().contains(new Position("d5")));
        assertEquals(2, p3.getLegalMove().size());

        Position e6 = new Position("e6"); // d6 -> d5
        Pawn p4 = new Pawn(false, e6, b);
        p4.hadMoved();

        assertTrue(p4.getLegalMove().contains(new Position("e5")));
        assertEquals(1, p4.getLegalMove().size());

    }

    @Test
    public void pawnMoveObstructed() {

        // White obstruct Start Single Pawn Move
        Position a2 = new Position("a2");
        Position a3 = new Position("a3");
        Pawn pw1 = new Pawn(true, a2, b);
        Pawn pb1 = new Pawn(false, a3, b);

        assertEquals(0, pw1.getLegalMove().size());
        assertEquals(0, pb1.getLegalMove().size());

        // White obstruct Start Double Pawn Move
        Position c2 = new Position("c2");
        Position c4 = new Position("c4");
        Pawn pw2 = new Pawn(true, c2, b);
        Pawn pb2 = new Pawn(false, c4, b);

        assertTrue(pw2.getLegalMove().contains(new Position("c3")));
        assertEquals(1, pw2.getLegalMove().size());
        assertTrue(pb2.getLegalMove().contains(new Position("c3")));
        assertEquals(1, pb2.getLegalMove().size());

        // White obstruct other Square Move
        Position e3 = new Position("e3");
        Position e4 = new Position("e4");
        Pawn pw3 = new Pawn(true, e3, b);
        Pawn pb3 = new Pawn(false, e4, b);

        assertEquals(0, pw3.getLegalMove().size());
        assertEquals(0, pb3.getLegalMove().size());

        // Black obstruct Start Single Pawn Move
        Position h7 = new Position("h7");
        Position h6 = new Position("h6");
        Pawn pw4 = new Pawn(false, h7, b);
        Pawn pb4 = new Pawn(true, h6, b);

        assertEquals(0, pw4.getLegalMove().size());
        assertEquals(0, pb4.getLegalMove().size());

        // Black obstruct Start Double Pawn Move
        Position f7 = new Position("f7");
        Position f5 = new Position("f5");
        Pawn pw5 = new Pawn(false, f7, b);
        Pawn pb5 = new Pawn(true, f5, b);

        assertTrue(pw5.getLegalMove().contains(new Position("f6")));
        assertEquals(1, pw5.getLegalMove().size());
        assertTrue(pb5.getLegalMove().contains(new Position("f6")));
        assertEquals(1, pb5.getLegalMove().size());

        // Black obstruct other Square Move
        Position d6 = new Position("d6");
        Position d5 = new Position("d5");
        Pawn pw6 = new Pawn(false, d6, b);
        Pawn pb6 = new Pawn(true, d5, b);

        assertEquals(0, pw6.getLegalMove().size());
        assertEquals(0, pb6.getLegalMove().size());
    }

    @Test
    public void pawnCaptureMove() {

        // Testing white pawn capturing black piece
        Board board = new Board();
        Pawn wp = new Pawn(true, new Position("a2"), board);
        Pawn bp1 = new Pawn(false, new Position("a3"), board);
        Pawn bp2 = new Pawn(false, new Position("b3"), board);
        bp1.hadMoved(); // simulated moved pawns
        bp2.hadMoved();

        assertEquals(1, wp.getLegalMove().size());
        assertTrue(wp.getLegalMove().contains(new Position("b3")));

        assertEquals(0, bp1.getLegalMove().size());

        assertEquals(2, bp2.getLegalMove().size());
        assertTrue(bp2.getLegalMove().contains(new Position("a2")));

        // Testing black pawn capturing white piece
        Board board1 = new Board();
        Pawn bp = new Pawn(false, new Position("h7"), board1);
        Pawn wp1 = new Pawn(true, new Position("h6"), board1);
        Pawn wp2 = new Pawn(true, new Position("g6"), board1);
        wp1.hadMoved(); // simulated moved pawn
        wp2.hadMoved();

        assertEquals(1, bp.getLegalMove().size());
        assertTrue(bp.getLegalMove().contains(new Position("g6")));

        assertEquals(0, wp1.getLegalMove().size());

        assertEquals(2, wp2.getLegalMove().size());
        assertTrue(wp2.getLegalMove().contains(new Position("h7")));

        // Testing white pawn capturing multiple black pieces
        Board board2 = new Board();
        Pawn wp3 = new Pawn(true, new Position("b3"), board2);
        new Pawn(false, new Position("a4"), board2);
        new Pawn(false, new Position("b4"), board2);
        new Pawn(false, new Position("c4"), board2);

        assertEquals(2, wp3.getLegalMove().size());
        assertTrue(wp3.getLegalMove().contains(new Position("a4")));
        assertTrue(wp3.getLegalMove().contains(new Position("c4")));

        // Testing black pawn capturing multiple white pieces
        Board board3 = new Board();
        Pawn bp6 = new Pawn(false, new Position("g7"), board3);
        Pawn wp4 = new Pawn(true, new Position("f6"), board3);
        Pawn wp5 = new Pawn(true, new Position("h6"), board3);
        wp4.hadMoved();
        wp5.hadMoved();

        assertEquals(4, bp6.getLegalMove().size());
        assertTrue(bp6.getLegalMove().contains(new Position("f6")));
        assertTrue(bp6.getLegalMove().contains(new Position("h6")));

        assertEquals(2, wp4.getLegalMove().size());
        assertTrue(wp4.getLegalMove().contains(new Position("f7")));
        assertTrue(wp4.getLegalMove().contains(new Position("g7")));

        assertEquals(2, wp5.getLegalMove().size());
        assertTrue(wp5.getLegalMove().contains(new Position("g7")));
        assertTrue(wp5.getLegalMove().contains(new Position("h7")));
    }

    @Test
    public void pawnSameColorCantCapture() {

        // White Piece
        Pawn d2 = new Pawn(true, new Position("d2"), b);
        new Pawn(true, new Position("e3"), b);

        assertEquals(2, d2.getLegalMove().size());
        assertFalse(d2.getLegalMove().contains(new Position("e3")));
        assertFalse(d2.getLegalMove().contains(new Position("c3")));

        assertTrue(d2.getLegalMove().contains(new Position("d3")));
        assertTrue(d2.getLegalMove().contains(new Position("d4")));

        // Black Piece
        Pawn e7 = new Pawn(false, new Position("e7"), b);
        new Pawn(false, new Position("c6"), b);

        assertEquals(2, d2.getLegalMove().size());
        assertFalse(e7.getLegalMove().contains(new Position("c6")));
        assertFalse(e7.getLegalMove().contains(new Position("d6")));

        assertTrue(e7.getLegalMove().contains(new Position("e6")));
        assertTrue(e7.getLegalMove().contains(new Position("e5")));
    }

    @Test
    public void pawnEnPassantFlag() {
        Pawn pawn = new Pawn(true, new Position("a2"), b);
        pawn.setPassantCapture(false);
        assertFalse(pawn.canPassantCapture());

        // Set the en passant flag
        pawn.setPassantCapture(true);
        assertTrue(pawn.canPassantCapture());
    }

    @Test
    public void pawnEnPassantMoveTest() { // En Passant turns implement in board logic

        // White
        // Center case
        Board board1 = new Board();
        Pawn e5 = new Pawn(true, new Position("e5"), board1);
        Pawn d5 = new Pawn(false, new Position("d5"), board1);
        e5.hadMoved();
        d5.hadMoved();
        d5.setPassantCapture(true);

        assertEquals(2, e5.getLegalMove().size());
        assertTrue(e5.getLegalMove().contains(new Position("e6")));

        // Corner Case Left
        Board board2 = new Board();
        Pawn a5 = new Pawn(true, new Position("a5"), board2);
        Pawn b5 = new Pawn(false, new Position("b5"), board2);
        a5.hadMoved();
        b5.hadMoved();
        b5.setPassantCapture(true);

        assertEquals(2, a5.getLegalMove().size());
        assertTrue(a5.getLegalMove().contains(new Position("b6")));

        // Corner Case Right
        Board board3 = new Board();
        Pawn h5 = new Pawn(true, new Position("h5"), board3);
        Pawn g5 = new Pawn(false, new Position("g5"), board3);
        h5.hadMoved();
        g5.hadMoved();
        g5.setPassantCapture(true);

        assertEquals(2, h5.getLegalMove().size());
        assertTrue(h5.getLegalMove().contains(new Position("h6")));

        // Black
        // Center case
        Board board4 = new Board();
        Pawn e4 = new Pawn(false, new Position("e4"), board4);
        Pawn d4 = new Pawn(true, new Position("d4"), board4);
        e4.hadMoved();
        d4.hadMoved();
        d4.setPassantCapture(true);

        assertEquals(2, e4.getLegalMove().size());
        assertTrue(e4.getLegalMove().contains(new Position("e3")));

        // Corner Case Left
        Board board5 = new Board();
        Pawn a4 = new Pawn(false, new Position("a4"), board5);
        Pawn b4 = new Pawn(true, new Position("b4"), board5);
        a4.hadMoved();
        b4.hadMoved();
        b4.setPassantCapture(true);

        assertEquals(2, a4.getLegalMove().size());
        assertTrue(a4.getLegalMove().contains(new Position("a3")));

        // Corner Case Right
        Board board6 = new Board();
        Pawn h4 = new Pawn(false, new Position("h4"), board6);
        Pawn g4 = new Pawn(true, new Position("g4"), board6);
        h4.hadMoved();
        g4.hadMoved();
        g4.setPassantCapture(true);

        assertEquals(2, h4.getLegalMove().size());
        assertTrue(h4.getLegalMove().contains(new Position("h3")));
    }

    @Test
    public void pawnPromotion() {
        // White pawn at promotion row
        Pawn whitePawn = new Pawn(true, new Position("a8"), b);
        assertTrue(whitePawn.canPromote());

        // Black pawn at promotion row
        Pawn blackPawn = new Pawn(false, new Position("h1"), b);
        assertTrue(blackPawn.canPromote());
    }

    @Test
    public void testPawnCantPromotion() {
        // White pawn at promotion row
        Pawn whitePawn = new Pawn(true, new Position("b7"), b);
        assertFalse(whitePawn.canPromote());

        // Black pawn at promotion row
        Pawn blackPawn = new Pawn(false, new Position("f5"), b);
        assertFalse(blackPawn.canPromote());
    }
}

package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;

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

        Piece p1 = new Pawn(true, posWhite);

        assert(p1.isWhite());
        assertEquals(posWhite, p1.getPos());
        assertNull(p1.getBoard());
        assertTrue(!p1.isMovable());

    }

    @Test
    public void pawnConstructorWithBoardTest() {

        Piece p2 = new Pawn(false, posBlack, b);

        assert(!p2.isWhite());
        assertEquals(posBlack, p2.getPos());
        assertEquals(p2.getBoard(), b);
        assertTrue(!p2.isMovable());

    }

    @Test
    public void pawnLegalMoveTest() {

        Pawn p1 = new Pawn(true, posWhite, b);
        p1.legalMove();

        assertTrue(p1.getLegalMove().contains(new Position("d3"))); 
        assertTrue(p1.getLegalMove().contains(new Position("d4")));
        assertEquals(2, p1.getLegalMove().size());

        Position e3 = new Position("e3");
        Pawn p2 = new Pawn(true, e3, b);
        p2.legalMove();

        assertTrue(p2.getLegalMove().contains(new Position("e4")));
        assertEquals(1, p2.getLegalMove().size());

        Pawn p3 = new Pawn(false, posBlack, b);
        p3.legalMove();

        assertTrue(p3.getLegalMove().contains(new Position("d6"))); 
        assertTrue(p3.getLegalMove().contains(new Position("d5")));
        assertEquals(2, p3.getLegalMove().size());

        Position e6 = new Position("e6");
        Pawn p4 = new Pawn(false, e6, b);
        p4.legalMove();

        assertTrue(p4.getLegalMove().contains(new Position("e5")));
        assertEquals(1, p4.getLegalMove().size());

    }

    @Test
    public void PawnMoveObstructed() {

        // White obstruct Start Single Pawn Move
        Position a2 = new Position("a2");
        Position a3 = new Position("a3");
        Pawn pw1 = new Pawn(true, a2, b);
        Pawn pb1 = new Pawn(false, a3, b);
        pw1.legalMove();
        pb1.legalMove();

        assertEquals(0, pw1.getLegalMove().size());
        assertEquals(0, pb1.getLegalMove().size());

        // White obstruct Start Double Pawn Move
        Position c2 = new Position("c2");
        Position c4 = new Position("c4");
        Pawn pw2 = new Pawn(true, c2, b);
        Pawn pb2 = new Pawn(false, c4, b);
        pw2.legalMove();
        pb2.legalMove();

        assertTrue(pw2.getLegalMove().contains(new Position("c3")));
        assertEquals(1, pw2.getLegalMove().size());
        assertTrue(pb2.getLegalMove().contains(new Position("c3")));
        assertEquals(1, pb2.getLegalMove().size());

        // White obstruct other Square Move
        Position e3 = new Position("e3");
        Position e4 = new Position("e4");
        Pawn pw3 = new Pawn(true, e3, b);
        Pawn pb3 = new Pawn(false, e4, b);
        pw3.legalMove();
        pb3.legalMove();

        assertEquals(0, pw3.getLegalMove().size());
        assertEquals(0, pb3.getLegalMove().size());
        
        // Black obstruct Start Single Pawn Move
        Position h7 = new Position("h7");
        Position h6 = new Position("h6");
        Pawn pw4 = new Pawn(false, h7, b);
        Pawn pb4 = new Pawn(true, h6, b);
        pw4.legalMove();
        pb4.legalMove();

        assertEquals(0, pw4.getLegalMove().size());
        assertEquals(0, pb4.getLegalMove().size());

        // Black obstruct Start Double Pawn Move
        Position f7 = new Position("f7");
        Position f5 = new Position("f5");
        Pawn pw5 = new Pawn(false, f7, b);
        Pawn pb5 = new Pawn(true, f5, b);
        pw5.legalMove();
        pb5.legalMove();

        assertTrue(pw5.getLegalMove().contains(new Position("f6")));
        assertEquals(1, pw5.getLegalMove().size());
        assertTrue(pb5.getLegalMove().contains(new Position("f6")));
        assertEquals(1, pb5.getLegalMove().size());

        // Black obstruct other Square Move
        Position d6 = new Position("d6");
        Position d5 = new Position("d5");
        Pawn pw6 = new Pawn(false, d6, b);
        Pawn pb6 = new Pawn(true, d5, b);
        pw6.legalMove();
        pb6.legalMove();

        assertEquals(0, pw6.getLegalMove().size());
        assertEquals(0, pb6.getLegalMove().size());
    }


}

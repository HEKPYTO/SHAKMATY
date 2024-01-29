package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testPawnMovedStatus() {
        Pawn pawn = new Pawn(true, new Position("a2"));
        assertFalse(pawn.isMoved()); 
        
        // Move the pawn
        pawn.move(new Position("a3"));
        assertTrue(pawn.isMoved()); 
    }

    @Test
    public void pawnLegalMoveTest() {

        Pawn p1 = new Pawn(true, posWhite, b);

        assertTrue(p1.getLegalMove().contains(new Position("d3"))); 
        assertTrue(p1.getLegalMove().contains(new Position("d4")));
        assertEquals(2, p1.getLegalMove().size());

        Position e3 = new Position("e3");
        Pawn p2 = new Pawn(true, e3, b);

        assertTrue(p2.getLegalMove().contains(new Position("e4")));
        assertEquals(1, p2.getLegalMove().size());

        Pawn p3 = new Pawn(false, posBlack, b);

        assertTrue(p3.getLegalMove().contains(new Position("d6"))); 
        assertTrue(p3.getLegalMove().contains(new Position("d5")));
        assertEquals(2, p3.getLegalMove().size());

        Position e6 = new Position("e6");
        Pawn p4 = new Pawn(false, e6, b);

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
    public void testPawnCaptureMove() {

        // Testing white pawn capturing black piece
        Board board = new Board();
        Pawn wp = new Pawn(true, new Position("a2"), board);
        Pawn bp1 = new Pawn(false, new Position("a3"), board);
        Pawn bp2 = new Pawn(false, new Position("b3"), board);

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

        assertEquals(1, bp.getLegalMove().size());
        assertTrue(bp.getLegalMove().contains(new Position("g6")));

        assertEquals(0, wp1.getLegalMove().size());

        assertEquals(2, wp2.getLegalMove().size());
        assertTrue(wp2.getLegalMove().contains(new Position("h7")));

        // Testing white pawn capturing multiple black pieces
        Board board2 = new Board();
        Pawn wp3 = new Pawn(true, new Position("b3"), board2);
        Pawn bp3 = new Pawn(false, new Position("a4"), board2);
        Pawn bp4 = new Pawn(false, new Position("b4"), board2);
        Pawn bp5 = new Pawn(false, new Position("c4"), board2);

        assertEquals(2, wp3.getLegalMove().size());
        assertTrue(wp3.getLegalMove().contains(new Position("a4")));
        assertTrue(wp3.getLegalMove().contains(new Position("c4")));

        // Testing black pawn capturing multiple white pieces
        Board board3 = new Board();
        Pawn bp6 = new Pawn(false, new Position("g7"), board3);
        Pawn wp4 = new Pawn(true, new Position("f6"), board3);
        Pawn wp5 = new Pawn(true, new Position("h6"), board3);

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
    public void sameColorCantCapturePiece() {
        
        // White Piece
        Pawn d2 = new Pawn(true, new Position("d2"), b);
        Pawn e3 = new Pawn(true, new Position("e3"), b);

        assertEquals(2, d2.getLegalMove().size());
        assertTrue(!d2.getLegalMove().contains(new Position("e3")));

        assertTrue(d2.getLegalMove().contains(new Position("d3")));
        assertTrue(d2.getLegalMove().contains(new Position("d4")));

        // Black Piece
        Pawn e7 = new Pawn(false, new Position("e7"), b);
        Pawn c6 = new Pawn(false, new Position("c6"), b);

        assertEquals(2, d2.getLegalMove().size());
        assertTrue(!e7.getLegalMove().contains(new Position("c6")));

        assertTrue(e7.getLegalMove().contains(new Position("e6")));
        assertTrue(e7.getLegalMove().contains(new Position("e5")));
    }

    @Test
    public void testPawnEnPassantFlag() {
        Pawn pawn = new Pawn(true, new Position("a2"));
        assertFalse(pawn.isPassant()); 
        
        // Set the en passant flag
        pawn.setPassant(true);
        assertTrue(pawn.isPassant()); 
    }

    @Test
    public void enPassantMoveTest() { // En Passant turns will be implement in gameControl logic

        // White
        // Center case
        Pawn e5 = new Pawn(true, new Position("e5"), b);
        Pawn d5 = new Pawn(false, new Position("d5"), b);
        d5.setPassant(true);

        assertEquals(2, e5.getLegalMove().size());
        assertTrue(e5.getLegalMove().contains(new Position("e6")));

        // Corner Case Left
        Pawn a5 = new Pawn(true, new Position("a5"), b);
        Pawn b5 = new Pawn(false, new Position("b5"), b);
        b5.setPassant(true);

        assertEquals(2, a5.getLegalMove().size());
        assertTrue(a5.getLegalMove().contains(new Position("b6")));

        // Corner Case Right
        Pawn h5 = new Pawn(true, new Position("h5"), b);
        Pawn g5 = new Pawn(false, new Position("g5"), b);
        g5.setPassant(true);

        assertEquals(2, h5.getLegalMove().size());
        assertTrue(h5.getLegalMove().contains(new Position("h6")));

        // Black
        // Center case
        Pawn e4 = new Pawn(false, new Position("e4"), b);
        Pawn d4 = new Pawn(true, new Position("d4"), b);
        d4.setPassant(true);

        assertEquals(2, e4.getLegalMove().size());
        assertTrue(e4.getLegalMove().contains(new Position("e3")));

        // Corner Case Left
        Pawn a4 = new Pawn(false, new Position("a4"), b);
        Pawn b4 = new Pawn(true, new Position("b4"), b);
        b4.setPassant(true);

        assertEquals(2, a4.getLegalMove().size());
        assertTrue(a4.getLegalMove().contains(new Position("a3")));

        // Corner Case Right
        Pawn h4 = new Pawn(false, new Position("h4"), b);
        Pawn g4 = new Pawn(true, new Position("g4"), b);
        g4.setPassant(true);

        assertEquals(2, h4.getLegalMove().size());
        assertTrue(h4.getLegalMove().contains(new Position("h3")));
    }

    @Test
    public void testPawnPromotion() {
        // White pawn at promotion row
        Pawn whitePawn = new Pawn(true, new Position("a8"));
        assertTrue(whitePawn.canPromote()); 
    
        // Black pawn at promotion row
        Pawn blackPawn = new Pawn(false, new Position("h1"));
        assertTrue(blackPawn.canPromote()); 
    }

    @Test
    public void testPawnCantPromotion() {
        // White pawn at promotion row
        Pawn whitePawn = new Pawn(true, new Position("b7"));
        assertTrue(!whitePawn.canPromote()); 
    
        // Black pawn at promotion row
        Pawn blackPawn = new Pawn(false, new Position("f5"));
        assertTrue(!blackPawn.canPromote()); 
    }
}

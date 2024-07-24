package test.PieceTest;

import game.board.Board;
import game.util.Constant;
import org.junit.Before;
import org.junit.Test;

import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;

import static org.junit.Assert.*;


public class PieceTest {

    Position posWhite;
    Position posBlack;
    Board b;
    
    @Before
    public void setUp() {
        posWhite = new Position(0, 1);
        posBlack = new Position(0, Constant.COL - 1);

        b = new Board();
    }

    @Test
    public void constructorWhiteTestWithBoard() {

        Piece p1 = new Pawn(true, posWhite, b);

        assertTrue(p1.isWhite());
        assertEquals(posWhite, p1.getPosition());
        assertEquals(b, p1.getBoard());
        assertEquals(p1, b.getPiece(posWhite));
        assertFalse(p1.isMoved());
    }

    @Test
    public void constructorBlackTestWithBoard() {

        Piece p2 = new Pawn(false, posBlack, b);

        assertFalse(p2.isWhite());
        assertEquals(posBlack, p2.getPosition());
        assertEquals(b, p2.getBoard());
        assertEquals(p2, b.getPiece(posBlack));
        assertFalse(p2.isMoved());
    }

    @Test
    public void equalsTest() {
        Board b1 = new Board();
        Board b2 = new Board();

        Piece p1 = new Pawn(true, posWhite, b1);
        Piece p2 = new Pawn(true, posWhite, b2);

        assertTrue(p1.isWhite());
        assertEquals(posWhite, p1.getPosition());
        assertEquals(b1, p1.getBoard());

        assertTrue(p2.isWhite());
        assertEquals(posWhite, p2.getPosition());
        assertEquals(b2, p2.getBoard());

        assertEquals(p1, p2);
    }

}

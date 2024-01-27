package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.constant.Constant;
import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;


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
    public void constructorWhiteTest() {

        Piece p1 = new Pawn(true, posWhite);

        assert(p1.isWhite());
        assertEquals(posWhite, p1.getPos());
        assertNull(p1.getBoard());
        assertTrue(!p1.isMovable());
        
    }

    @Test
    public void constructorBlackTest() {

        Piece p2 = new Pawn(false, posBlack);

        assert(!p2.isWhite());
        assertEquals(posBlack, p2.getPos());
        assertNull(p2.getBoard());
        assertTrue(!p2.isMovable());
        
    }

    @Test
    public void constructorWhiteTestWithBoard() {

        Piece p1 = new Pawn(true, posWhite, b);

        assert(p1.isWhite());
        assertEquals(posWhite, p1.getPos());
        assertEquals(b, p1.getBoard());
        assertTrue(!p1.isMovable());
    }

    @Test
    public void constructorBlackTestWithBoard() {

        Piece p2 = new Pawn(false, posBlack, b);

        assert(!p2.isWhite());
        assertEquals(posBlack, p2.getPos());
        assertEquals(b, p2.getBoard());
        assertTrue(!p2.isMovable());
    }

}

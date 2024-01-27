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

public class PawnTest {
    
    Position posWhite;
    Position posBlack;
    Board b;
    
    @Before
    public void setUp() {
        posWhite = new Position(4, 1);
        posBlack = new Position(4, Constant.COL - 1);

        b = new Board();
    }

    @Test
    public void pawnConstructorTest1() {

        Piece p1 = new Pawn(true, posWhite);

        assert(p1.isWhite());
        assertEquals(posWhite, p1.getPos());
        assertNull(p1.getBoard());
        assertTrue(!p1.isMovable());

    }

    @Test
    public void pawnConstructorTest2() {

        Piece p2 = new Pawn(false, posBlack, b);

        assert(!p2.isWhite());
        assertEquals(posBlack, p2.getPos());
        assertEquals(p2.getBoard(), b);
        assertTrue(!p2.isMovable());

    }

    @Test
    public void getLegalPawnMove1() {

        Piece p = new Pawn(true, posWhite, b);

        assert(p.isWhite());
        assertEquals(posWhite, p.getPos());
        assertEquals(b, p.getBoard());

        p.legalMove();

        System.out.println(p.getLegalMove());


    }
}

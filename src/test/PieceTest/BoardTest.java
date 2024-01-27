package test.PieceTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Pawn;
import game.piece.Queen;
import game.position.Position;

public class BoardTest {

    Board b;

    @Before
    public void setUp() {
        b = new Board();
    }
    
    @Test
    public void boardConstructorTest() {

    }

    @Test
    public void setPiecePlacementTest() {

        Position pos1 = new Position("h8");
        Pawn p1 = new Pawn(true, pos1);
        b.setPiece(p1);
        
        assertEquals(pos1.getX(), 7);
        assertEquals(pos1.getY(), 7);
        assertEquals(p1, b.getBoard().get(0).get(7));
        assertEquals(new Position("h8"), p1.getPos());

        Position pos2 = new Position("a1");
        Pawn p2 = new Pawn(true, pos2);
        b.setPiece(p2);
        
        assertEquals(pos2.getX(), 0);
        assertEquals(pos2.getY(), 0);
        assertEquals(p2, b.getBoard().get(7).get(0));

        Position pos3 = new Position("d4");
        Pawn p3 = new Pawn(false, pos3);
        b.setPiece(p3);

        assertEquals(pos3.getX(), 3);
        assertEquals(pos3.getY(), 3);
        assertEquals(p3, b.getBoard().get(4).get(3));

        Position pos4 = new Position("b1");
        Pawn p4 = new Pawn(false, pos4);
        b.setPiece(p4);

        assertEquals(pos4.getX(), 1);
        assertEquals(pos4.getY(), 0);
        assertEquals(p4, b.getBoard().get(7).get(1));

        Position pos5 = new Position("f4");
        Pawn p5 = new Pawn(true, pos5);
        b.setPiece(p5);

        assertEquals(pos5.getX(), 5);
        assertEquals(pos5.getY(), 3);
        assertEquals(p5, b.getBoard().get(4).get(5));
    }

    @Test
    public void getPieceFromBoardTest() {

        Position pos1 = new Position("d4");
        Pawn p1 = new Pawn(true, pos1);
        b.setPiece(p1);

        assertEquals(p1, b.getPiece(pos1));

        Position pos2 = new Position("e2");
        Pawn p2 = new Pawn(true, pos2);
        b.setPiece(p2);

        assertEquals(p2, b.getPiece(pos2));

        Position pos3 = new Position("a7");
        Pawn p3 = new Pawn(true, pos3);
        b.setPiece(p3);

        assertEquals(p3, b.getPiece(pos3));
    }
}

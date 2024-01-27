package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import game.position.Position;


public class PositionTest {

    @Test
    public void baseConstructorTest() {

        Position p = new Position(5, 0);
        assertEquals(p.getX(), 5);
        assertEquals(p.getY(), 0);
    }
    
    @Test
    public void testConstructorString() {

        Position pos1 = new Position("a1");
        assertEquals(0, pos1.getX());
        assertEquals(0, pos1.getY());

        Position pos2 = new Position("h8");
        assertEquals(7, pos2.getX());
        assertEquals(7, pos2.getY());

        Position pos3 = new Position("d4");
        assertEquals(3, pos3.getX());
        assertEquals(3, pos3.getY());

        Position pos4 = new Position("d5");
        assertEquals(3, pos4.getX());
        assertEquals(4, pos4.getY());

        Position pos5 = new Position("b1");
        assertEquals(1, pos5.getX());
        assertEquals(0, pos5.getY());

        Position pos6 = new Position("g7");
        assertEquals(6, pos6.getX());
        assertEquals(6, pos6.getY());
    }

    @Test
    public void testToString() {
        Position pos = new Position(3, 4);
        assertEquals("d5", pos.toString());

        Position pos2 = new Position(4, 3);
        assertEquals("e4", pos2.toString());
    }

    @Test
    public void testEquals() {
        Position pos1 = new Position(2, 3);
        Position pos2 = new Position(2, 3);
        Position pos3 = new Position(4, 5);

        assertTrue(pos1.equals(pos2));
        assertFalse(pos1.equals(pos3));
    }

    @Test
    public void testRookPosition() {

        Position shortCastleWhitePos = new Position("g1", "f1");
        assertEquals("g1", shortCastleWhitePos.toString());
        assertEquals("f1", shortCastleWhitePos.getRookPos().toString());
    
        Position longCastleWhitePos = new Position("c1", "d1");
        assertEquals("c1", longCastleWhitePos.toString());
        assertEquals("d1", longCastleWhitePos.getRookPos().toString());
    
        Position shortCastleBlackPos = new Position("g8", "f8");
        assertEquals("g8", shortCastleBlackPos.toString());
        assertEquals("f8", shortCastleBlackPos.getRookPos().toString());
    
        Position longCastleBlackPos = new Position("c8", "d8");
        assertEquals("c8", longCastleBlackPos.toString());
        assertEquals("d8", longCastleBlackPos.getRookPos().toString());
    }
    
    @Test
    public void testSetAndGetRookPosition() {
        Position pos = new Position("a1");
        Position rookPos = new Position("b1");
        pos.setRookPos(rookPos);
        assertEquals(rookPos, pos.getRookPos());
    }
    
    @Test
    public void testEdgeCases() {
        Position pos1 = new Position("a1");
        assertEquals(0, pos1.getX());
        assertEquals(0, pos1.getY());
    
        Position pos2 = new Position("h8");
        assertEquals(7, pos2.getX());
        assertEquals(7, pos2.getY());
    }
}

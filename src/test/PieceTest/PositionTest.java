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
        assertEquals(p.getRow(), 5);
        assertEquals(p.getCol(), 0);
    }
    
    @Test
    public void testConstructorString() {

        Position pos1 = new Position("a1");
        assertEquals(0, pos1.getRow());
        assertEquals(0, pos1.getCol());

        Position pos2 = new Position("h8");
        assertEquals(7, pos2.getRow());
        assertEquals(7, pos2.getCol());

        Position pos3 = new Position("d4");
        assertEquals(3, pos3.getRow());
        assertEquals(3, pos3.getCol());

        Position pos4 = new Position("d5");
        assertEquals(4, pos4.getRow());
        assertEquals(3, pos4.getCol());

        Position pos5 = new Position("b1");
        assertEquals(0, pos5.getRow());
        assertEquals(1, pos5.getCol());

        Position pos6 = new Position("g7");
        assertEquals(6, pos6.getRow());
        assertEquals(6, pos6.getCol());
    }

    @Test
    public void testToString() {
        Position pos = new Position(2, 2);
        assertEquals("c3", pos.toString());

        Position pos2 = new Position(4, 4);
        assertEquals("e5", pos2.toString());
    }

    @Test
    public void testAlphabetToString() {
        Position pos = new Position("d4");
        assertEquals("d4", pos.toString());

        Position pos2 = new Position("e5");
        assertEquals("e5", pos2.toString());
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
        assertEquals(0, pos1.getRow());
        assertEquals(0, pos1.getCol());

        Position pos2 = new Position("h8");
        assertEquals(7, pos2.getRow());
        assertEquals(7, pos2.getCol());

        Position pos3 = new Position("a8");
        assertEquals(7, pos3.getRow());
        assertEquals(0, pos3.getCol());

        Position pos4 = new Position("h1");
        assertEquals(0, pos4.getRow());
        assertEquals(7, pos4.getCol());
    }
}

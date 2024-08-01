package test.PositionTest;

import org.junit.Test;

import game.position.Position;

import static org.junit.Assert.*;


public class PositionTest {

    @Test
    public void baseConstructorTest() {

        Position p = new Position(5, 0);
        assertEquals(p.getRow(), 5);
        assertEquals(p.getCol(), 0);
    }

    @Test
    public void stringConstructorTest() {

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

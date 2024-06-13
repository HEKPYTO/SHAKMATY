package test.PieceTest;

import game.Board.Board;
import game.piece.Bishop;
import game.position.Position;

import org.junit.*;

import static org.junit.Assert.*;

public class BishopTest {

    Board b; // Board
    Position wdb; // White Dark Square Bishop
    Position wlb; // White Light Square Bishop
    Position bdb; // Black Dark Square Bishop
    Position blb; // Black Light Square Bishop

    @Before
    public void setup() {
        b = new Board();
        wdb = new Position("c8");
        wlb = new Position("f1");
        bdb = new Position("f8");
        blb = new Position("c1");
    }

    @Test
    public void bishopConstructorTest() {
        Bishop wDB = new Bishop(true, wdb, b);

        assertTrue(wDB.getColor());
        assertEquals(wdb, wDB.getPosition());
        assertEquals(b, wDB.getBoard());

        Bishop bDB = new Bishop(false, bdb, b);

        assertFalse(bDB.getColor());
        assertEquals(bdb, bDB.getPosition());
        assertEquals(b, bDB.getBoard());
    }

    @Test
    public void bishopMoveStartingPositionTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        assertEquals(7, wDB.getNextLegalMove().size());

        Bishop bDB = new Bishop(false, bdb, b);
        assertEquals(7, bDB.getNextLegalMove().size());
    }

    @Test
    public void bishopAtCenterTest() {
        Position middle_w = new Position("d4");
        Bishop wDB = new Bishop(true, middle_w, b);
        assertEquals(13, wDB.getNextLegalMove().size());

        Position middle_b = new Position("g4");
        Bishop bWB = new Bishop(false, middle_b, b);
        assertEquals(9, bWB.getNextLegalMove().size());
    }

    @Test
    public void bishopCaptureTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        new Bishop(false, new Position("f5"), b);
        assertEquals(5, wDB.getNextLegalMove().size());
    }

    @Test
    public void bishopCantCaptureTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        new Bishop(true, new Position("f5"), b);
        assertEquals(4, wDB.getNextLegalMove().size());
    }

    @Test
    public void bishopDiagonalMovementTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        assertEquals(7, wDB.getNextLegalMove().size());

        new Bishop(false, new Position("f5"), b);
        assertEquals(5, wDB.getNextLegalMove().size());

        Bishop bDB = new Bishop(false, new Position("a1"), b);
        assertEquals(7, bDB.getNextLegalMove().size());
    }

    @Test
    public void bishopEdgeCaseTest() {
        Bishop bDB = new Bishop(false, blb, b);
        assertEquals(7, bDB.getNextLegalMove().size());

        Bishop wLB = new Bishop(true, wlb, b);
        assertEquals(7, wLB.getNextLegalMove().size());
    }
}

package test.PieceTest;

import game.piece.Bishop;
import game.position.Position;
import game.board.Board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class BishopTest {

    Board b; // Board
    Position wdb; // White Dark Square Bishop
    Position wlb; // White Light Square Bishop
    Position bdb; // Black Dark Square Bishop
    Position blb; // Black Light Square Bisho

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

        assertTrue(wDB.getWhite());
        assertEquals(wdb, wDB.getPos());
        assertEquals(b, wDB.getBoard());

        Bishop bDB = new Bishop(false, bdb, b);

        assertTrue(!bDB.getWhite());
        assertEquals(bdb, bDB.getPos());
        assertEquals(b, bDB.getBoard());
    }

    @Test
    public void bishopMoveStartingPositonTest() {
        Bishop wDB = new Bishop(true, wdb, b);

        assertEquals(7, wDB.getLegalMove().size());

        Bishop bDB = new Bishop(false, bdb, b);

        assertEquals(7, bDB.getLegalMove().size());
    }

    @Test
    public void bishopAtCenterTest() {
        Position middle_w = new Position("d4");
        Bishop wDB = new Bishop(true, middle_w, b);
        assertEquals(13, wDB.getLegalMove().size());

        Position middle_b = new Position("g4");
        Bishop bWB = new Bishop(false, middle_b, b);
        assertEquals(9, bWB.getLegalMove().size());
    }

    @Test
    public void bishopDiagonalMovementTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        assertEquals(7, wDB.getLegalMove().size());

        new Bishop(false, new Position("f5"), b);
        assertEquals(5, wDB.getLegalMove().size());

        Bishop bDB = new Bishop(false, new Position("a1"), b);
        assertEquals(7, bDB.getLegalMove().size());
    }

    @Test
    public void bishopCaptureTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        new Bishop(false, new Position("f5"), b);
        assertEquals(5, wDB.getLegalMove().size());
    }

    @Test
    public void bishopEdgeCaseTest() {
        Bishop bDB = new Bishop(false, blb, b);
        assertEquals(7, bDB.getLegalMove().size());

        Bishop wLB = new Bishop(true, wlb, b);
        assertEquals(7, wLB.getLegalMove().size());
    }

    @Test
    public void bishopInvalidMoveTest() {
        Bishop wDB = new Bishop(true, wdb, b);
        new Bishop(true, new Position("e7"), b);
        assertEquals(wDB.getLegalMove().size(), 7);
    }

}

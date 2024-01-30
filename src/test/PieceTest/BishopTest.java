package test.PieceTest;
import game.piece.Bishop;
import game.position.Position;
import game.board.Board;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class BishopTest {

    Board b;            //Board
    Position wdb;     // White Dark Square Bishop
    Position wlb;     // White Light Square Bishop
    Position bdb;     // Black Dark Square Bishop
    Position blb;     // Black Light Square Bishop

    //Bishop W_D_B = new Bishop(true, new Position(2, 7));    // White Dark Square Bishop
    //Bishop W_L_B = new Bishop(true, new Position(5, 7));    // White Light Square Bishop 
    //Bishop B_D_B = new Bishop(false, new Position(2, 0));   // Black Dark Square Bishop 
    //Bishop B_L_B = new Bishop(false, new Position(5, 0));   // Black Light Square Bishop
    

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

}

package test.PieceTest;
import game.piece.Bishop;
import game.position.Position;
import game.board.Board;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class BishopTest {

    Bishop W_D_B = new Bishop(true, new Position(2, 7));    // White Dark Square Bishop
    Bishop W_L_B = new Bishop(true, new Position(5, 7));    // White Light Square Bishop 
    Bishop B_D_B = new Bishop(false, new Position(2, 0));   // Black Dark Square Bishop 
    Bishop B_L_B = new Bishop(false, new Position(5, 0));   // Black Light Square Bishop
    
    @Test
    public void test_GET() {
        Assert.assertEquals(W_D_B.getWhite(), true); // test get white
        Assert.assertEquals(W_D_B.getPos().getX(), 2); // test get X
        Assert.assertEquals(W_D_B.getPos().getY(), 7); // test get Y
    }

    @Test
    public void test_SET() {
        W_D_B.getPos().setX(5);
        W_D_B.getPos().setY(4);
        Assert.assertEquals(W_D_B.getPos().getX(), 5); // test set X
        Assert.assertEquals(W_D_B.getPos().getY(), 4); // test set Y
    }

}

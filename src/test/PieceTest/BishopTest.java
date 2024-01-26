package test.PieceTest;
import game.piece.Bishop;
import game.position.Position;
import game.board.Board;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class BishopTest {

    @Test
    public void test_WB() {
        Bishop W_B = new Bishop(true, new Position(0, 0));
        Assert.assertEquals(W_B.getWhite(), true); // test get white
        Assert.assertEquals(W_B.getPos().getX(), 0); // test get X
        Assert.assertEquals(W_B.getPos().getY(), 0); // test get Y
    }

    @Test
    public void test_board() {
        
    }

}

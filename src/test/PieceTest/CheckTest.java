package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.position.Position;
import game.util.Checked;

public class CheckTest {

    Board b;

    @Before
    public void setup() {
        b = new Board();
    }
    
    @Test
    public void testCheckConstructor() {

        Checked c = new Checked(new Position("e1"), b);

        assertEquals(new Position("e1"), c.getCurrent());
        assertEquals(c.getChecked().size(), 0);

    }
}

package test.PieceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.board.Board;
import game.piece.Bishop;
import game.piece.King;
import game.piece.Knight;
import game.piece.Pawn;
import game.piece.Queen;
import game.piece.Rook;
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

        new Pawn(true, new Position("e1"), b);
        Checked c = new Checked(new Position("e1"), b);

        assertEquals(new Position("e1"), c.getCurrent());
        assertEquals(c.getCheckedPosition().size(), 0);
        assertTrue(c.isWhite());

    }

    @Test
    public void getPawnCheck() {

        new Pawn(false, new Position("d2"), b); // one pawn check

        Checked c = new Checked(new Position("e1"), b, true);

        assertEquals(1, c.getCheckedPosition().size());
        assertTrue(c.getCheckedPosition().contains(new Position("d2")));

        new Pawn(true, new Position("e7"), b);
        new Pawn(true, new Position("c7"), b);

        Checked c1 = new Checked(new Position("d8"), b, false); // two pawn check

        assertEquals(2, c1.getCheckedPosition().size());
        assertTrue(c1.getCheckedPosition().contains(new Position("e7")));
        assertTrue(c1.getCheckedPosition().contains(new Position("c7")));

        Checked c2 = new Checked(new Position("d7"), b, false); // no pawn check

        assertEquals(0, c2.getCheckedPosition().size());

        Checked c3 = new Checked(new Position("d6"), b, false);

        assertEquals(0, c3.getCheckedPosition().size());
    }

    @Test
    public void getLCheck() {

        new Knight(false, new Position("c3"), b);

        Checked c1 = new Checked(new Position("d1"), b, true); // one knight

        assertEquals(1, c1.getCheckedPosition().size());
        assertTrue(c1.getCheckedPosition().contains(new Position("c3")));

        new Knight(true, new Position("e3"), b);

        assertEquals(1, c1.getCheckedPosition().size());
        assertTrue(c1.getCheckedPosition().contains(new Position("c3")));

        Checked c2 = new Checked(new Position("d2"), b, true); // one white and one black

        assertEquals(0, c2.getCheckedPosition().size());

        new Knight(false, new Position("b2"), b);

        Checked c3 = new Checked(new Position("d1"), b, true); // two white and one black

        assertEquals(2, c3.getCheckedPosition().size());
        assertTrue(c3.getCheckedPosition().contains(new Position("c3")));
        assertTrue(c3.getCheckedPosition().contains(new Position("b2")));

        Checked c4 = new Checked(new Position("f5"), b, false); // one knight

        assertEquals(1, c4.getCheckedPosition().size());
        assertTrue(c4.getCheckedPosition().contains(new Position("e3")));
    }

    @Test
    public void getDiagonalCheck() {

        Checked c = new Checked(new Position("e5"), b, true);

        assertEquals(0, c.getCheckedPosition().size()); // no piece

        new Bishop(false, new Position("h2"), b); // one black bishop

        assertEquals(1, c.getCheckedPosition().size());
        assertTrue(c.getCheckedPosition().contains(new Position("h2")));

        new Bishop(false, new Position("h8"), b);

        assertEquals(2, c.getCheckedPosition().size());
        assertTrue(c.getCheckedPosition().contains(new Position("h8"))); // two black bishop

        new Bishop(false, new Position("g7"), b);
        assertEquals(2, c.getCheckedPosition().size()); // block one black bishop with another one

        new Bishop(true, new Position("f6"), b);
        assertEquals(1, c.getCheckedPosition().size()); // white bishop block black bishop

        new Bishop(true, new Position("c3"), b);
        assertEquals(1, c.getCheckedPosition().size()); // white bishop with check

        Checked c1 = new Checked(new Position("b2"), b, false);
        assertEquals(1, c1.getCheckedPosition().size());

        Checked c2 = new Checked(new Position("g6"), b, false);
        assertEquals(0, c2.getCheckedPosition().size()); // no check
    }

    @Test
    public void getPlusCheck() {

        Checked c1 = new Checked(new Position("e4"), b, true);

        new Rook(false, new Position("h4"), b);
        new Rook(false, new Position("e8"), b);

        assertEquals(2, c1.getCheckedPosition().size());

        Checked c2 = new Checked(new Position("c4"), b, true);

        assertEquals(1, c2.getCheckedPosition().size());

        new Rook(false, new Position("c4"), b);

        assertEquals(3, c1.getCheckedPosition().size());

        assertEquals(1, c2.getCheckedPosition().size()); // not count itself

        new Rook(true, new Position("d4"), b);

        assertEquals(2, c1.getCheckedPosition().size()); // blocked

        assertEquals(0, c2.getCheckedPosition().size());

        Checked c3 = new Checked(new Position("e5"), b, false);

        assertEquals(0, c3.getCheckedPosition().size());

        Checked c4 = new Checked(new Position("e5"), b, true);

        assertEquals(1, c4.getCheckedPosition().size());

    }

    @Test
    public void getDiagonalPlusCheck() { // plus and diagonal are correct, this should be correct

        Checked c1 = new Checked(new Position("d4"), b, true);

        new Queen(false, new Position("h8"), b);

        assertEquals(c1.getCheckedPosition().size(), 1);
        assertTrue(c1.getCheckedPosition().contains(new Position("h8")));

        new Queen(true, new Position("h4"), b);

        assertEquals(c1.getCheckedPosition().size(), 1);

        new Queen(false, new Position("g4"), b);

        assertEquals(c1.getCheckedPosition().size(), 2);
        assertTrue(c1.getCheckedPosition().contains(new Position("g4")));

        Checked c2 = new Checked(new Position("g5"), b, false);

        assertEquals(c2.getCheckedPosition().size(), 1);

        new Queen(true, new Position("g7"), b);

        assertEquals(c1.getCheckedPosition().size(), 1);
        assertFalse(c1.getCheckedPosition().contains(new Position("h8")));
        assertFalse(c1.getCheckedPosition().contains(new Position("g7")));
    }

    @Test
    public void getSquareCheck() {

        new King(true, new Position("d5"), b);

        Checked c1 = new Checked(new Position("c4"), b, false);
        Checked c2 = new Checked(new Position("d4"), b, false);
        Checked c3 = new Checked(new Position("e4"), b, false);
        Checked c4 = new Checked(new Position("f4"), b, false);

        assertTrue(c1.getCheckedPosition().contains(new Position("d5")));
        assertTrue(c2.getCheckedPosition().contains(new Position("d5")));
        assertTrue(c3.getCheckedPosition().contains(new Position("d5")));
        assertFalse(c4.getCheckedPosition().contains(new Position("d5")));

        //idk should not break
    }
}

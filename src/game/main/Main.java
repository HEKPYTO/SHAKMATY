package game.main;



import game.board.Board;
import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {

        // Position posWhite = new Position("e2");
        Board b = new Board();
        b.setDefaultPosition();

        System.err.println(b.displayBoard());

        // p.legalMove();

        // System.err.println(p.getLegalMove());
    }


}

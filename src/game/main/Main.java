package game.main;

import game.board.Board;
import game.piece.Pawn;
import game.piece.Piece;
import game.position.Position;

public class Main {
    
    public static void main(String[] args) {

        Board b = new Board();

        String s = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3";
        b.importFEN(s);

        System.out.println(b.displayBoard());
        System.out.println(b.exportBoardToString());

        // Piece p = b.getPiece(new Position("e2"));

        // System.out.println(p);

        // System.out.println(b.getBoard());

        // System.out.println(b.getPiece(new Position("e1")));

        // System.out.println(b.getBoard().get(0).get(0));
        // System.out.println(b.getBoard().get(0).get(4));
        // System.out.println(b.getBoard().get(0).get(0).getWhite());
        // System.out.println(b.getBoard().get(0).get(4).getWhite());

        // p.legalMove();

        // System.err.println(p.getLegalMove());
    }


}

package game.util.parser;

import game.board.Board;
import game.position.TransPosition;

import java.util.ArrayList;

public abstract class Parser { // Moves Parser Template
    protected static Board board;
    protected static Board dummy;
    protected static int count;

    public Parser(Board board) {
        Parser.board = board;
        dummy = board.copyBoard();
        count = 0;
    }

    public abstract void action(String line, boolean moved);

    protected abstract ArrayList<TransPosition> parse(String line);

    protected void parseMove(TransPosition transPosition, boolean moved) {

        if (transPosition.getTo() == null) throw new IllegalArgumentException("Transition Movement is null");

        if (moved) board.movePiece(transPosition);
        else dummy.movePiece(transPosition);
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Parser.count = count;
    }

    public static boolean isWhiteTurn() {
        return count % 2 != 0;
    }
}

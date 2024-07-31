package game.util.parser;

import game.board.Board;
import game.position.TransPosition;

import java.util.ArrayList;

public abstract class Parser { // Moves Parser Template
    protected Board board;
    protected Board dummy;
    protected int count;
    protected ArrayList<TransPosition> translatedMove = new ArrayList<>();

    public Parser(Board board) {
        this.board = board;
        dummy = board.copyBoard();
        count = 0;
    }

    public abstract void action(String line, boolean moved);

    protected abstract void parse(String line);

    protected void parseMove(TransPosition transPosition, boolean moved) {

        if (transPosition.getFrom() == null || transPosition.getTo() == null) throw new IllegalArgumentException("Transition Movement is null");

        if (moved) board.movePiece(transPosition);
        else dummy.movePiece(transPosition);

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isWhiteTurn() {
        return count % 2 != 0;
    }
}

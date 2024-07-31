package game.board;

import game.piece.*;
import game.position.Position;
import game.position.Status;
import game.position.TransPosition;
import game.util.Checker;
import game.util.Constant;

public class Board {

    private final Piece[][] board;
    private Status status = Status.NORMAL;

    public Board() {
        board = new Piece[Constant.COL][Constant.ROW];
    }

    private void setPiece(Piece piece, Position position) {
        board[position.getRow()][position.getCol()] = piece;
    }

    public boolean placePiece(Piece piece, Position position) {
        if (!isEmpty(position)) return false;
        setPiece(piece, position);
        return true;
    }

    public Piece getPiece(Position position) {
        return board[position.getRow()][position.getCol()];
    }

    private boolean movePiece(Position from, Position to) {

        if (!isInBound(from) || !isInBound(to)) return false;

        // General Move

        if (isSameColor(from, to) || isEmpty(to)) {
            Piece piece = getPiece(from);

            if (piece == null) throw new IllegalArgumentException("Piece is null at " + from);

            setPiece(piece, to);
            piece.moveHandle(to);
            setPiece(null, from);

            return true;
        }

        return false;
    }

    public void movePiece(TransPosition tPosition) {

        // Castling Handling

        if (getPiece(tPosition.getFrom()) instanceof King king &&
            getPiece(tPosition.getTo()) instanceof Rook rook &&
                king.isWhite() == rook.isWhite()) {
            int backRank = king.isWhite() ? 0 : Constant.ROW - 1;

            // Is short Castle ? Short : Long
            Position toRook = king.getPosition().getCol() < rook.getPosition().getCol() ? new Position(backRank, Constant.COL - 3) : new Position(backRank, 3);
            Position toKing = king.getPosition().getCol() < rook.getPosition().getCol() ? new Position(backRank, Constant.COL - 2) : new Position(backRank, 2);

            movePiece(king.getPosition(), toKing);
            movePiece(rook.getPosition(), toRook);

            return;
        }

        boolean valid = movePiece(tPosition.getFrom(), tPosition.getTo());

        // Invalid Move Logic

        if (!valid) throw new IllegalStateException("Invalid Move Pair: " + tPosition.getFrom() + " -> " + tPosition.getTo());

        // En Passant Allow Logic

        if (getPiece(tPosition.getTo()) instanceof Pawn pawn && Math.abs(tPosition.getTo().getRow() - tPosition.getFrom().getRow()) == 2) {
            pawn.setPassantCaptured(true);
        }

        // En Passant Capture Logic

        if (getPiece(new Position(tPosition.getFrom().getRow(), tPosition.getTo().getCol())) instanceof Pawn pawn && pawn.canPassantCaptured()) {
            setPiece(null, pawn.getPosition());
        }

        // Promote Logic

        if (getPiece(tPosition.getTo()) instanceof Pawn pawn && pawn.canPromote() && tPosition.getStatus() == Status.NORMAL) {
            throw new IllegalStateException("Promotion is Available: Please select piece to promote to");
        }

        // OK Promotion Logic

        switch (tPosition.getStatus()) {
            case BISHOP, KNIGHT, ROOK, QUEEN -> promotePiece(tPosition.getTo(), tPosition.getStatus());
            case CHECK -> {
                if (!(new Checker(this)).isCheck(!getPiece(tPosition.getTo()).isWhite()))
                    throw new IllegalStateException("Specified Check Move Failed");
            }
            case WIN -> {
                boolean lastMoveColor = getPiece(tPosition.getTo()).isWhite();
                boolean isMate = (new Checker(this)).isMate(!lastMoveColor);
                if (isMate) this.status = Status.WIN;
            }
        }

        (new Checker(this)).disablePassant(!getPiece(tPosition.getTo()).isWhite());
    }

    private void promotePiece(Position pos, Status status) {
        if (getPiece(pos) instanceof Pawn p && p.canPromote()) {
            Piece promotedPiece;
            switch (status) {
                case KNIGHT -> promotedPiece = new Knight(p.isWhite(), pos, this);
                case BISHOP -> promotedPiece = new Bishop(p.isWhite(), pos, this);
                case ROOK -> promotedPiece = new Rook(p.isWhite(), pos, this);
                case QUEEN -> promotedPiece = new Queen(p.isWhite(), pos, this);
                default -> throw new IllegalStateException("Invalid Promote Status: " + status);
            }

            setPiece(null, pos);
            setPiece(promotedPiece, pos);
            promotedPiece.moveHandle(pos);
        }
    }

    public boolean isSameColor(Position position1, Position position2) {
        return isEmpty(position1) || isEmpty(position2) || getPiece(position1).isWhite() != getPiece(position2).isWhite();
    }

    public boolean isEmpty(Position position) {
        return board[position.getRow()][position.getCol()] == null;
    }

    public boolean isInBound(Position position) {
        return position.getRow() >= 0 && position.getRow() < Constant.COL && position.getCol() >= 0 && position.getCol() < Constant.COL;
    }

    public String displayBoard() {
        StringBuilder showBoard = new StringBuilder();
        for (int i = Constant.ROW - 1; i >= 0; i--) {

            showBoard.append(i + 1).append(" ");

            for (int j = 0; j < Constant.COL; j++) {
                Piece piece = getPiece(new Position(i, j));

                int icon = 0;
                switch (piece) {
                    case Pawn _ -> icon = 1;
                    case Knight _ -> icon = 2;
                    case Bishop _ -> icon = 3;
                    case Rook _ -> icon = 4;
                    case Queen _ -> icon = 5;
                    case King _ -> icon = 6;

                    case null -> {
                        showBoard.append(Constant.ICONS[(i + j) % 2][icon]);
                        showBoard.append(" ");
                        continue;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + piece);
                }

                showBoard.append(Constant.ICONS[piece.isWhite() ? 1: 0][icon]);

                showBoard.append(" ");
            }

            showBoard.append("\n");
        }
        showBoard.append("  a b c d e f g h");

        return showBoard.toString();
    }

    public Board copyBoard() {
        Board newBoard = new Board();
        for (int i = 0; i < Constant.COL; i++) {
            for (int j = 0; j < Constant.ROW; j++) {
                Position position = new Position(i, j);
                Piece piece = getPiece(position);

                if (piece == null) continue;

                Piece newPiece = (Piece) piece.deepCopy();

                newBoard.setPiece(newPiece, position);
                newPiece.setBoard(newBoard);
            }
        }

        return newBoard;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board other) {
            for (int i = 0; i < Constant.COL; i++) {
                for (int j = 0; j < Constant.ROW; j++) {
                    Position position = new Position(i, j);
                    if (getPiece(position) != other.getPiece(position)) return false;
                }
            }
        }

        return true;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Status getStatus() {
        return status;
    }
}

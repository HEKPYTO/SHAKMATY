package game.control;

import game.board.Board;
import game.piece.*;
import game.position.Position;
import game.util.Constant;

public class Display {

    public static final String SEPARATOR = "--------------------------------------------------";
    public static final String WELCOME = "- WELCOME TO ---------------------------------------";
    public static final String longBar = "+-------+----------------------------------------+";
    private static final int[][] pieceCount = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    private Board board;

    public Display(Board board) {
        setBoard(board);
        countPiece();
    }

    public static String formatLogo() {
        return WELCOME + "\n" + Constant.LOGO + "\n" + SEPARATOR;
    }

    public static String greeter() {
        return formatLogo() + "\n" + "[1] SETUP BOARD" + "\n" + "[2] SETUP POSITION" + "\n" + "[0] EXIT" + "\n" + "[ANY] START" + "\n";
    }

    private void clearCountPiece() {
        for (int i = 0; i < pieceCount.length; i++) {
            for (int j = 0; j < pieceCount[i].length; j++) {
                pieceCount[i][j] = 0;
            }
        }
    }

    private void countPiece() {
        clearCountPiece();
        for (int i = 0; i < Constant.ROW; i++) { // too lazy lol
            for (int j = 0; j < Constant.COL; j++) {
                Piece piece = board.getPiece(new Position(i, j));

                if (piece == null) continue;

                int color = piece.isWhite() ? 1 : 0;
                int icon;

                switch (piece) {
                    case Pawn _ -> icon = 0;
                    case Knight _ -> icon = 1;
                    case Bishop _ -> icon = 2;
                    case Rook _ -> icon = 3;
                    case Queen _ -> icon = 4;
                    case King _ -> {
                        continue;
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + piece);
                }

                pieceCount[color][icon]++;
            }
        }
    }

    private static int calculateScore(boolean color) {
        int value = color ? 1: 0;

        return pieceCount[value][0] + (pieceCount[value][1] + pieceCount[value][2]) * 3 + pieceCount[value][3] * 5 + pieceCount[value][4] * 9;
    }

    public static String drawHandle(boolean color) {
        String left = "\n| " + (color ? "WHITE": "BLACK") + " |";
        StringBuilder pieces = new StringBuilder();
        for (int i = 0; i < pieceCount[0].length; i++) {
            int diff = pieceCount[1][i] - pieceCount[0][i];

            diff = color ? Math.max(diff, 0) : Math.min(diff, 0);

            if (diff == 0) continue;
            pieces.append(Constant.ICONS[color ? 1 : 0][i + 1]);
            if (diff > 1) pieces.append("x").append(diff).append(" ");
            else pieces.append(" ");
        }

        int whiteScore = calculateScore(true);
        int blackScore = calculateScore(false);

        int diffabs = color ? Math.max(whiteScore - blackScore, 0) : -Math.min(whiteScore - blackScore, 0);

        String pieceBar = pieces + (diffabs > 0 ? "+" + diffabs : "") + "\n";
        String display = left + new String(new char[48 + (diffabs == 0 ? 1: 0) - left.length() - pieceBar.length()]).replace("\0", " ") + pieceBar;

        return longBar + display + longBar + "\n";
    }

    public static String offSetDisplayBoard(String displayBoard) {

        String[] lines = displayBoard.split("\n");
        StringBuilder finalBoard = new StringBuilder();
        for (String line : lines) {
            finalBoard.append("|       |     ").append(line).append("\n");
        }

        finalBoard.deleteCharAt(finalBoard.length() - 1);

        return finalBoard.toString() + "\n";
    }

    public static String flipBoard(String displayBoard) {

        String[] lines = displayBoard.split("\n");
        String columnLine = lines[lines.length - 1];

        StringBuilder flipLines = new StringBuilder();
        for (int i = lines.length - 2; i >= 0; i--) {
            flipLines.append(lines[i]).append("\n");
        }
        flipLines.append(columnLine);

        return flipLines.toString();
    }

    public static String mirrorBoard(String displayBoard) {
        String[] lines = displayBoard.split("\n");
        StringBuilder mirroredBoard = new StringBuilder();

        for (String line : lines) {
            if (line.matches(".*\\d.*")) {
                String[] parts = line.split(" ");
                StringBuilder reversedLine = new StringBuilder();
                reversedLine.append(parts[0]).append(" ");
                reversedLine.append(parts[1]).append(" ");
                for (int i = parts.length - 2; i > 1; i--) {
                    reversedLine.append(parts[i]).append(" ");
                }
                reversedLine.append(parts[parts.length - 1]);
                mirroredBoard.append(reversedLine.toString()).append("\n");
            } else if (line.matches(".*[a-h].*")) {
                String[] parts = line.split(" ");
                StringBuilder reversedLine = new StringBuilder();
                reversedLine.append("  ");
                for (int i = parts.length - 1; i > 1; i--) {
                    reversedLine.append(parts[i]).append(" ");
                }
                mirroredBoard.append(reversedLine.toString()).append("\n");
            } else {
                mirroredBoard.append(line).append("\n");
            }
        }

        return mirroredBoard.toString();
    }

    public String prettyPrint(boolean isWhite) {

        if (isWhite) {
            return drawHandle(false) + offSetDisplayBoard(board.displayBoard()) + drawHandle(true);
        }

        return drawHandle(true) + offSetDisplayBoard(mirrorBoard(flipBoard(board.displayBoard()))) + drawHandle(false);
    }

    public String previewPrint() {
        return longBar + "\n" + offSetDisplayBoard(board.displayBoard()) + longBar;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

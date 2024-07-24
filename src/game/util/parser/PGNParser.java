package game.util.parser;

import game.board.Board;
import game.piece.King;
import game.piece.Piece;
import game.piece.Rook;
import game.position.Position;
import game.position.Status;
import game.position.TransPosition;
import game.util.Checker;
import game.util.Constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGNParser extends Parser {

    public PGNParser(Board board) {
        super(board);
    }

    public void ImportPGN(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("[")) {
                    parseHeader(line);
                } else {
                    parse(line);
                }
            }
        }
    }

    @Override
    public void action(String line, boolean moved) {
        Pattern metaPattern = Pattern.compile("\\[([^]]+)]");
        Matcher metaMatch = metaPattern.matcher(line);
        int oldCount = getCount();
        while (metaMatch.find()) {
            String[] parts = metaMatch.group(1).split(" \"");
            String tag = parts[0];
            String value = parts[1].replaceAll("\"$", "");
            parseHeader(tag + " : " + value);
        }

        String movesPart = line.replaceAll("\\[.*?]", "").trim().replaceAll("\\d+\\.\\s*", "").replaceAll("\\s+", " ");
        ArrayList<TransPosition> moves = parse(movesPart);

        if (!moved) setCount(oldCount);
        else {
            for (TransPosition move: moves) {
                parseMove(move, true);
            }
        }
    }

    private static void parseHeader(String line) { // For later development
        return;
    }

    @Override
    protected ArrayList<TransPosition> parse(String line) {
        ArrayList<TransPosition> moves = new ArrayList<>();
        String[] moveParts = line.split("\\s+");
        for (String part : moveParts) {
            if (part.matches("\\d+\\.")) continue; // Redundant ?

            TransPosition transPosition;

            setCount(getCount() + 1);
            transPosition = PGNTranslator(part);

            assert transPosition != null;
            if (!dummy.getPiece(transPosition.getFrom()).getLegalMove().contains(transPosition.getTo()))
                throw new IllegalArgumentException("Illegal Move : " + transPosition);

            parseMove(transPosition, false);
            System.out.println(dummy.displayBoard());
            moves.add(transPosition);
        }

        return moves;
    }

    public static TransPosition PGNTranslator(String move) {
        Position from = null;
        Position to = null;
        int backRank = isWhiteTurn() ? 0 : Constant.COL - 1;

        // Special Moves
        switch (move) {
            case "O-O" -> {
                for (int col = Constant.COL - 1; col > 0 ; col--) {
                    Position backPosition = new Position(backRank, col);
                    Piece piece = dummy.getPiece(backPosition);
                    if (piece instanceof Rook) {
                        to = backPosition;
                    } else if (to != null && piece instanceof King) {
                        from = backPosition;
                        break;
                    }
                }

                if (from != null) return new TransPosition(from, to);
            }
            case "O-O-O" -> {
                for (int col = 0; col < Constant.COL; col++) {
                    Position backPosition = new Position(backRank, col);
                    Piece piece = dummy.getPiece(backPosition);
                    if (piece instanceof Rook) {
                        to = backPosition;
                    } else if (to != null && piece instanceof King) {
                        from = backPosition;
                        break;
                    }
                }

                if (to != null) return new TransPosition(from, to);
            }


            case "0-1" -> {
                if (!isWhiteTurn()) throw new IllegalArgumentException("BLACK Illegally claimed victory in BLACK TURN");
                throw new RuntimeException("BLACK assumed WIN");
            }
            case "1-0" -> {
                if (isWhiteTurn()) throw new IllegalArgumentException("WHITE Illegally claimed victory in WHITE TURN");
                throw new RuntimeException("WHITE assumed WIN");
            }
        }

        Pattern pattern = Pattern.compile("([KQRNB])?([a-h])?([1-8])?x?([a-h][1-8])(=[QRNB])?([+])?(#)?");
        Matcher matcher = pattern.matcher(move);

        if (matcher.matches()) {
            String piece = matcher.group(1);
            String file = matcher.group(2);
            String rank = matcher.group(3);
            String destination = matcher.group(4);
            String promotion = matcher.group(5);
            String checked = matcher.group(6);
            String mate = matcher.group(7);

            System.out.println(piece + " " + file + " " + rank + " " + destination + " " + promotion);

            int destCol = destination.charAt(0) - 'a';
            int destRow = destination.charAt(1) - '1';

            to = new Position(destination);

            if (piece == null) { // Pawn Move
                if (file != null) { // Pawn Capture
                    destRow += 1;
                    int startRow = isWhiteTurn() ? destRow - 1: destRow + 1;
//                    System.out.println("CAP: " + startRow + " : " + destRow);
                    from = new Position(file + startRow);
                } else {
                    int startRow = isWhiteTurn() ? destRow - 1 : destRow + 1;
                    if (dummy.getPiece(new Position(startRow, destCol)) == null) {
                        int startRow2 = isWhiteTurn() ? 1: 6;
                        if (dummy.getPiece(new Position(startRow2, destCol)) != null) from = new Position(startRow2, destCol);
                    } else {
                        from = new Position(startRow, destCol);
                    }
                }
            } else {
                char pieceChar = piece.toLowerCase().charAt(0);
//                System.out.println(pieceChar + " " + to + " " + rank + " " + file);
                from = findPieceFrom(pieceChar, to, rank, file);

            }

            if (from == null) throw new IllegalArgumentException("Possibly Wrong or an Illegal move: " + move);

            if (promotion != null) {
                String promotionPiece = Character.toString(promotion.charAt(promotion.length() - 1));
                Status status;
                switch (promotionPiece) {
                    case "N" -> status = Status.KNIGHT;
                    case "B" -> status = Status.BISHOP;
                    case "R" -> status = Status.ROOK;
                    case "Q" -> status = Status.QUEEN;
                    default -> throw new IllegalArgumentException("Wrong promotion piece type: " + promotionPiece);
                }

                return new TransPosition(from, to, status);
            }

            Board vDummy = dummy.copyBoard();
            vDummy.movePiece(new TransPosition(from, to));
            boolean isInCheck = (new Checker(vDummy)).isCheck(!isWhiteTurn());

            System.out.println(move);
            System.out.println(checked + " " + isInCheck);

            boolean isMate = (new Checker(vDummy)).isMate(!isWhiteTurn());
            if (!isMate && mate != null) {
                throw new IllegalArgumentException("This is not mating move: " + move);
            }

            if (isMate) {
                return new TransPosition(from, to, Status.WIN);
            }

            if ((checked != null && !isInCheck) || (checked == null && isInCheck)) {
                throw new IllegalArgumentException("Possibly wrong move being checked: " + move);
            }

            return new TransPosition(from, to, checked != null ? Status.CHECK : Status.NORMAL);
        }

        throw new IllegalArgumentException("Invalid move format: " + move);
    }

    public static Position findPieceFrom(char pieceType, Position to, String specifyRow, String specifyCol) {
        ArrayList<Position> possibleFrom = new ArrayList<>();
        for (int i = 0; i < Constant.COL; i++) {
            for (int j = 0; j < Constant.ROW; j++) {
                Position position = new Position(i, j);
                if (!dummy.isEmpty(position)
                        && dummy.getPiece(position).isWhite() == isWhiteTurn()
                        && pieceType == (FENParser.getFENPieceSymbol(dummy.getPiece(position)) + "").toLowerCase().charAt(0)
                        && dummy.getPiece(position).getLegalMove().contains(to)) { // caching later

                    if (specifyRow != null && dummy.getPiece(position).getPosition().getRow() != specifyRow.charAt(0) - '1') continue;

                    if (specifyCol != null && dummy.getPiece(position).getPosition().getCol() != specifyCol.charAt(0) - 'a') continue;

                    possibleFrom.add(position);
                }
            }
        }

        if (possibleFrom.size() == 1) return possibleFrom.getFirst();

        else if (possibleFrom.size() > 1) throw new IllegalArgumentException("There are multiple moves on board, please specify row-col more");

        return null;
    }
}

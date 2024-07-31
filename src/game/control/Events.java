package game.control;

import game.board.Board;
import game.util.Checker;
import game.util.Constant;
import game.util.parser.FENParser;
import game.util.parser.PGNParser;

import java.util.Scanner;

import static game.control.State.*;

public class Events {

    private static State gameState = State.WELCOME;
    private static String fen = Constant.STARTPOS;
    private static String pgn = "";
    private static Board board;
    private static PGNParser parser;
    private static volatile String inputMove;

    private static final Scanner scanner = new Scanner(System.in);

    private static void changeState(State next) {
        if (next != gameState) gameState = next;
        else throw new IllegalArgumentException("Duplicate State: " + gameState);
    }

    public static void play() {
        for (;;) {
            System.out.println(gameState);
            switch (gameState) {
                case WELCOME -> {

                    System.out.println(Display.greeter());
                    System.out.println("ENTER MODE: ");
                    String mode = scanner.nextLine().trim();

                    switch (mode) {
                        case "1" -> changeState(INPUTFGN);
                        case "2" -> changeState(State.INPUTPGN);
                        case "0" -> changeState(State.EXIT);
                        default -> changeState(State.INITPOS);
                    }
                }

                case INPUTFGN -> {
                    System.out.println("SETUP BOARD MODE (FEN), WARNING LEAVE BLANK IF UNSURE !");
                    System.out.print("ENTER POSITION CODE FEN FORMAT: ");
                    if (scanner.hasNextLine()) {
                        String initFEN = scanner.nextLine().trim();
                        if (!initFEN.isEmpty()) setFen(initFEN);
                        changeState(State.WELCOME);
                    }
                }

                case INPUTPGN -> {
                    Board dummy = FENParser.importFromFEN(fen);

                    System.out.println("SETUP BOARD MODE (PGN), DEPEND ON SETUP BOARD, TYPE [OK] TO LEAVE");
                    if (!pgn.isEmpty()) {
                        try {
                            new PGNParser(dummy).action(pgn, true);
                        } catch (RuntimeException e) {
                            System.out.println("WARNING: " + e.getMessage());
                        }
                        System.out.println(new Display(dummy).previewPrint());
                        System.out.println("TYPE [CLEAR] TO CLEAR, CURRENT POSITION: " + pgn);
                    }
                    System.out.print("ENTER POSITION CODE FORMAT: ");
                    if (scanner.hasNextLine()) {
                        String position = scanner.nextLine().trim();

                        switch (position) {
                            case "CLEAR" -> pgn = "";
                            case "OK" -> {
                                changeState(WELCOME);
                            }
                            default -> {
                                try {
                                    new PGNParser(FENParser.importFromFEN(fen)).action(pgn + position, false);
                                    pgn += position + " ";
                                } catch (IllegalArgumentException | IllegalStateException e) {
                                    System.out.println(e.getMessage());
                                } catch (RuntimeException e) {
                                    if (!e.getMessage().contains("WON")) throw new IllegalStateException("FALSE EXCEPTION: " + e.getMessage());
                                    pgn += position + " ";
                                }
                            }
                        }
                    }
                }

                case INITPOS -> {
                    try {
                        board = fen.isEmpty() ? FENParser.defaultStartBoard() : FENParser.importFromFEN(fen);
                        parser = new PGNParser(board);
                        if (!pgn.isEmpty()) {
                            try {
                                parser.action(pgn, true);
                            } catch (RuntimeException e) {
                                if (e.getMessage().contains("WON")) {
                                    changeState(END);
                                    continue;
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    changeState(State.INMATE);
                }

                case INMATE -> {
                    boolean isMate = (new Checker(board)).isMate(!parser.isWhiteTurn());

                    switch (board.getStatus()) {
                        case WIN -> {
                            if (isMate) changeState(END);
                            else throw new IllegalStateException("False Mate Flags"); // False WIN is not acceptable
                        }
                        default -> changeState(TOPLAY);
                    }
                }

                case TOPLAY -> {
                    System.out.println(parser.getCount());
                    System.out.println(new Display(board).prettyPrint(!parser.isWhiteTurn()));
                    System.out.print(!parser.isWhiteTurn() ? "WHITE TO PLAY : " : "BLACK TO PLAY : ");
                    if (scanner.hasNextLine()) {
                        String move = scanner.nextLine().trim();
                        if (move.isEmpty()) {
                            System.out.println("PLEASE INPUT SOMETHING");
                        } else {
                            setInputMove(move);
                            changeState(State.CHECKLEGAL);
                        }
                    }
                }
                case CHECKLEGAL -> {
                    try {
                        parser.action(inputMove, true);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        if (e.getMessage().contains("WIN")) {
                            changeState(State.END);
                            continue;
                        }
                    }

                    boolean check = (new Checker(board)).isCheck(!parser.isWhiteTurn());
                    if (check) {
                        System.out.println("THE MOVE RESULT IN YOUR KING EXPOSED, MAKE A NEW MOVE !");
                    }
                    changeState(check ? State.TOPLAY : State.INMATE);
                }
                case END -> {
                    System.out.println(board.displayBoard());
                    System.out.println((new Display(board)).prettyPrint(!parser.isWhiteTurn()));
                    System.out.println(parser.isWhiteTurn() ? "WHITE WON" : "BLACK WON");
                    System.out.print("PRESS [ANY] TO CONTINUE: ");
                    String _ = scanner.nextLine().trim();

                    setFen("");
                    changeState(WELCOME);
                }
                case EXIT -> {
                    System.out.println("Quiting ... ");
                    System.exit(0);
                }
            }
        }
    }

    public static String getFen() {
        return fen;
    }

    public static void setFen(String fen) {
        setPgn("");
        Events.fen = fen;
    }

    public static String getPgn() {
        return pgn;
    }

    public static void setPgn(String pgn) {
        Events.pgn = pgn;
    }

    public static String getInputMove() {
        return inputMove;
    }

    public static void setInputMove(String inputMove) {
        Events.inputMove = inputMove;
    }
}

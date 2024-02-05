package game.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import game.board.Board;

public class GameControl {

    private static GameState gameState = GameState.PLAY;
    private static boolean DEBUG_MODE = false;
    private static boolean PLAY_WHITE = true;
    private static int PLAY_MODE = 2;
    private static GameLogic game = null;

    public static String boxIn(String in) {
        String[] lines = in.split("\n");
        int maxLength = 0;

        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        StringBuilder result = new StringBuilder();

        result.append("+").append("-".repeat(maxLength + 2)).append("+\n");

        for (String line : lines) {
            result.append("| ").append(line).append(" ".repeat(maxLength - line.length())).append(" |\n");
        }

        result.append("+").append("-".repeat(maxLength + 2)).append("+");

        return result.toString();
    }

    public static String indexingBoard(String boardString) {
        StringBuilder indexedBoard = new StringBuilder();
    
        String[] rows = boardString.split("\n");
        int rowCount = 8; 
        for (String row : rows) {
            indexedBoard.append(rowCount).append(" ").append(row).append("\n");
            rowCount--; 
        }
    
        indexedBoard.append("  a b c d e f g h\n");
    
        return indexedBoard.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) { // use if, I dont want to break switch at every case

            if (gameState == GameState.WELCOME) {

                System.out.println("Hello!, welcome to");

                try {
                    String content = Files.readString(Paths.get("src/game/constant/Shakmaty.txt"));
                    System.out.println(boxIn(content));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.print("Press Enter to continue... ");

                scanner.nextLine();

                System.out.println();

                gameState = GameState.MODESELECT;

            } else if (gameState == GameState.MODESELECT) {

                try {
                    String content = Files.readString(Paths.get("src/game/constant/ModeSelect.txt"));
                    System.out.println(boxIn(content));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.print("Choice [123q]: ");

                String input = scanner.nextLine();

                if (input.equals("1")) {
                    gameState = GameState.PLAYERSELECT;
                } else if (input.equals("2")) {
                    gameState = GameState.PLAY;
                } else if (input.equals("3")) {
                    DEBUG_MODE = true;
                    gameState = GameState.PLAY;
                } else if (input.equals("q")) {
                    System.out.println("Good Bye!");

                    gameState = GameState.EXIT;
                } else {
                    gameState = GameState.MODESELECT;
                }

                System.out.println();

            } else if (gameState == GameState.PLAYERSELECT) {
                try {
                    String content = Files.readString(Paths.get("src/game/constant/PlayerSelect.txt"));
                    System.out.println(boxIn(content));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.print("Choice [12rq]: ");

                String input = scanner.nextLine();

                if (input.equals("1")) {
                    gameState = GameState.PLAY;
                } else if (input.equals("2")) {
                    PLAY_WHITE = false;
                    gameState = GameState.PLAY;
                } else if (input.equals("r")) {
                    gameState = GameState.MODESELECT;

                } else if (input.equals("q")) {
                    System.out.println("Good Bye!");

                    gameState = GameState.EXIT;
                } else {

                    gameState = GameState.PLAYERSELECT;
                }

                System.out.println();

            } else if (gameState == GameState.PLAY) {

                if (game == null) { // one shot
                    Board b = new Board();
                    b.setDefaultPosition();
                    game = new GameLogic(b);
                }

                System.out.println("White Make a Move: ");
                
                System.err.println(boxIn(indexingBoard(game.getCurrentBoard().displayBoard())));

                gameState = GameState.EXIT;
            }

            else return;
        }
    }
}

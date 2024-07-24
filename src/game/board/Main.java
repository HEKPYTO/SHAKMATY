package game.board;

import game.util.parser.FENParser;
import game.util.parser.PGNParser;
import game.util.parser.Parser;
//import game.util.parser.PGNParser;

public class Main {
    public static void main(String[] args) {
        Board b = FENParser.defaultStartBoard();

//        String move = "1. e4 Nf6 2. Nc3 d6 3. d4 g6 4. Bg5 Bg7 5. Nf3 O-O 6. Bd3 c5 7. O-O Bd7 8. e5\n" +
//                "Be8 9. exf6 exf6 10. Bf4 cxd4 11. Nxd4 a5 12. Re1 Qc8 13. Bxd6 Na6 14. Bxf8 Kxf8\n" +
//                "15. Bb5 Bh8 16. Bxe8 Qxc3 17. bxc3 Rxe8 18. Rb1 Rxe1+ 19. Qxe1 Bg7 20. Rxb7 g5 21. Nf5 h6 22. Nxg7\n" +
//                "Kxg7 23. Qe8 h5 24. Rxf7+ Kg6 25. Ra7+ Kf5 26. Rxa6 h4 27. Rxa5+ Kg4 28. Ra4+\n" +
//                "Kf5 29. c4 h3 30. c5 g4";
//        String move = "1. e4 e5\n" +
//                "2. Bc4 Nf6\n" +
//                "3. d3 c6\n" +
//                "4. Nf3 d5\n" +
//                "5. Bb3 Bb4+\n" +
//                "6. c3 Bd6\n" +
//                "7. Bg5 dxe4\n" +
//                "8. dxe4 h6\n" +
//                "9. Bh4 Qe7\n" +
//                "10. Nbd2 Nbd7\n" +
//                "11. Bg3 Bc7\n" +
//                "12. O-O Nh5\n" +
//                "13. h3 Nxg3\n" +
//                "14. fxg3 Nc5\n" +
//                "15. Bxf7+ Kxf7\n" +
//                "16. Nxe5+ Kg8\n" +
//                "17. Ng6 Qg5\n" +
//                "18. Rf8+ Kh7\n" +
//                "19. Nxh8 Bg4\n" +
//                "20. Qf1 Nd3\n" +
//                "21. Qxd3 Rxf8\n" +
//                "22. hxg4 Qxg4\n" +
//                "23. Nf3 Qxg3\n" +
//                "24. e5+ Kxh8\n" +
//                "25. e6 Bb6+\n" +
//                "26. Kh1 Qg4\n" +
//                "27. Qd6 Rd8\n" +
//                "28. Qe5 Rd5\n" +
//                "29. Qb8+ Kh7\n" +
//                "30. e7 Qh5+\n" +
//                "31. Nh2 Rd1+\n" +
//                "32. Rxd1 Qxd1+\n" +
//                "33. Nf1 Qxf1+\n" +
//                "34. Kh2 Qg1+\n";

//        String move = "1. e4 c5 2. Nc3 d6 3. Nf3 Nc6 4. d4 cxd4 5. Nxd4 a6 6. Nd5 e6 7. Nf4 e5 8. Nxc6 bxc6 9. Nh3 Nf6 10. Ng5 d5 11. Bd2 Bc5 12. Bd3 Ng4 13. Qf3 Qxg5 14. O-O-O Nxf2 15. Kb1 Nxd1 16. exd5 Qxd2 17. Qxd1 Qxg2 18. Bc4 Bg4 19. Be2 Bxe2 20. dxc6 Bxd1 21. a3 Qxc2+ 22. Ka2 Qb3+ 23. Kb1 Bc2+ 24. Kc1 Rd8 25. h3 Rd1+ 26. Rxd1 Bxd1 27. h4 Bg4 28. h5 Bxh5 29. c7 Kd7 30. Kb1 e4 31. c8=B+ Kxc8 32. Kc1 e3 33. a4 e2 34. Kb1 e1=N 35. Kc1 f5 36. a5 f4 37. Kd2 f3 38. Kc1 f2 39. Kd2 f1=R 40. Kc1 g5 41. Kd2 g4 42. Ke2 g3+ 43. Kd2 g2 44. Kc1 g1=Q";

//        String move = "e4 e5 Nc3 Nf6";

//        String pgn = "[Event \"F/S Return Match\"]\n" +
//                "[Site \"Belgrade, Serbia JUG\"]\n" +
//                "[Date \"1992.11.04\"]\n" +
//                "[Round \"29\"]\n" +
//                "[White \"Fischer, Robert J.\"]\n" +
//                "[Black \"Spassky, Boris V.\"]\n" +
//                "[Result \"1/2-1/2\"]\n\n" +
//                "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6\n" +
//                "8. c3 O-O 9. h3 Nb8 10. d4 Nbd7 11. c4 c6 12. cxb5 axb5 13. Nc3 Bb7 14. Bg5\n" +
//                "b4 15. Nb1 h6 16. Bh4 Re8 17. Nbd2 Bf8 18. Rc1 exd4 19. Nxd4 Qb6 20. Nf5 d5\n" +
//                "21. exd5 Rxe1+ 22. Qxe1 cxd5 23. Nf3 g6 24. Ne3 Re8 25. Qd2 Bg7 26. Nxd5 Nxd5\n" +
//                "27. Bxd5 Bxd5 28. Qxd5 Nf6 29. Qb3 Qe6 30. Re1 Qd7 31. Rxe8+ Nxe8 32. Qxb4 Qd1+\n" +
//                "33. Kh2 Qd6+ 34. Qxd6 Nxd6 35. Be7 Ne4 36. b4 Bf8 37. Bxf8 Kxf8 38. Nd4 Nd6\n" +
//                "39. b5 Ke7 40. a4 Kd7 41. Kg3 Kc7 42. Kf4 Nc4 43. Ke4 Kb6 44. Kd5 Nb2 45. a5+\n";

//        System.out.println(b.displayBoard());
//        Events();

        String mate = "[Event \"Casual Game\"]\n" +
                "[Site \"London\"]\n" +
                "[Date \"1892.??.??\"]\n" +
                "[Round \"?\"]\n" +
                "[White \"Wordsworth Donisthorpe\"]\n" +
                "[Black \"Mundell\"]\n" +
                "[Result \"1-0\"]\n" +
                "[EventDate \"?\"]\n" +
                "[ECO \"C46\"]\n" +
                "[WhiteElo \"?\"]\n" +
                "[BlackElo \"?\"]\n" +
                "[PlyCount \"29\"]\n" +
                "\n" +
                "1. e4 e5 2. Nf3 Nc6 3. Nc3 d6 4. d4 Bg4 5. Be3 f5 6. d5 fxe4 7. Nxe4 Nce7 8. c4\n" +
                "Nf6 9. Nxf6+ gxf6 10. h3 Bd7 11. Nh4 Ng6 12. Bd3 Nxh4 13. Qh5+ Ng6 14. Bxg6+ Ke7\n" +
                "15. Qxe5+ fxe5 16. Bg5# 1-0";

        (new PGNParser(b)).action(mate, true);
    }
}

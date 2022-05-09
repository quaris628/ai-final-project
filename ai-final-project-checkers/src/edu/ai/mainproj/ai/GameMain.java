package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.PlayerType;

public class GameMain {

    private static final boolean TIME_CONTROL = false;
    private static final int SEC_PER_TURN = 5;
    private static final int DEPTH_RED = 5;
    private static final int DEPTH_BLACK = 5;

    public static void main(String... args) {
        Player red = new AIPlayer(PlayerType.RED, DEPTH_RED);
        Player black = new AIPlayer(PlayerType.BLACK, DEPTH_BLACK);

        while (true) {
            runGame(red, black);
        }

    }

    public static void runGame(Player black, Player red) {
        CheckersGame game = new CheckersGame();
        System.out.println(game);

        while (game.getWinner() == null) {
            long startTime = System.currentTimeMillis();
            Player currentPlayer = game.getTurn() == PlayerType.RED ? red : black;
            if (game.getPossibleMoves().isEmpty()) {
                black.notifyGameEnd(game);
                red.notifyGameEnd(game);
                System.out.println("DRAW");
                return;
            }
            currentPlayer.executeTurn(game);
            while (TIME_CONTROL && startTime + SEC_PER_TURN * (1000) > System.currentTimeMillis())
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // ?
                    // maybe TODO
                }
            System.out.println(game);
        }
        System.out.println(game.getWinner() + " WINS!");
        black.notifyGameEnd(game);
        red.notifyGameEnd(game);
    }

}

package edu.ai.mainproj.main;

import edu.ai.mainproj.players.AIPlayer;
import edu.ai.mainproj.players.AutoDifficultyAIPlayer;
import edu.ai.mainproj.players.CheckersPlayer;
import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.PlayerType;

public class ConsoleGameMain {

    private static final boolean TIME_CONTROL = false;
    private static final int SEC_PER_TURN = 5;
    private static final int DEPTH_RED = 5;
    private static final int DEPTH_BLACK = 5;

    private GameRunner gameRunner;
    private long startTime;

    public ConsoleGameMain(CheckersPlayer black, CheckersPlayer red) {
        gameRunner = new GameRunner(black, red);
        if (TIME_CONTROL) {
            gameRunner.getTurnStart().subscribe(
                    (e) -> startTime = System.currentTimeMillis());
            gameRunner.getTurnComplete().subscribe((e) -> timeControl());
        }
        gameRunner.getGameComplete().subscribe(this::onGameComplete);
        gameRunner.getTurnComplete().subscribe((e) -> onTurnComplete());
    }

    public void run() {
        while (true) {
            gameRunner.startNewGame();
        }
    }

    private void onGameComplete() {
        System.out.println("---------------");
        System.out.println("   GAME OVER   ");
        System.out.print(" Winner: ");
        System.out.println(gameRunner.getGame().getWinner());
        System.out.println("---------------");
    }

    private void onTurnComplete() {
        System.out.print("\n\n\n");
        System.out.println(gameRunner.getGame());
    }

    private void timeControl() {
        long deltaTime = System.currentTimeMillis() - startTime;
        long waitTime = SEC_PER_TURN * 1000 - deltaTime;
        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                // ?
                // maybe TODO
            }
        }
    }

    public static void main(String... args) {
        CheckersPlayer black = new AutoDifficultyAIPlayer(PlayerType.BLACK);
        CheckersPlayer red = new AIPlayer(PlayerType.RED, DEPTH_RED);
        new ConsoleGameMain(black, red).run();
    }

    // deprecated
    public static void runGame(CheckersPlayer black, CheckersPlayer red) {
        CheckersGame game = new CheckersGame();
        System.out.println(game);

        while (game.getWinner() == null) {
            long startTime = System.currentTimeMillis();
            CheckersPlayer currentPlayer = game.getTurn() == PlayerType.RED ? red : black;
            if (game.getPossibleMoves().isEmpty()) {
                //black.notifyGameEnd(game);
                //red.notifyGameEnd(game);
                System.out.println("DRAW");
                return;
            }
            //currentPlayer.executeTurn(game);
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
        //black.notifyGameEnd(game);
        //red.notifyGameEnd(game);
    }

}

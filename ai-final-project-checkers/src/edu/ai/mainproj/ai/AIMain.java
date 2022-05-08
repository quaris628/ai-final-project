package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.Player;

public class AIMain {

    private static final boolean TIME_CONTROL = false;
    private static final int SEC_PER_TURN = 5;

    public static void main(String... args) throws InterruptedException {
        CheckersGame game = new CheckersGame(new AIPlayer(5), new AIPlayer(5));

        System.out.println(game);
        while (game.getWinner() == null) {
            long startTime = System.currentTimeMillis();
            Player currentPlayer = game.getCurrentPlayer();
            if (game.getPossibleMoves().isEmpty()) {
                System.out.println("DRAW");
                return;
            }
            currentPlayer.executeTurn(game);
            while (TIME_CONTROL && startTime + SEC_PER_TURN * (1000) > System.currentTimeMillis())
                Thread.sleep(10);
            System.out.println(game);
        }
        System.out.println(game.getWinner() + " WINS!");
    }

}

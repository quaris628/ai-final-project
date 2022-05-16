package edu.ai.tests.players;
import edu.ai.mainproj.players.AutoDifficultyAIPlayer;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.PlayerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AutoDifficultyAIPlayer class
 *
 *
 * @author Nathan Swartz
 */
public class AutoDifficultyAIPlayerTests {

    public AutoDifficultyAIPlayerTests() {}

    @Test
    public void getAvgWinRatios_initial() {
        AutoDifficultyAIPlayer player = new AutoDifficultyAIPlayer(PlayerType.RED);

        // if these asserts fail it just means re-calculate the expecteds
        assertEquals(1, AutoDifficultyAIPlayer.MIN_DIFFICULTY);
        assertEquals(9, AutoDifficultyAIPlayer.MAX_DIFFICULTY);
        assertEquals(5, AutoDifficultyAIPlayer.START_DIFFICULTY);

        // expecteds for MAX_DEPTH = 9, MIN_DEPTH = 1, START_DEPTH = 5
        float[] expecteds = new float[] {
                0.1f,
                0.2f,
                0.3f,
                0.4f,
                0.5f,
                0.625f,
                0.75f,
                0.875f
        };
        float delta = 0.000001f;
        assertArrayEquals(expecteds, player.getAvgWinRatios(), delta);
    }

    @Test
    public void getAvgWinRatios_opponentWin() {
        AutoDifficultyAIPlayer player = new AutoDifficultyAIPlayer(PlayerType.RED);

        // set up a win for AI's opponent
        CheckersBoard board = new CheckersBoard();
        new CheckersPiece(PlayerType.BLACK, board.getCheckersTile(0, 1));
        CheckersGame game = new CheckersGame(board);
        assertTrue(game.isDone());
        assertEquals(PlayerType.BLACK, game.getWinner());
        player.notifyGameEnd(game);

        // if these asserts fail it just means re-calculate the expecteds
        assertEquals(1, AutoDifficultyAIPlayer.MIN_DIFFICULTY);
        assertEquals(9, AutoDifficultyAIPlayer.MAX_DIFFICULTY);
        assertEquals(5, AutoDifficultyAIPlayer.START_DIFFICULTY);
        float delta = 0.000001f;
        assertEquals(0.2f, AutoDifficultyAIPlayer.ALPHA, delta);

        // expecteds for MAX_DEPTH = 9, MIN_DEPTH = 1, START_DEPTH = 5,
        //     ALPHA = 0.5f
        float[] expecteds = new float[] {
                0.1f,
                0.2f,
                0.3f,
                0.4f,
                0.4f,
                0.625f,
                0.75f,
                0.875f
        };
        assertArrayEquals(expecteds, player.getAvgWinRatios(), delta);
    }

}

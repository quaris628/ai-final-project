package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;

/**
 * An AI player that automatically adjusts its difficulty
 *     to be closest to drawing its opponent as it expects,
 *     based on past game results.
 *
 * @author Nathan Swartz
 */
public class AutoDifficultyAIPlayer extends AIPlayer {

    public static final int MIN_DEPTH = 1; // inclusive
    public static final int MAX_DEPTH = 9; // exclusive
    public static final int START_DEPTH = 5;

    // for exponentially moving average. Between 0 and 1.
    // higher alpha discounts older data faster
    public static final float ALPHA = 0.5f;

    // indexes correspond to depths, index 0 is for MIN_DEPTH
    // 1   = expect AI wins
    // 0.5 = expect draw
    // 0   = expect opponent wins
    private float[] avgWinRatio;

    public AutoDifficultyAIPlayer(PlayerType playerType) {
        super(playerType, (MAX_DEPTH + MIN_DEPTH) / 2);
        // initiate avgWinRatio data with a linear slope of
        //     probabilities, with not-quite-zero at minimum depth
        //     and not-quite-one at (maximum depth - 1)
        // never initiate to 0 or 1, otherwise will never play
        //     below/above that depth
        avgWinRatio = new float[MAX_DEPTH - MIN_DEPTH];
        for (int i = 0; i < START_DEPTH - MIN_DEPTH; i++) {
            avgWinRatio[i] = 0.5f * ((float)i + 1)/(START_DEPTH - MIN_DEPTH + 1);
            // for debugging
            System.out.print(avgWinRatio[i]);
            System.out.print(" ");
        }
        avgWinRatio[START_DEPTH - MIN_DEPTH] = 0.5f;
        // for debugging
        System.out.print(avgWinRatio[START_DEPTH - MIN_DEPTH]);
        System.out.print(" ");
        for (int i = START_DEPTH - MIN_DEPTH + 1; i < MAX_DEPTH - MIN_DEPTH; i++) {
            avgWinRatio[i] = 0.5f + 0.5f * ((float)i - START_DEPTH + 1)/(MAX_DEPTH - START_DEPTH);
            // for debugging
            System.out.print(avgWinRatio[i]);
            System.out.print(" ");
        }
    }

    @Override
    public void notifyGameEnd(CheckersGamePlayable endGame) {
        // record the result of this game
        // 1   = AI wins
        // 0.5 = draw
        // 0   = opponent wins
        float result;
        if (endGame.getWinner() == null) {
            result = 0.5f;
        } else if (endGame.getWinner() == getPlayerColor()) {
            result = 1.0f;
        } else {
            result = 0.0f;
        }

        // do exponentially weighted moving average for accumulated avg win ratio
        avgWinRatio[depth - MIN_DEPTH] =
                (1.0f - ALPHA) * avgWinRatio[depth - MIN_DEPTH]
                + ALPHA * result;

        // if we expect the AI to win
        if (avgWinRatio[depth - MIN_DEPTH] < 0.5f) {
            // decrease depth if next-lowest depth has an expected
            //     win ratio closer to 50/50
            if (depth > MIN_DEPTH &&
                    Math.abs(0.5f - avgWinRatio[depth - MIN_DEPTH - 1])
                    < Math.abs(0.5f - avgWinRatio[depth - MIN_DEPTH])) {
                depth--;
            }
        }
        // if we expect the opponent to win
        else if (avgWinRatio[depth - MIN_DEPTH] > 0.5f) {
            // increase depth if next-highest depth has an expected
            //     win ratio closer to 50/50
            if (depth < MAX_DEPTH - 1 &&
                    Math.abs(0.5f - avgWinRatio[depth - MIN_DEPTH + 1])
                    < Math.abs(0.5f - avgWinRatio[depth - MIN_DEPTH])) {
                depth++;
            }
        }

    }

    public float[] getAvgWinRatios() { return avgWinRatio.clone(); }
}

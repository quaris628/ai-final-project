package edu.ai.mainproj.players;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;

/**
 * An AI player that automatically adjusts its difficulty
 * to be closest to drawing its opponent as it expects,
 * based on past game results.
 *
 * @author Nathan Swartz
 */
public class AutoDifficultyAIPlayer extends AIPlayer {

    public static final int MIN_DIFFICULTY = 1; // inclusive
    public static final int MAX_DIFFICULTY = 9; // exclusive
    public static final int START_DIFFICULTY = 5;

    // for exponentially moving average. Between 0 and 1.
    // higher alpha discounts older data faster
    // (On the low side is probably good?)
    public static final float ALPHA = 0.2f;

    // indexes correspond to depths, index 0 is for MIN_DEPTH
    // 1   = expect AI wins
    // 0.5 = expect draw
    // 0   = expect opponent wins
    private float[] winRatios;

    public AutoDifficultyAIPlayer(PlayerType playerType) {
        super(playerType, (MAX_DIFFICULTY + MIN_DIFFICULTY) / 2);
        // initiate avgWinRatio data with a linear slope of
        //     probabilities, with not-quite-zero at minimum depth
        //     and not-quite-one at (maximum depth - 1)
        // never initiate to 0 or 1, otherwise will never play
        //     below/above that depth
        winRatios = new float[MAX_DIFFICULTY - MIN_DIFFICULTY];
        for (int i = 0; i < START_DIFFICULTY - MIN_DIFFICULTY; i++) {
            winRatios[i] = 0.5f * ((float) i + 1) / (START_DIFFICULTY - MIN_DIFFICULTY + 1);
            // for debugging
            //System.out.print(winRatios[i]);
            //System.out.print(" ");
        }
        winRatios[START_DIFFICULTY - MIN_DIFFICULTY] = 0.5f;
        // for debugging
        //System.out.print(winRatios[START_DIFFICULTY - MIN_DIFFICULTY]);
        //System.out.print(" ");
        for (int i = START_DIFFICULTY - MIN_DIFFICULTY + 1; i < MAX_DIFFICULTY - MIN_DIFFICULTY; i++) {
            winRatios[i] = 0.5f + 0.5f * ((float) i - START_DIFFICULTY + 1) / (MAX_DIFFICULTY - START_DIFFICULTY);
            // for debugging
            //System.out.print(winRatios[i]);
            //System.out.print(" ");
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
        winRatios[depth - MIN_DIFFICULTY] =
                (1.0f - ALPHA) * winRatios[depth - MIN_DIFFICULTY]
                        + ALPHA * result;
        // TODO also have others slowly trend back towards their expected initial values

        // if we expect the AI to win
        if (winRatios[depth - MIN_DIFFICULTY] > 0.5f) {
            // decrease depth if next-lowest depth has an expected
            //     win ratio closer to 50/50, or any value less than 0.5
            if (depth > MIN_DIFFICULTY &&
                    Math.abs(0.5f - winRatios[depth - MIN_DIFFICULTY - 1])
                            < Math.abs(0.5f - winRatios[depth - MIN_DIFFICULTY])) {
                depth--;
            }
        }
        // if we expect the opponent to win
        else if (winRatios[depth - MIN_DIFFICULTY] < 0.5f) {
            // increase depth if next-highest depth has an expected
            //     win ratio closer to 50/50
            if (depth < MAX_DIFFICULTY - 1 &&
                    Math.abs(0.5f - winRatios[depth - MIN_DIFFICULTY + 1])
                            < Math.abs(0.5f - winRatios[depth - MIN_DIFFICULTY])) {
                depth++;
            }
        }

    }

    public float[] getAvgWinRatios() {
        return winRatios.clone();
    }
}

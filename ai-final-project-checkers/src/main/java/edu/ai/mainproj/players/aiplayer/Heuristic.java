package edu.ai.mainproj.players.aiplayer;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;

// immutable
public class Heuristic {
    public static final float FLOAT_DELTA = 0.0000001f;
    // constants for values of each piece type for heuristic calculation
    private static final float NORMAL_PIECE_HEURISTIC_FRAC_OF_KING = 0.7f;
    private static final int NUM_PIECES_EACH_SIDE = 12;

    private static final float BLACK_WIN_HEURISTIC_VALUE = 1.0f;
    private static final float RED_WIN_HEURISTIC_VALUE = -1.0f;
    private static final float DRAW_HEURISTIC_VALUE = 0.0f;

    public final float value;
    public final boolean isCertain;

    public Heuristic(float value, boolean isCertain) {
        this.value = value;
        this.isCertain = isCertain;
    }

    /**
     * Returns 1 if this is better than the parameter
     *         0 if this is the same as the parameter
     *        -1 if this is worse than  the parameter
     * @param o
     * @param player
     * @return
     */
    public int isBetterThan(Heuristic o, PlayerType player) {
        if (o == null) { return 1; }
        // if heuristics are (about) the same
        if (Math.abs(this.value - o.value) < FLOAT_DELTA) {
            // certainty may make one better
            return isMoreCertainThan(o);
        } else if (player == PlayerType.BLACK) {
            // positive is better
            if (this.value > o.value) { return 1; }
            if (this.value < o.value) { return -1; }
            else { return isMoreCertainThan(o); }
        } else {
            // player is red, negative is better
            if (this.value < o.value) { return 1; }
            if (this.value > o.value) { return -1; }
            else { return isMoreCertainThan(o); }
        }
    }

    private int isMoreCertainThan(Heuristic o) {
        if (!this.isCertain && o.isCertain) { return -1; }
        else if (this.isCertain && !o.isCertain) { return 1; }
        else { return 0; }
    }

    public static Heuristic valueOf(CheckersGamePlayable game) {
        if (game.isDone()) { return valueOfCompletedGame(game); }
        else { return valueOfIncompleteGame(game); }
    }

    public static Heuristic valueOfCompletedGame(CheckersGamePlayable game) {
        assert game.isDone();
        float heuristicValue;
        if (game.getWinner() == PlayerType.BLACK)
            heuristicValue = BLACK_WIN_HEURISTIC_VALUE;
        else if (game.getWinner() == PlayerType.RED) {
            heuristicValue = RED_WIN_HEURISTIC_VALUE;
        } else {
            heuristicValue = DRAW_HEURISTIC_VALUE;
        }
        return new Heuristic(heuristicValue, true);
    }

    public static Heuristic valueOfIncompleteGame(CheckersGamePlayable game) {
        assert !game.isDone();
        int heuristic = 0;
        for (CheckersTile tile : game.getBoardState().getAllCheckersTiles()) {
            if (!tile.isBlank()) {
                CheckersPiece piece = tile.getCheckersPiece();

                float pieceHeuristic; // anywhere b/t 0.0 to 1.0
                if (piece.isKing()) {
                    pieceHeuristic = 1.0f;
                } else {
                    // distance moved across board
                    float distanceHeuristic;
                    int numRows = game.getBoardState().getNumRows();
                    if (piece.getPlayer() == PlayerType.BLACK) {
                        distanceHeuristic = (numRows - piece.getCheckersTile().row) / (float) numRows;
                    } else {
                        distanceHeuristic = piece.getCheckersTile().row / (float) numRows;
                    }
                    pieceHeuristic = distanceHeuristic * NORMAL_PIECE_HEURISTIC_FRAC_OF_KING;
                }

                if (piece.getPlayer() == PlayerType.BLACK)
                    heuristic += pieceHeuristic;
                else
                    heuristic -= pieceHeuristic;
            }
        }
        return new Heuristic(heuristic / (1.0f + NUM_PIECES_EACH_SIDE), false);
    }

    @Override
    public String toString() {
        return "[" + String.format("%.04f", value)
                + ", " + (isCertain ? "Certain" : "Uncertain") + "]";
    }
}

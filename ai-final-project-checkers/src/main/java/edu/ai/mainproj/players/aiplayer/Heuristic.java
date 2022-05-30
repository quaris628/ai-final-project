package edu.ai.mainproj.players.aiplayer;

import edu.ai.mainproj.checkers.PlayerType;

public class Heuristic {
    public static final float FLOAT_DELTA = 0.00001f;

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
        // if heuristics are (about) the same
        if (Math.abs(this.value - o.value) < FLOAT_DELTA) {
            // certainty may make one better
            return isBetterByCertainty(o);
        } else if (player == PlayerType.BLACK) {
            // positive is better
            if (this.value > o.value) { return 1; }
            if (this.value < o.value) { return -1; }
            else { return isBetterByCertainty(o); }
        } else {
            // player is red, negative is better
            if (this.value < o.value) { return 1; }
            if (this.value > o.value) { return -1; }
            else { return isBetterByCertainty(o); }
        }
    }

    private int isBetterByCertainty(Heuristic o) {
        if (!this.isCertain && o.isCertain) { return -1; }
        else if (this.isCertain && !o.isCertain) { return 1; }
        else { return 0; }
    }

    @Override
    public String toString() {
        return "[" + String.format("%.02f", value)
                + ", " + (isCertain ? "Certain" : "Uncertain") + "]";
    }

}

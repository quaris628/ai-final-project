package edu.ai.mainproj.checkers;

/**
 * Enum for checkers players types/colors
 * Black goes first and starts on the bottom
 * Red goes second and starts on the top
 *
 * @author Nathan Swartz
 */
public enum PlayerType {
    BLACK,
    RED;

    /**
     * Returns whether the passed player is the
     * opposite color / opponent of this player
     *
     * @param player
     * @return true if player is opposite color, else false
     */
    public boolean isOpposite(PlayerType player) {
        return this.ordinal() != player.ordinal();
    }

    @Override
    public String toString() {
        if (this == BLACK) {
            return "Black";
        } else {
            return "Red";
        }
    }
}

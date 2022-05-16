package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.PlayerType;

/**
 * Enum for 4 basic diagonal directions
 *
 * @author Nathan Swartz
 */
public enum DiagonalDirection {
    FORWARD_LEFT(-1, -1),
    FORWARD_RIGHT(-1, 1),
    BACKWARD_LEFT(1, -1),
    BACKWARD_RIGHT(1, 1);

    // amounts and directions that the rows/columns
    //     should change if going one unit in this direction
    public final int rowDelta;
    public final int columnDelta;

    DiagonalDirection(int rowDelta, int columnDelta) {
        this.rowDelta = rowDelta;
        this.columnDelta = columnDelta;
    }

    /**
     * Checks if this direction is considered forwards for a player.
     *
     * @param player
     * @return true iff this direction is forwards for player
     */
    public boolean isForwardsFor(PlayerType player) {
        return (player == PlayerType.BLACK && rowDelta == -1)
                || (player == PlayerType.RED && rowDelta == 1);
    }

}

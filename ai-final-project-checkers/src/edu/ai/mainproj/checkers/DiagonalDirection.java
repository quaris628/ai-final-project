package edu.ai.mainproj.checkers;

public enum DiagonalDirection {
    FORWARD_LEFT(-1, -1),
    FORWARD_RIGHT(-1, 1),
    BACKWARD_LEFT(1, -1),
    BACKWARD_RIGHT(1, 1);

    public final int rowDelta;
    public final int columnDelta;

    DiagonalDirection(int rowDelta, int columnDelta) {
        this.rowDelta = rowDelta;
        this.columnDelta = columnDelta;
    }

    public boolean isForwardsFor(PlayerType player) {
        return rowDelta == 1 && player == PlayerType.BLACK
                || rowDelta == -1 && player == PlayerType.RED;
    }

}

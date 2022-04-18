package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;

import java.util.LinkedList;
import java.util.List;


/**
 * A tile on a checkers board.
 * May contain a piece.
 *
 * @author Nathan Swartz
 */
public class CheckersTile extends Tile {

    public CheckersTile(CheckersBoard board, int row, int column) {
        super(board, row, column);
    }

    public Iterable<CheckersTile> getNeighbors() {
        List<CheckersTile> neighbors = new LinkedList<CheckersTile>();
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            CheckersTile neighbor = getNeighborAt(dir);
            if (neighbor != null) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    public CheckersTile getNeighborAt(DiagonalDirection direction) {
        int neighborRow = row + direction.rowDelta;
        int neighborColumn = column + direction.columnDelta;
        if (0 <= neighborRow && neighborRow < board.getNumRows()
                && 0 <= neighborColumn && neighborColumn < board.getNumColumns()) {
            return (CheckersTile)board.getTile(neighborRow, neighborColumn);
        } else {
            return null;
        }
    }

    public boolean doesKing(PlayerType player) {
        return player == PlayerType.BLACK && row == 0
            || player == PlayerType.RED && row == board.getNumRows() - 1;
    }

    public CheckersPiece getCheckersPiece() { return (CheckersPiece)getPiece(); }
}

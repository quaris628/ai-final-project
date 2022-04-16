package edu.ai.mainproj.anygame;

import edu.ai.mainproj.checkers.DiagonalDirection;
import edu.ai.mainproj.checkers.PlayerType;

import java.util.LinkedList;
import java.util.List;

/**
 * A tile on the checkers board.
 * May contain a piece.
 *
 * @author Nathan Swartz
 */
public class Tile {

    private final GridBoard board;
    private final int row;
    private final int column;
    private Piece piece; // null means blank

    /**
     * Creates a new tile, on a board, at a certain location
     *     on the board's grid (specified by row and column)
     * @param board
     * @param row
     * @param column
     */
    public Tile(GridBoard board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        piece = null;
    }

    /**
     * Returns all tiles adjacent to this tile
     * Excludes would-be tiles that are out-of-bounds, does not
     * have null values in their place.
     * @return all neighboring tiles
     */
    public Iterable<Tile> getNeighbors() {
        List<Tile> neighbors = new LinkedList<Tile>();
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            Tile neighbor = getNeighborAt(dir);
            if (neighbor != null) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Returns the tile adjacent to this tile, in the specified
     *     direction
     * If the tile in that direction does not exist, i.e. it is
     *     off the edge of the game board, returns null
     * @param direction
     * @return neighboring tile
     */
    public Tile getNeighborAt(DiagonalDirection direction) {
        int neighborRow = row + direction.rowDelta;
        int neighborColumn = column + direction.columnDelta;
        if (0 <= neighborRow && neighborRow < board.getSize()
            && 0 <= neighborColumn && neighborColumn < board.getSize()) {
            return board.getTile(neighborRow, neighborColumn);
        } else {
            return null;
        }
    }

    /**
     * Returns whether a piece that belongs to a player should
     *     be king-ed if it comes onto this tile.
     * @param player
     * @return
     */
    public boolean doesKing(PlayerType player) {
        return row == 0 && player == PlayerType.BLACK
                || row == board.getSize() - 1 && player == PlayerType.RED;
    }

    /**
     * If this is a king-ing tile, kings the piece.
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        if (doesKing(piece.getPlayer())) {
            piece.king();
        }
    }

    // simple getters/setters

    /**
     * Tests if there is a piece on this tile.
     * @return true if no piece is on the tile, otherwise false
     */
    public boolean isBlank() { return piece == null; }
    public Piece getPiece() { return piece; }
    public void removePiece() { this.piece = null; }
}

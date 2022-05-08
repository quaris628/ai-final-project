package edu.ai.mainproj.anygame;

import javax.naming.OperationNotSupportedException;

/**
 * A tile on a grid board
 * May contain a piece
 *
 * @author Nathan Swartz
 */
public class Tile {

    public final GridBoard board;
    public final int row;
    public final int column;
    private Piece piece; // null means blank

    /**
     * Creates a new tile on the board, at a specified row and column.
     * Not intended to be called outside of the Board constructor.
     * @param board this tile belongs to
     * @param row this tile will be at on the board
     * @param column this tile will be at on the board
     */
    public Tile(GridBoard board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        this.piece = null;
    }

    /**
     * Sets the piece that is on this tile
     * Only one piece may be on a tile at a time
     * Does not update Piece's reference to its tile
     * @param piece to set onto this tile
     * @throws IllegalStateException if a piece is
     *     already on the tile and the parameter is not null
     */
    public void setPiece(Piece piece) {
        if (this.piece != null && piece != null) {
            throw new IllegalStateException("May not place more than one piece on a tile.");
        }
        this.piece = piece;
    }

    /**
     * Removes this tile's piece, if it has any.
     * If this tile doesn't have a piece, runs and changes nothing.
     * Does not update Piece's reference to its tile.
     */
    public void removePiece() { this.piece = null; }
    public boolean isBlank() { return piece == null; }

    /**
     * Gets the piece on this tile
     * Returns null if this tile has no piece.
     * @return piece on this tile
     */
    public Piece getPiece() { return piece; }

    /**
     * @return hashcode unique to row, column
     */
	@Override
	public int hashCode() {
		return row * 691 ^ column;
	}

    @Override
    public String toString() {
        if (isBlank()) {
            return " ";
        } else {
            return piece.toString();
        }
    }
}

package edu.ai.mainproj.anygame;

/**
 * A Piece that can be placed and moved around Tiles
 *
 * May be moved to another tile, when moved
 *     is no longer on previous tile
 * May be removed from the board
 *
 * IS NOT RESTRICTED BY CHECKERS MOVEMENT RULES
 *
 * @author Nathan Swartz
 */
public class Piece {

    private Tile tile;

    /**
     * Constructs a new Piece on a tile
     * @param tile
     */
    public Piece(Tile tile) {
        this.tile = tile;
    }

    public boolean isValidMove(Move move) {
        return true;
    }

    /**
     * Moves this piece to another tile
     * DOES NOT check if the move is a
     *     valid move for any particular game
     * @param tile destination
     */
    public void moveTo(Tile tile) {
        if (!tile.isBlank()) {
            throw new IllegalArgumentException(
                    "Destination tile already has a piece");
        }
        onMoveTo(tile);
        this.tile.removePiece();
        this.tile = tile;
        this.tile.setPiece(this);
    }

    /**
     * For child classes to override for special behavior when this
     *     piece moves
     * @param tile destination
     */
    protected void onMoveTo(Tile tile) { }

    /**
     * Removes this piece from the tile it is on.
     */
	public void remove() {
		if (this.tile != null) {
            this.tile.removePiece();
            this.tile = null;
        }
	}

    // simple getters/setters
	public Tile getTile() { return tile; }
}

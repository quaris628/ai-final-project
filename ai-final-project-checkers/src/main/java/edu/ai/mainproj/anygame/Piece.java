package edu.ai.mainproj.anygame;

/**
 * A Piece that can be placed and moved around Tiles
 * <p>
 * May be moved to another tile, when moved
 * is no longer on previous tile
 * May be removed from the board
 * <p>
 * IS NOT RESTRICTED BY CHECKERS MOVEMENT RULES
 *
 * @author Nathan Swartz
 */
public class Piece {

    private Tile tile;

    /**
     * Constructs a new Piece on a tile
     *
     * @param tile
     */
    public Piece(Tile tile) {
        this.tile = tile;
        tile.setPiece(this);
    }

    /**
     * Moves this piece to another tile
     * DOES NOT check if the move is a
     * valid move for any particular game
     *
     * @param tile destination
     */
    public void moveTo(Tile tile) {
        if (!tile.isBlank()) {
            throw new IllegalArgumentException(
                    "Destination tile already has a piece");
        }
        if (this.tile != null) {
            this.tile.removePiece();
        }
        this.tile = tile;
        this.tile.setPiece(this);
    }

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

    /**
     * Gets the tile this piece is on.
     * If the piece is not on a tile, returns null.
     *
     * @return tile this piece is on
     */
    public Tile getTile() {
        return tile;
    }

    @Override
    public String toString() {
        return "O";
    }
}

package edu.ai.mainproj.checkers;

/**
 * A Piece, belonging to a certain player,
 *     that can be placed and moved around Tiles
 *
 * Belongs to exactly one player, immutable
 * May be moved to another tile, when moved
 *     is no longer on previous tile
 * May be removed from the board
 *
 * Starts unkinged
 * May be kinged once, upon doing so is a king forever
 *
 * IS NOT RESTRICTED BY CHECKERS MOVEMENT RULES
 * Maybe TODO
 * idk what the best system is
 *
 * @author Nathan Swartz
 */
public class Piece {

    private Tile tile;
    private final PlayerType playerType;
    private boolean king;

    /**
     * Constructs a new Piece given the player who
     *     owns it, and the tile it belongs to.
     * @param playerType
     * @param tile
     */
    public Piece(PlayerType playerType, Tile tile) {
        this.tile = tile;
        this.playerType = playerType;
        this.king = false;
    }

    /**
     * Moves this piece to another tile
     * DOES NOT check if the move is a
     *     valid checkers move
     * @param tile destination
     */
    public void moveTo(Tile tile) {
        if (!tile.isBlank()) {
            throw new IllegalArgumentException(
                    "Destination tile already has a piece");
        }
        this.tile.removePiece();
        this.tile = tile;
        this.tile.setPiece(this);
        // Validation of moves, etc is in the Move class
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
    public boolean isKing() { return king; }
    /**
     * Kings this piece
     */
	public void king() { king = true; }
    public PlayerType getPlayer() { return playerType; }
	public Tile getTile() { return tile; }
}

package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

/**
 * A piece for the checkers game
 * Is either black or red depending on its PlayerType (enum)
 * Is either a normal piece or a king
 *
 * Can only be placed on checkers tiles
 *
 * @author Nathan Swartz
 */
public class CheckersPiece extends Piece {

    private final PlayerType player;
    private boolean king;

    /**
     * Creates a new checkers piece
     * @param player the piece belongs to
     * @param tile the piece starts on
     */
    public CheckersPiece(PlayerType player, CheckersTile tile) {
        super(tile);
        this.player = player;
        king = tile.doesKing(player);
    }

    @Override
    public void moveTo(Tile tile) {
        if (tile instanceof CheckersTile) {
            super.moveTo(tile);
            if (((CheckersTile) tile).doesKing(player)) {
                king = true;
            }
        } else {
            // not ideal to throw exception, but I don't
            // see a better way to enforce this
            throw new IllegalArgumentException(
                    "Checkers piece cannot be placed on a non-checkers tile");
        }
    }

    public CheckersTile getCheckersTile() {
        return (CheckersTile) getTile();
    }

    public boolean isKing() { return king; }
    public PlayerType getPlayer() { return player; }

    @Override
    public String toString() {
        if (player == PlayerType.BLACK) { return "B"; }
        if (player == PlayerType.RED) { return "R"; }
        return super.toString();
    }

}

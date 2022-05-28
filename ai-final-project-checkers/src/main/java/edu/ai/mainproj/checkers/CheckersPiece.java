package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

/**
 * A piece for the checkers game
 * Is either black or red depending on its PlayerType (enum)
 * Is either a normal piece or a king
 * <p>
 * Can only be placed on checkers tiles
 *
 * @author Nathan Swartz
 */
public class CheckersPiece extends Piece {

    private final PlayerType player;
    private boolean king;

    /**
     * Creates a new checkers piece
     *
     * @param player the piece belongs to
     * @param tile   the piece starts on
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
        } else {
            // not ideal to throw exception, but I don't
            // see a better way to enforce this...
            throw new IllegalArgumentException(
                    "Checkers piece cannot be placed on a non-checkers tile");
        }
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public PlayerType getPlayer() {
        return player;
    }

    public CheckersTile getCheckersTile() {
        return (CheckersTile) getTile();
    }

    @Override
    public int hashCode() {
        int result = 73;
        result = result * 57 + player.hashCode();
        result = result * 57 + (king ? 0 : 1);
        result = result * 57 + (getTile() == null ? 0 : getTile().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (player == PlayerType.BLACK) ret.append("\u001B[37m");
        else ret.append("\u001B[31m");
        if (isKing()) ret.append("▲");
        else ret.append("●");
        ret.append("\u001B[0m");
        return ret.toString();
    }

    /**
     * An alternative to .clone()
     * returns deep copy of current object's state
     * @return object containing all data on this piece
     */
    public PieceData getData() {
        return new PieceData(getTile().row, getTile().column, player, king);
    }

    public static class PieceData {
        public final int row;
        public final int column;
        public final PlayerType player;
        public final boolean isKing;
        protected PieceData(int row, int column, PlayerType player, boolean isKing) {
            this.row = row;
            this.column = column;
            this.player = player;
            this.isKing = isKing;
        }
    }
}

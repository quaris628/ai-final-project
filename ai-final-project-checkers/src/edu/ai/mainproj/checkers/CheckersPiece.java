package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

/**
 * A piece for the checkers game
 * Is either black or red depending on its PlayerType (enum)
 * Is either a normal piece or a king
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
    public CheckersPiece(PlayerType player, Tile tile) {
        super(tile);
        this.player = player;

    }

    @Override
    public boolean isValidMove(Move move) {
        if (move.piece != this) { return false; }

    }

    @Override
    public void onMoveTo(Tile tile) {
        if (getTile().doesKing(player)) {
            king = true;
        }
    }

    /**
     * Checks whether this piece is a king or not
     * @return true if this piece is a king, else false
     */
    public boolean isKing() { return king; }


}

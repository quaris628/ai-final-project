package edu.ai.mainproj.anygame;

/**
 * A Move "Command" class for the action of moving pieces
 * to different tiles.
 * <p>
 * Impossible = cannot be valid no matter the state of the board
 * Not valid = against the game rules, or current state of the
 * board does not allow it
 * <p>
 * Any possible move, even if it breaks the rules of
 * the game, should be able to be constructed.
 * Any possible move that may be invalid depending on the game
 * rules and current state of the board should be able to
 * be constructed, but isValid should return false.
 * <p>
 * If a move is invalid, execute() should fail and
 * throw an IllegalStateException.
 *
 * @author Nathan Swartz
 */
public class Move {

    public final Piece piece;
    public final Tile destination;
    private Tile startingTile;

    /**
     * Creates a new Move out of a piece and a destination.
     * Should not allow creation of impossible moves
     * (moves that are not possible no matter but isValid should return false)
     *
     * @param piece       to move to destination tile
     * @param destination tile to move the piece to
     * @throws IllegalArgumentException if piece or destination is null
     */
    public Move(Piece piece, Tile destination) throws IllegalArgumentException {
        if (piece == null || destination == null) {
            throw new IllegalArgumentException((piece == null
                    ? "piece" : "destination") + " cannot be null");
        }
        this.piece = piece;
        this.destination = destination;
        this.startingTile = null;
    }

    /**
     * Checks if this move is valid or not.
     * May produce different results depending on the board state.
     * Will return false if the destination is not blank.
     * Other conditions may be specified by child classes.
     *
     * @return true if move is valid, otherwise false
     */
    public boolean isValid() {
        return destination.isBlank();
    }

    /**
     * Executes the move, changing the state of the game board.
     * The piece is removed from its current tile and is placed
     * on the destination tile.
     * Other behavior may be specified by child classes.
     *
     * @throws IllegalStateException if isValid returns false
     */
    public void execute() throws IllegalStateException {
        if (!(isValid())) {
            throw new IllegalStateException(
                    "Move is not valid, cannot execute.");
        }
        startingTile = piece.getTile();
        piece.moveTo(destination);
    }

    /**
     * Un-does this move, returning the board state to what it was
     * before this move was made.
     * WARNING does not check if unexecution is valid!
     * TODO maybe make it check if unexecution is valid
     */
    public void unexecute() {
        // TODO implement all overrides where needed
        piece.moveTo(startingTile);
    }

    /**
     * Gets the starting tile of the piece in this move
     * Returns null until execute() is called.
     *
     * @return starting tile of the piece in this move
     */
    public Tile getStartingTile() {
        return startingTile;
    }

}
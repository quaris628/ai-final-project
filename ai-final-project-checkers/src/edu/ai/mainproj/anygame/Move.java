package edu.ai.mainproj.anygame;

/**
 * A Move "Command" class for the action of moving pieces
 *     to different tiles.
 *
 * Any move that could be made even if it breaks the rules of
 *     the game should be able to be constructed.
 * Any move that may be invalid depending on the game
 *     rules and current state of the board should be able to
 *     be constructed, but isValid should return false.
 *
 * If a move is invalid, execute() should fail and
 *     throw an IllegalStateException
 *
 * @author Nathan Swartz
 */
public class Move {

	public final Piece piece;
	public final Tile destination;
	
	public Move(Piece piece, Tile destination) throws IllegalArgumentException {
		if (piece == null || destination == null) {
			throw new IllegalArgumentException((piece == null
					? "piece" : "destination") + " cannot be null");
		}
		this.piece = piece;
		this.destination = destination;
	}
	
	public boolean isValid() {
		return destination.isBlank();
	}
	
	public void execute() throws IllegalStateException {
		if (!(isValid())) {
			throw new IllegalStateException(
					"Move is not valid, cannot execute.");
		}
		piece.moveTo(destination);
	}

}
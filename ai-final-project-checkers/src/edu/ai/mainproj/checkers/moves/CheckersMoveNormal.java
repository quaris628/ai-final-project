package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.DiagonalDirection;

/**
 * For a normal single-tile-away move that a checkers piece can make
 * For both kings and non-king pieces. If the piece is a king, it
 *     may move in any diagonal direction, otherwise the piece may only
 *     move forwards (relative to the player's orientation).
 *
 * @author Nathan Swartz
 */
public class CheckersMoveNormal extends CheckersMove {

	private final DiagonalDirection direction;

	public CheckersMoveNormal(CheckersPiece piece, DiagonalDirection direction) {
		super(piece, piece.getCheckersTile().getNeighborAt(direction));
		this.direction = direction;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid() && (piece.isKing()
				|| direction.isForwardsFor(piece.getPlayer()));
	}
	
}
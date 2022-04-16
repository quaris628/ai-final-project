package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;

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
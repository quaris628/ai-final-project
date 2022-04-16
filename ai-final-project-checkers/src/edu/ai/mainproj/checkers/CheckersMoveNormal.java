package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;

public class CheckersMoveNormal extends Move {

	private final DiagonalDirection direction;
	private final CheckersPiece piece;

	public CheckersMoveNormal(CheckersPiece piece, DiagonalDirection direction) {
		super(piece, piece.getCheckersTile().getNeighborAt(direction));
		this.piece = piece;
		this.direction = direction;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid() && (piece.isKing()
				|| direction.isForwardsFor(piece.getPlayer()));
	}
	
}
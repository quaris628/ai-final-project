package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;

public class NormalMove extends Move {
	
	public NormalMove(Piece piece, DiagonalDirection direction) {
		super(piece, piece.getTile().getNeighborAt(direction));
	}
	
	@Override
	public boolean isValid() {
		// TODO should not move backward if not king
		// TODO create another subclass for king moves
		return this.getDestination() != null
			&& this.getDestination().getPiece() == null;
	}
	
	@Override
	protected void childExecute() {
		this.getPiece().moveTo(this.getDestination());
	}
	
}
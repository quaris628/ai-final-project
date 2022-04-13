package edu.ai.mainproj.checkers;

public class NormalMove extends Move {
	
	public NormalMove(Piece piece, DiagonalDirection direction) {
		super(piece, piece.getTile().getNeighborAt(direction));
	}
	
	@Override
	public boolean isValid() {
		return this.getDestination() != null
			&& this.getDestination().getPiece() == null;
	}
	
	@Override
	protected void executeChild() {
		this.getPiece().moveTo(this.getDestination());
	}
	
}
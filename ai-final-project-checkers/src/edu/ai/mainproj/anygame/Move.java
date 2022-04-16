package edu.ai.mainproj.anygame;

public abstract class Move {
	
	protected final Piece piece;
	protected final Tile destination;
	
	protected Move(Piece piece, Tile destination) {
		this.piece = piece;
		this.destination = destination;
	}
	
	public abstract boolean isValid();
	
	public boolean execute() {
		if (!(isValid() && destination.isBlank())) {
			return false;
		}
		childExecute();
		piece.moveTo(destination);
		return true;
	}

	protected abstract void childExecute();
	
	protected Piece getPiece() { return piece; }
	protected Tile getDestination() { return destination; }
	
}
package edu.ai.mainproj.anygame;

public class Move {

	private final Piece piece;
	private final Tile destination;
	
	public Move(Piece piece, Tile destination) {
		this.piece = piece;
		this.destination = destination;
	}
	
	public boolean isValid() {
		return piece != null && destination != null
				&& destination.isBlank();
	}
	
	public void execute() {
		if (!(isValid())) {
			throw new IllegalStateException(
					"Move is not valid, cannot execute.");
		}
		piece.moveTo(destination);
	}

	public Piece getPiece() { return piece; }
	public Tile getDestination() { return destination; }
	
}
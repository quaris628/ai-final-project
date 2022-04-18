package edu.ai.mainproj.anygame;

public class Move {

	public final Piece piece;
	public final Tile destination;
	
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

}
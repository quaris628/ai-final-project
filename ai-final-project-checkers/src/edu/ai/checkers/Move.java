

public abstract class Move {
	
	private Piece piece;
	private Tile destination;
	
	protected Move(Piece piece, Tile destination) {
		this.piece = piece;
		this.destination = destination;
	}
	
	public abstract boolean isValid();
	
	public void execute() {
		if (isValid()) {
			executeChild();
		}
		if (piece.getTile().isKingerFor(piece.getPlayer())) {
			piece.king();
		}
	}
	
	protected abstract void executeChild();
	
	protected Piece getPiece() { return piece; }
	protected Tile getDestination() { return destination; }
	
}
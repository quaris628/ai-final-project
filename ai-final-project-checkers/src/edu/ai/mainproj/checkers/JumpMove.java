package edu.ai.mainproj.checkers;

public class JumpMove extends Move {
	
	private Tile jumpedTile;
	
	public JumpMove(Piece piece, DiagonalDirection direction) {
		super(piece, piece.getTile().getNeighborAt(direction).getNeighborAt(direction));
		this.jumpedTile = piece.getTile().getNeighborAt(direction);
	}
	
	// Dangerous, may allow invalid moves
	// Hence it's made only package-accessible
	JumpMove(Piece piece, Tile jumpedTile, Tile destination) {
		super(piece, destination);
		this.jumpedTile = jumpedTile;
	}
	
	@Override
	public boolean isValid() {
		return this.getDestination() != null
			&& this.getDestination().getPiece() == null
			&& this.jumpedTile.getPiece() != null
			&& this.jumpedTile.getPiece().getPlayer().isOpposite(getPiece().getPlayer());
	}
	
	@Override
	protected void executeChild() {
		this.getPiece().moveTo(this.getDestination());
		this.jumpedTile.getPiece().remove();
	}
	
	
}
package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

public class CheckersMoveJump extends CheckersMove {
	
	private final CheckersTile jumpedTile;
	
	public CheckersMoveJump(CheckersPiece piece, DiagonalDirection direction) {
		super(piece, (piece == null || piece.getCheckersTile() == null
				|| piece.getCheckersTile().getNeighborAt(direction) == null) ? null
				: piece.getCheckersTile().getNeighborAt(direction).getNeighborAt(direction));
		this.jumpedTile = (piece == null || piece.getCheckersTile() == null) ? null
				: piece.getCheckersTile().getNeighborAt(direction);
	}
	
	@Override
	public boolean isValid() {
		return super.isValid()
				&& this.jumpedTile != null
				&& !this.jumpedTile.isBlank()
				&& this.jumpedTile.getCheckersPiece().getPlayer() != null
				&& this.jumpedTile.getCheckersPiece().getPlayer().isOpposite(piece.getPlayer());
	}

	@Override
	public void execute() {
		super.execute();
		jumpedTile.removePiece();
	}
	
}
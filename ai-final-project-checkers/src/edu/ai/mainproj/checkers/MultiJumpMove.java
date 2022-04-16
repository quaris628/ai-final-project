package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

// I'm less sure this class is correct. -Nathan
public class MultiJumpMove extends Move {
	
	private CheckersMoveJump[] jumps;
	
	public static MultiJumpMove CreateMultiJumpMove(Piece piece, DiagonalDirection[] directions) {
		CheckersMoveJump[] jumps = new CheckersMoveJump[directions.length];
		Tile tile = piece.getTile();
		for (int i = 0; i < directions.length; i++) {
			Tile jumpedTile = tile.getNeighborAt(directions[i]);
			Tile destTile = jumpedTile.getNeighborAt(directions[i]);
			jumps[i] = new CheckersMoveJump(piece, jumpedTile, destTile);
			tile = destTile;
			if (tile == null) { return null; }
		}
		return new MultiJumpMove(piece, tile, jumps);
	}
	
	private MultiJumpMove(Piece piece, Tile destination, CheckersMoveJump[] jumps) {
		super(piece, destination);
		this.jumps = jumps;
	}
	
	@Override
	public boolean isValid() {
		for (CheckersMoveJump jump : jumps) {
			if (!jump.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void executeChild() {
		// TODO
	}
	
}
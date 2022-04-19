package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

/**
 * For the capturing jump move in checkers
 *
 * @author Nathan Swartz
 */
public class CheckersMoveJump extends CheckersMove {

	public final DiagonalDirection direction;
	public final CheckersTile startingTile;
	public final CheckersTile jumpedTile;
	private final boolean multiJumpCheckStartingTileBlank; // see doc comment below

	public static CheckersMoveJump Create(CheckersPiece piece, DiagonalDirection direction) {
		// some argument checking so that exception messages for the
		// following problems make more sense
		if (piece == null || direction == null
				|| piece.getCheckersTile() == null
				|| piece.getCheckersTile().getNeighborAt(direction) == null
				|| piece.getCheckersTile().getNeighborAt(direction).getNeighborAt(direction) == null) {
			String message = "";
			if (piece == null) {
				message = "piece cannot be null";
			} else if (direction == null) {
				message = "direction cannot be null";
			} else if (piece.getCheckersTile() == null) {
				message = "piece must be on a tile";
			} else if (piece.getCheckersTile().getNeighborAt(direction) == null) {
				message = "piece's neighbor in given direction must exist";
			} else if (piece.getCheckersTile().getNeighborAt(direction).getNeighborAt(direction) == null) {
				message = "piece's second neighbor in given direction must exist";
			}
			throw new IllegalArgumentException(message);
		}
		CheckersTile startingTile = piece.getCheckersTile();
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction, false);
	}

	/*
	public static CheckersMoveJump Create(CheckersTile startingTile, DiagonalDirection direction) {
		CheckersPiece piece = startingTile.getCheckersPiece();
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction);
	}
	*/

	/**
	 * Used for when this jump is part of a multi-jump sequence, where the piece
	 * 	   is not currently on the starting tile.
	 * In this case, the startingTile must be blank.
	 */
	public static CheckersMoveJump CreateAsPartOfMultiJump(CheckersPiece piece, CheckersTile startingTile,
														   DiagonalDirection direction) {
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction, startingTile.isBlank());
	}

	protected CheckersMoveJump(CheckersPiece piece, CheckersTile startingTile,
							   CheckersTile jumpedTile, CheckersTile destination,
							   DiagonalDirection direction,
							   boolean multiJumpCheckStartingTileBlank) {
		super(piece, destination);
		this.startingTile = startingTile;
		this.jumpedTile = jumpedTile;
		this.direction = direction;
		this.multiJumpCheckStartingTileBlank = multiJumpCheckStartingTileBlank;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid()
				&& this.jumpedTile != null
				&& !this.jumpedTile.isBlank()
				&& this.jumpedTile.getCheckersPiece().getPlayer() != null
				&& this.jumpedTile.getCheckersPiece().getPlayer().isOpposite(piece.getPlayer())
				&& (piece.isKing() || direction.isForwardsFor(piece.getPlayer()))
				&& (!multiJumpCheckStartingTileBlank || startingTile.isBlank());
	}

	@Override
	public void execute() {
		super.execute();
		jumpedTile.getCheckersPiece().remove();
		// cannot use only jumpedTile.removePiece() because
		//     it does not set the removed piece's reference to null
	}
	
}
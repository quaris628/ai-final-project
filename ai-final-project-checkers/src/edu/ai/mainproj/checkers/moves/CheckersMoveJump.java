package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.DiagonalDirection;

/**
 * For the capturing jump move in checkers
 *
 * @author Nathan Swartz
 */
public class CheckersMoveJump extends CheckersMove {

	public final DiagonalDirection direction;
	public final CheckersTile startingTile;
	public final CheckersTile jumpedTile;
	private final boolean multiJumpStartingTileNotBlank; // see doc comment below

	public static CheckersMoveJump Create(CheckersPiece piece, DiagonalDirection direction) {
		CheckersTile startingTile = piece.getCheckersTile();
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction);
	}

	public static CheckersMoveJump Create(CheckersTile startingTile, DiagonalDirection direction) {
		CheckersPiece piece = startingTile.getCheckersPiece();
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction);
	}

	/**
	 * Used for when this jump is part of a multi-jump sequence, where the piece
	 * 	   is not currently on the starting tile.
	 * In this case, the startingTile must be blank.
	 */
	public static CheckersMoveJump Create(CheckersPiece piece, CheckersTile startingTile,
										  DiagonalDirection direction) {
		CheckersTile jumpedTile = startingTile.getNeighborAt(direction);
		CheckersTile destination = jumpedTile.getNeighborAt(direction);
		return new CheckersMoveJump(piece, startingTile, jumpedTile, destination, direction, startingTile.isBlank());
	}

	protected CheckersMoveJump(CheckersPiece piece, CheckersTile startingTile,
							 CheckersTile jumpedTile, CheckersTile destination,
							 DiagonalDirection direction) {
		super(piece, destination);
		this.startingTile = startingTile;
		this.jumpedTile = jumpedTile;
		this.direction = direction;
		this.multiJumpStartingTileNotBlank = false;
	}

	protected CheckersMoveJump(CheckersPiece piece, CheckersTile startingTile,
							   CheckersTile jumpedTile, CheckersTile destination,
							   DiagonalDirection direction,
							   boolean multiJumpStartingTileNotBlank) {
		super(piece, destination);
		this.startingTile = startingTile;
		this.jumpedTile = jumpedTile;
		this.direction = direction;
		this.multiJumpStartingTileNotBlank = multiJumpStartingTileNotBlank;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid()
				&& this.jumpedTile != null
				&& !this.jumpedTile.isBlank()
				&& this.jumpedTile.getCheckersPiece().getPlayer() != null
				&& this.jumpedTile.getCheckersPiece().getPlayer().isOpposite(piece.getPlayer())
				&& (piece.isKing() || direction.isForwardsFor(piece.getPlayer()))
				&& !multiJumpStartingTileNotBlank;
	}

	@Override
	public void execute() {
		super.execute();
		jumpedTile.removePiece();
	}
	
}
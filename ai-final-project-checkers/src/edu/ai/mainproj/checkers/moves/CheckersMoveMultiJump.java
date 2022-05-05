package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

import java.util.*;

/**
 * For a sequence of back-to-back jumps that one piece can do in checkers
 * These multiple jumps are still considered a single move
 *
 * @author Nathan Swartz
 */
public class CheckersMoveMultiJump extends CheckersMove implements Iterable<CheckersMoveJump> {
	
	private final List<CheckersMoveJump> jumps;

	/**
	 * Create a multi-jump out of two jumps
	 * @param jumpBefore
	 * @param jumpAfter
	 * @return new multi-jump move
	 */
	public static CheckersMoveMultiJump CreateAsJoin(
			CheckersMoveJump jumpBefore, CheckersMoveJump jumpAfter) {
		if (jumpBefore == null || jumpAfter == null || jumpBefore.piece == null
				|| jumpBefore.piece != jumpAfter.piece
				|| jumpBefore.destination != jumpAfter.startingTile) {
			return null;
		} else {
			LinkedList<CheckersMoveJump> jumps = new LinkedList<CheckersMoveJump>();
			jumps.add(jumpBefore);
			jumps.add(jumpAfter);
			return new CheckersMoveMultiJump(jumpBefore.piece, jumpAfter.destination, jumps);
		}
	}

	private CheckersMoveMultiJump(CheckersPiece piece,
			CheckersTile destination, List<CheckersMoveJump> jumps) {
		super(piece, destination);
		this.jumps = jumps;
	}

	/**
	 * Returns a deep copy of this object, where jump is added
	 *     to the sequence of jumps at the start of the sequence
	 * DOES NOT modify this object
	 * @param jump to prepend
	 * @return new, deep copy of this with jump prepended
	 */
	public CheckersMoveMultiJump prepend(CheckersMoveJump jump) {
		// check jump != null
		// check pieces of jump and this are the same
		// check jump's destination is the starting tile of this
		if (jump == null || jump.piece != this.piece
			|| jump.destination != this.jumps.get(0).startingTile) { return null; }

		// check jumps do not double back over the jumped tile twice
		for (CheckersMoveJump eachJump : this.jumps) {
			if (eachJump.jumpedTile == jump.jumpedTile) { return null; }
		}

		// if we're at this point everything should be okay to create

		List<CheckersMoveJump> deepCopyJumpsList =
				new LinkedList<CheckersMoveJump>(this.jumps);
		deepCopyJumpsList.add(0, jump);
		return new CheckersMoveMultiJump(this.piece, this.destination, deepCopyJumpsList);
	}

	/**
	 * Returns a deep copy of this object, where jump is added
	 *     to the sequence of jumps at the end of the sequence
	 * DOES NOT modify this object
	 * @param jump to append
	 * @return new, deep copy of this with jump appended
	 */
	public CheckersMoveMultiJump append(CheckersMoveJump jump) {
		// check jump != null
		// check pieces of jump and this are the same
		// check jump's starting tile is the destination tile of this
		if (jump == null || jump.piece != this.piece
				|| jump.startingTile != this.destination) { return null; }

		// check jumps do not double back over the jumped tile twice
		for (CheckersMoveJump eachJump : this.jumps) {
			if (eachJump.jumpedTile == jump.jumpedTile) { return null; }
		}

		// if we're at this point everything should be okay to create

		List<CheckersMoveJump> deepCopyJumpsList =
				new LinkedList<CheckersMoveJump>(this.jumps);
		deepCopyJumpsList.add(jump);
		return new CheckersMoveMultiJump(this.piece, jump.destination, deepCopyJumpsList);
	}

	@Override
	public boolean isValid() {
		if (jumps.size() <= 1) {
			return false;
		}
		for (CheckersMoveJump jump : jumps) {
			if (!jump.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		for (CheckersMoveJump jump : jumps) {
			jump.execute();
		}
	}

	@Override
	public Iterator<CheckersMoveJump> iterator() {
		return jumps.iterator();
	}
	
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		for (CheckersMoveJump jump : jumps) {
			hash *= 953 ^ jump.jumpedTile.hashCode();
		}
		return hash;
	}

	public static class CheckersMoveMultiJumpBuilder {
		// necessary for CheckersMoveMultiJump constructor
		private CheckersPiece piece;
		private CheckersTile destination;
		private List<CheckersMoveJump> jumps;

		private boolean returnNull;

		// optional
		private CheckersTile startingTile;

		public CheckersMoveMultiJumpBuilder(CheckersPiece piece) {
			if (piece == null || piece.getCheckersTile() == null) {
				returnNull = true;
			} else {
				this.piece = piece;
				this.destination = piece.getCheckersTile();
				this.jumps = new LinkedList<CheckersMoveJump>();

				this.startingTile = piece.getCheckersTile();
				returnNull = false;
			}
		}

		public CheckersMoveMultiJumpBuilder(CheckersPiece piece, CheckersTile startingTile) {
			if (piece == null || startingTile == null) {
				returnNull = true;
			} else {
				this.piece = piece;
				this.destination = startingTile;
				this.jumps = new LinkedList<CheckersMoveJump>();

				this.startingTile = startingTile;
				returnNull = false;
			}
		}

		public CheckersMoveMultiJumpBuilder withDirection(DiagonalDirection dir) {
			if (dir == null) { returnNull = true; }
			if (returnNull) { return this; }
			// create jump, starting from current destination, in direction dir
			CheckersMoveJump jump = CheckersMoveJump.CreateAsPartOfMultiJump(
					this.piece, this.destination, dir);
			// update current destination
			this.destination = jump.destination;
			// append this jump onto the existing list of jumps
			this.jumps.add(jump);
			return this;
		}

		public CheckersMoveMultiJumpBuilder withDirections(DiagonalDirection[] dirs) {
			if (dirs == null) { returnNull = true; }
			if (returnNull) { return this; }
			return this.withDirections(Arrays.asList(dirs));
		}

		public CheckersMoveMultiJumpBuilder withDirections(Iterable<DiagonalDirection> dirs) {
			if (dirs == null) { returnNull = true; }
			if (returnNull) { return this; }

			Set<CheckersTile> jumpedTiles = new HashSet<CheckersTile>();
			CheckersTile tile = piece.getCheckersTile();
			for (DiagonalDirection dir : dirs) {
				// CheckersMoveJump.Create will check if the next 2 tiles are off-board
				CheckersMoveJump jump = CheckersMoveJump.CreateAsPartOfMultiJump(piece, tile, dir);
				if (jump == null) { returnNull = true; return this; }
				jumps.add(jump);

				tile = tile.getNeighborAt(dir);
				// if the next jumped tile was already jumped, this jump is impossible
				if (jumpedTiles.contains(tile)) { returnNull = true; return this; }
				jumpedTiles.add(tile);

				tile = tile.getNeighborAt(dir);
			}
			// if there is 1 or fewer jumps, this isn't a multi-jump move
			if (jumpedTiles.size() <= 1) { returnNull = true; }
			return this;
		}

		public CheckersMoveMultiJump build() {
			if (returnNull) { return null; }
			return new CheckersMoveMultiJump(piece, destination, jumps);
		}

	}

}
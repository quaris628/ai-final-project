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
public class CheckersMoveJumpMulti extends CheckersMoveJump implements Iterable<CheckersMoveJumpSingle> {
	
	private final List<CheckersMoveJumpSingle> jumps;

	/**
	 * Create a multi-jump out of two jumps
	 * @param jumpBefore
	 * @param jumpAfter
	 * @return new multi-jump move
	 */
	public static CheckersMoveJumpMulti CreateAsJoin(
			CheckersMoveJumpSingle jumpBefore, CheckersMoveJumpSingle jumpAfter) {
		if (jumpBefore == null || jumpAfter == null || jumpBefore.piece == null
				|| jumpBefore.piece != jumpAfter.piece
				|| jumpBefore.destination != jumpAfter.startingTile) {
			return null;
		} else {
			List<CheckersMoveJumpSingle> jumps = new ArrayList<CheckersMoveJumpSingle>();
			jumps.add(jumpBefore);
			jumps.add(jumpAfter);
			return new CheckersMoveJumpMulti(jumpBefore.piece, jumpAfter.destination, jumps);
		}
	}

	private CheckersMoveJumpMulti(CheckersPiece piece,
								  CheckersTile destination, List<CheckersMoveJumpSingle> jumps) {
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
	@Override
	public CheckersMoveJumpMulti prepend(CheckersMoveJumpSingle jump) {
		// check jump != null
		// check pieces of jump and this are the same
		// check jump's destination is the starting tile of this
		if (jump == null || jump.piece != this.piece
			|| jump.destination != this.jumps.get(0).startingTile) { return null; }

		// check jumps do not double back over the jumped tile twice
		for (CheckersMoveJumpSingle eachJump : this.jumps) {
			if (eachJump.jumpedTile == jump.jumpedTile) { return null; }
		}

		List<CheckersMoveJumpSingle> deepCopyJumpsList =
				new LinkedList<CheckersMoveJumpSingle>(this.jumps);
		deepCopyJumpsList.add(0, jump);
		return new CheckersMoveJumpMulti(this.piece, this.destination, deepCopyJumpsList);
	}

	/**
	 * Returns a deep copy of this object, where jump is added
	 *     to the sequence of jumps at the end of the sequence
	 * DOES NOT modify this object
	 * @param jump to append
	 * @return new, deep copy of this with jump appended
	 */
	@Override
	public CheckersMoveJumpMulti append(CheckersMoveJumpSingle jump) {
		// check jump != null
		// check pieces of jump and this are the same
		// check jump's starting tile is the destination tile of this
		if (jump == null || jump.piece != this.piece
				|| jump.startingTile != this.destination) { return null; }

		// check jumps do not double back over the jumped tile twice
		for (CheckersMoveJumpSingle eachJump : this.jumps) {
			if (eachJump.jumpedTile == jump.jumpedTile) { return null; }
		}

		List<CheckersMoveJumpSingle> deepCopyJumpsList =
				new ArrayList<CheckersMoveJumpSingle>(this.jumps);
		deepCopyJumpsList.add(jump);
		return new CheckersMoveJumpMulti(this.piece, jump.destination, deepCopyJumpsList);
	}

	@Override
	public boolean isValid() {
		if (jumps.size() <= 1) {
			return false;
		}
		for (CheckersMoveJumpSingle jump : jumps) {
			if (!jump.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		CheckersTile home = piece.getCheckersTile();
		this.stringRepresentation = "JumpMove[" + home.column + "," + home.row + "->" + destination.column + "," + destination.row + "]";
		for (CheckersMoveJumpSingle jump : jumps) {
			jump.execute();
		}
	}

	@Override
	public void unexecute() {
		for (int i = jumps.size() - 1; i >= 0; i--) {
			jumps.get(i).unexecute();
		}
	}

	@Override
	public Iterator<CheckersMoveJumpSingle> iterator() {
		return jumps.iterator();
	}
	
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		for (CheckersMoveJumpSingle jump : jumps) {
			hash *= 953 ^ jump.jumpedTile.hashCode();
		}
		return hash;
	}

	// Don't worry too much about this, it isn't ever used (currently)
	public static class CheckersMoveMultiJumpBuilder {
		// necessary for CheckersMoveMultiJump constructor
		private CheckersPiece piece;
		private CheckersTile destination;
		private List<CheckersMoveJumpSingle> jumps;

		private boolean returnNull;

		// optional
		private CheckersTile startingTile;

		public CheckersMoveMultiJumpBuilder(CheckersPiece piece) {
			if (piece == null || piece.getCheckersTile() == null) {
				returnNull = true;
			} else {
				this.piece = piece;
				this.destination = piece.getCheckersTile();
				this.jumps = new LinkedList<CheckersMoveJumpSingle>();

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
				this.jumps = new LinkedList<CheckersMoveJumpSingle>();

				this.startingTile = startingTile;
				returnNull = false;
			}
		}

		public CheckersMoveMultiJumpBuilder withDirection(DiagonalDirection dir) {
			if (dir == null) { returnNull = true; }
			if (returnNull) { return this; }
			// create jump, starting from current destination, in direction dir
			CheckersMoveJumpSingle jump = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
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
				CheckersMoveJumpSingle jump = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(piece, tile, dir);
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

		public CheckersMoveJumpMulti build() {
			if (returnNull) { return null; }
			return new CheckersMoveJumpMulti(piece, destination, jumps);
		}

	}

	@Override
	public String toString() {
		if (stringRepresentation != null)
			return stringRepresentation;
		return "MJump[]";
	}

}
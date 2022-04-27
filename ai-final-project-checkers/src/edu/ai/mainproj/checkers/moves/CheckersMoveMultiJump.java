package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * For a sequence of back-to-back jumps that one piece can do in checkers
 * These multiple jumps are still considered a single move
 *
 * @author Nathan Swartz
 */
public class CheckersMoveMultiJump extends CheckersMove {
	
	private final List<CheckersMoveJump> jumps;

	public static CheckersMoveMultiJump Create(
			CheckersPiece piece, DiagonalDirection[] directions) {
		return Create(piece, Arrays.asList(directions));
	}

	public static CheckersMoveMultiJump Create(
			CheckersPiece piece, Iterable<DiagonalDirection> directions) {
		// TODO rewrite to check if the move is impossible
		// check if move is impossible
		
		// null checks
		if (piece == null
			|| directions == null
			|| piece.getCheckersTile() == null
			|| directions.size() <= 1) { return null; }
		
		// check if move goes off of the board or turns back on itself
		// TODO check jumps do not double back over the same tile
		Set<CheckersTile> jumpedTiles = new HashSet<CheckersTile>();
		CheckersTile tile = piece.getCheckersTile();
		for (DiagonalDirection dir : directions) {
			tile = tile.getNeighborAt(dir);
			if (tile == null || jumpedTiles.contains(tile)) { return null; }
			jumpedTiles.add(tile);
			tile = tile.getNeighborAt(dir);
			if (tile == null) { return null; }
		}
		
		// vvv old method, review very closely or rewrite vvv
		List<CheckersMoveJump> jumps = new LinkedList<CheckersMoveJump>();
		Iterator<DiagonalDirection> dirIter = directions.iterator();
		// tile variable is used like an iterator
		// parallel to the direction iterator
		CheckersTile tile = piece.getCheckersTile();

		// first jump (piece is on the starting tile)
		DiagonalDirection dir = dirIter.next();
		jumps.add(CheckersMoveJump.Create(piece, dir));
		tile = tile.getNeighborAt(dir).getNeighborAt(dir);

		// all subsequent jumps (piece is not on the starting tile)
		while (dirIter.hasNext()) {
			dir = dirIter.next();
			jumps.add(CheckersMoveJump.CreateAsPartOfMultiJump(piece, tile, dir));
			tile = tile.getNeighborAt(dir).getNeighborAt(dir);
		}

		return new CheckersMoveMultiJump(piece, tile, jumps);
	}

	/**
	 * Create a multi-jump that pre-pends the jumpBefore onto the jumps
	 * @param jumpBefore
	 * @param jumps
	 * @return new CheckersMoveMultiJump
	 */
	public static CheckersMoveMultiJump Create(
			CheckersMoveJump jumpBefore, CheckersMoveMultiJump jumps) {
		// TODO check jumps do not double back over the same piece twice
		// check pieces of jumpBefore and jumps are the same
		if (jumpBefore.piece != jumps.piece) { return null; }
		// check destination of jumpBefore is the start of the first jump of jumps
		else if (jumpBefore.destination != jumps.jumps.get(0).startingTile) {
			return null;
		}
		// if we're at this point everything should be okay to create
		List<CheckersMoveJump> deepCopyJumps = new LinkedList<CheckersMoveJump>();
		deepCopyJumps.add(jumpBefore);
		deepCopyJumps.addAll(jumps.jumps);
		return new CheckersMoveMultiJump(jumpBefore.piece, jumps.destination, deepCopyJumps);
	}

	/**
	 * Create a multi-jump that appends the jumpAfter onto the jumps
	 * @param jumps
	 * @param jumpAfter
	 * @return new CheckersMoveMultiJump
	 */
	public static CheckersMoveMultiJump Create(
			CheckersMoveMultiJump jumps, CheckersMoveJump jumpAfter) {
		// TODO check jumps do not double back over the same piece twice
		// check pieces of jumpAfter and jumps are the same
		if (jumps.piece != jumpAfter.piece) { return null; }
		// check destination of last jump of jumps is the same as the starting tile of jumpAfter
		else if (jumps.jumps.get(jumps.jumps.size() - 1).destination != jumpAfter.startingTile) {
			return null;
		}
		// if we're at this point everything should be okay to create
		List<CheckersMoveJump> deepCopyJumps = new LinkedList<CheckersMoveJump>(jumps.jumps);
		deepCopyJumps.add(jumpAfter);
		return new CheckersMoveMultiJump(jumpAfter.piece, jumps.destination, deepCopyJumps);
	}

	/**
	 * Create a multi-jump that is only one jump
	 * Should be used only if it is only going to be used as
	 *     one part in a whole multi-jump sequence with 2 or more jumps
	 * @param jump
	 * @return new CheckersMoveMultiJump
	 */
	public static CheckersMoveMultiJump Create(CheckersMoveJump jump) {
		List<CheckersMoveJump> jumps = new LinkedList<CheckersMoveJump>();
		jumps.add(jump);
		return new CheckersMoveMultiJump(jump.piece, jump.destination, jumps);
	}
	
	protected CheckersMoveMultiJump(CheckersPiece piece,
			CheckersTile destination, List<CheckersMoveJump> jumps) {
		super(piece, destination);
		this.jumps = jumps;
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
	
}
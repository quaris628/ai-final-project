package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

// TODO check imports
// maybe we need to import Iterable?
// maybe we don't need * in java.util?
import java.util.*;

/**
 * For a sequence of back-to-back jumps that one piece can do in checkers
 * These multiple jumps are still considered a single move
 *
 * @author Nathan Swartz
 */
public class CheckersMoveMultiJump extends CheckersMove implements Iterable<CheckersMoveJump> {
	
	private final List<CheckersMoveJump> jumps;

	public static CheckersMoveMultiJump Create(
			CheckersPiece piece, DiagonalDirection[] directions) {
		if (directions == null) { return null; }
		return Create(piece, Arrays.asList(directions));
	}

	public static CheckersMoveMultiJump Create(
			CheckersPiece piece, Iterable<DiagonalDirection> directions) {
		// check if move is impossible
		
		// null parameter checks
		if (piece == null || directions == null
			|| piece.getCheckersTile() == null) { return null; }

		Set<CheckersTile> jumpedTiles = new HashSet<CheckersTile>();
        List<CheckersMoveJump> jumps = new LinkedList<CheckersMoveJump>();
		CheckersTile tile = piece.getCheckersTile();
		for (DiagonalDirection dir : directions) {
            // this create method will check if the next 2 tiles are off-board
            CheckersMoveJump jump = CheckersMoveJump.CreateAsPartOfMultiJump(piece, tile, dir);
            if (jump == null) { return null; }
            jumps.add(jump);

            tile = tile.getNeighborAt(dir);
            // if the next jumped tile was already jumped, this jump is impossible
			if (jumpedTiles.contains(tile)) { return null; }
			jumpedTiles.add(tile);

			tile = tile.getNeighborAt(dir);
		}
        // if there is 1 or fewer jumps, this isn't a multi-jump move
        if (jumpedTiles.size() <= 1) { return null; }

        // If the code has reached this point, the move is not impossible

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
		if (jumpBefore == null || jumps == null) { return null; }
		
		// check pieces of jumpBefore and jumps are the same
		if (jumpBefore.piece != jumps.piece) { return null; }
		
		// check jumpBefore destination is the starting tile of jumps
		if (jumpBefore.destination != jumps.jumps.get(0).startingTile) {
			return null;
		}
		
		// check jumps do not double back over the same piece twice
		Set<CheckersTile> jumpedTiles = new HashSet<CheckersTile>();
		jumpedTiles.add(jumpBefore.jumpedTile);
		for (CheckersMoveJump jump : jumps) {
			if (jump == null) { return null; }
			// if the next jumped tile was already jumped, this jump is impossible
			if (jumpedTiles.contains(jump.jumpedTile)) { return null; }
			jumpedTiles.add(jump.jumpedTile);
		}
		
		// if we're at this point everything should be okay to create
		List<CheckersMoveJump> deepCopyJumpsList = new LinkedList<CheckersMoveJump>();
		deepCopyJumpsList.add(jumpBefore);
		deepCopyJumpsList.addAll(jumps.jumps);
		return new CheckersMoveMultiJump(jumpBefore.piece, jumps.destination, deepCopyJumpsList);
	}

	/**
	 * Create a multi-jump that appends the jumpAfter onto the jumps
	 * @param jumps
	 * @param jumpAfter
	 * @return new CheckersMoveMultiJump
	 */
	public static CheckersMoveMultiJump Create(
			CheckersMoveMultiJump jumps, CheckersMoveJump jumpAfter) {
		if (jumps == null || jumpAfter == null) { return null; }
		
		// check pieces of jumps and jumpAfter are the same
		if (jumps.piece != jumpAfter.piece) { return null; }
		
		// check destination of last jump of jumps is the same as the starting tile of jumpAfter
		if (jumps.jumps.get(jumps.jumps.size() - 1).destination != jumpAfter.startingTile) {
			return null;
		}
		
		// check jumps do not double back over the same piece twice
		Set<CheckersTile> jumpedTiles = new HashSet<CheckersTile>();
		jumpedTiles.add(jumpAfter.jumpedTile);
		for (CheckersMoveJump jump : jumps) {
			if (jump == null) { return null; }
			// if the next jumped tile was already jumped, this jump is impossible
			if (jumpedTiles.contains(jump.jumpedTile)) { return null; }
			jumpedTiles.add(jump.jumpedTile);
		}
		
		// if we're at this point everything should be okay to create
		List<CheckersMoveJump> deepCopyJumpsList = new LinkedList<CheckersMoveJump>();
		deepCopyJumpsList.addAll(jumps.jumps);
		deepCopyJumpsList.add(jumpAfter);
		return new CheckersMoveMultiJump(jumpAfter.piece, jumps.destination, deepCopyJumpsList);
		
		
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
	
	@Override
	public Iterator<CheckersMoveJump> iterator() {
		return jumps.iterator();
	}
	
}
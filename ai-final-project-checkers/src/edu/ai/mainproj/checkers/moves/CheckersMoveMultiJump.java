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

	// create a multi-jump that pre-pends the jumpBefore onto the jumps
	public static CheckersMoveMultiJump Create(
			CheckersMoveJump jumpBefore, CheckersMoveMultiJump jumps) {
		// TODO
		return null;
	}

	// create a multi-jump that is only one jump
	public static CheckersMoveMultiJump Create(CheckersMoveJump jump) {
		// TODO
		return null;
	}
	
	protected CheckersMoveMultiJump(CheckersPiece piece,
			CheckersTile destination, List<CheckersMoveJump> jumps) {
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
	public void execute() {
		for (CheckersMoveJump jump : jumps) {
			jump.execute();
		}
	}
	
}
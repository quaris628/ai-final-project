package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMoveNormal;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Move class
 *
 * Tests methods:
 *  - Constructor
 *  - isValid
 *  - execute
 *  - unexecute
 *  - getStartingTile
 *
 * @author Nathan Swartz
 */
public class MoveTests {

    private static final GridBoard blankBoard = new GridBoard(8, 8);

    public MoveTests() {}

    // --------------------------------
    // CONSTRUCTOR
    // --------------------------------

    @Test
    public void testConstructor_allNull_exception() {
        // TODO
    }

    @Test
    public void testConstructor_pieceNull_exception() {
        // TODO
    }

    @Test
    public void testConstructor_dirNull_exception() {
        // TODO
    }

    @Test
    public void testConstructor_normal_succeeds() {
        // TODO
    }

    // --------------------------------
    // IS VALID
    // --------------------------------

    @Test
    public void testIsValid_destinationNotBlank_false() {
        // TODO

    }

    @Test
    public void testIsValid_destinationBlank_true() {
        // TODO

    }

    // --------------------------------
    // EXECUTE
    // --------------------------------

    @Test
    public void testExecute_destinationNotBlank_exception() {
        // TODO
    }

    @Test
    public void testExecute_destinationBlank() {
        // TODO
    }

}
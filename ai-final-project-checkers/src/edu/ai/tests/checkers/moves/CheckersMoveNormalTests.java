package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMoveNormal;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveNormal class
 *
 * Tests methods:
 *  - Constructor (does not throw exception)
 *  - isValid
 *
 * @author Nathan Swartz
 */
public class CheckersMoveNormalTests {

    public CheckersMoveNormalTests() {}

    // --------------------------------
    // CONSTRUCTOR
    // --------------------------------

    @Test
    public void testConstructor_allNull_fails() {
        try {
            new CheckersMoveNormal(null, null);
            fail();
        } catch (Exception e) { assertTrue(true); }
    }

    @Test
    public void testConstructor_pieceNull_fails() {
        try {
            new CheckersMoveNormal(null, DiagonalDirection.FORWARD_RIGHT);
            fail();
        } catch (Exception e) { assertTrue(true); }
    }

    @Test
    public void testConstructor_dirNull_fails() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        try {
            new CheckersMoveNormal(piece, null);
            fail();
        } catch (Exception e) { assertTrue(true); }
    }

    @Test
    public void testConstructor_piece01dirForwardLeft_succeeds() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        try {
            new CheckersMoveNormal(piece, DiagonalDirection.FORWARD_LEFT);
            assertTrue(true);
        } catch (Exception e) { fail(); }
    }

    @Test
    public void testConstructor_piece45dirBackwardRight_succeeds() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        try {
            new CheckersMoveNormal(piece, DiagonalDirection.BACKWARD_RIGHT);
            assertTrue(true);
        } catch (Exception e) { fail(); }
    }

    // --------------------------------
    // IS VALID
    // --------------------------------

    @Test
    public void testIsValid_normalPiece45dirForwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = new CheckersMoveNormal(
                piece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
    }

    @Test
    public void testIsValid_normalPiece45dirBackwardRight_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = new CheckersMoveNormal(
                piece, DiagonalDirection.BACKWARD_RIGHT);

        assertFalse(move.isValid());
    }

    // TODO add more tests

}

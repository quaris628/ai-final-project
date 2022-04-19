package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMoveJump;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveJump class
 *
 * Tests methods:
 *  - Create (does not throw exception)
 *  - isValid TODO
 *  - execute TODO
 *  Also tests kinging behavior TODO
 *
 * @author Nathan Swartz
 */
public class CheckersMoveJumpTests {

    public CheckersMoveJumpTests() {}

    // --------------------------------
    // CREATE
    // --------------------------------

    @Test
    public void testCreate_pieceNull_exception() {
        try {
            CheckersMoveJump.Create(null, DiagonalDirection.FORWARD_LEFT);
            fail();
        } catch (IllegalArgumentException e) { assertTrue(true); }
    }

    @Test
    public void testCreate_dirNull_exception() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        try {
            CheckersMoveJump.Create(piece, null);
            fail();
        } catch (IllegalArgumentException e) { assertTrue(true); }
    }

    @Test
    public void testCreate_offBoard_exception() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        try {
            CheckersMoveJump.Create(piece, DiagonalDirection.FORWARD_LEFT);
            fail();
        } catch (IllegalArgumentException e) { assertTrue(true); }
    }

    @Test
    public void testCreate_normal_succeeds() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        try {
            CheckersMoveJump.Create(piece, DiagonalDirection.BACKWARD_RIGHT);
            assertTrue(true);
        } catch (IllegalArgumentException e) { fail(); }
    }

    // --------------------------------
    // IS VALID & EXECUTE
    // --------------------------------

    // normal black pieces cannot jump backwards

    @Test
    public void testIsValid_normalBlackBackward_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(6, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertFalse(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_testExecute_normalBlackForward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertFalse(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    // normal red pieces cannot jump forwards

    @Test
    public void testIsValid_normalRedBackward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertFalse(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_testExecute_normalRedBackward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(6, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertFalse(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);
        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    // king black pieces may jump both forward and backward

    @Test
    public void testIsValid_testExecute_kingBlackForward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        // make jumper a king
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK,
                board.getCheckersTile(0, 1));
        jumperPiece.moveTo(start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertTrue(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    @Test
    public void testIsValid_kingBlackBackward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(6, 7);
        // make jumper a king
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK,
                board.getCheckersTile(0, 1));
        jumperPiece.moveTo(start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertTrue(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    // king red pieces may jump both forward and backward

    @Test
    public void testIsValid_testExecute_kingRedBackward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(6, 7);
        // make jumper a king
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED,
                board.getCheckersTile(7, 0));
        jumperPiece.moveTo(start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertTrue(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);
        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    @Test
    public void testIsValid_kingRedForward_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        // make jumper a king
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED,
                board.getCheckersTile(7, 0));
        jumperPiece.moveTo(start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertTrue(jumperPiece.isKing());
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        // check tiles
        assertTrue(start.isBlank());
        assertTrue(jumped.isBlank());
        assertEquals(jumperPiece, dest.getCheckersPiece());
        // check pieces
        assertNull(jumpedPiece.getCheckersTile());
        assertEquals(dest, jumperPiece.getCheckersTile());
    }

    // First neighbor has piece of opposite player

    @Test
    public void testIsValid_blackJumpBlank_false() {
        // TODO
    }

    @Test
    public void testIsValid_blackJumpBlack_false() {
        // TODO
    }

    @Test
    public void testIsValid_testExecute_blackJumpRed_true() {
        // TODO
    }

    // Second neighbor is blank

    @Test
    public void testIsValid_jumpToPiece_false() {
        // TODO
    }

    @Test
    public void testIsValid_testExecute_jumpToBlank_true() {
        // TODO
    }

    // --------------------------------
    // KINGING BEHAVIOR
    // --------------------------------

    @Test
    public void testKinging_jumpMidBoard_false() {
        // TODO
    }

    @Test
    public void testKinging_jumpBlackTop_true() {
        // TODO
    }

    @Test
    public void testKinging_jumpRedBottom_true() {
        // TODO
    }


}

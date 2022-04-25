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
 *  - Create
 *  - isValid
 *  - execute
 *  Also tests kinging behavior
 *
 * @author Nathan Swartz
 */
public class CheckersMoveJumpTests {

    public CheckersMoveJumpTests() {}

    // --------------------------------
    // CREATE
    // --------------------------------

    @Test
    public void testCreate_pieceNull_null() {
        CheckersMoveJump move = CheckersMoveJump.Create(
                null, DiagonalDirection.FORWARD_LEFT);
        assertNull(move);
    }

    @Test
    public void testCreate_dirNull_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveJump move = CheckersMoveJump.Create(piece, null);
        assertNull(move);
    }

    @Test
    public void testCreate_jumpedOffBoard_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveJump move = CheckersMoveJump.Create(
                piece, DiagonalDirection.FORWARD_LEFT);
        assertNull(move);
    }
	
    @Test
    public void testCreate_destOffBoard_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(2, 1));
        CheckersMoveJump move = CheckersMoveJump.Create(
                piece, DiagonalDirection.FORWARD_LEFT);
        assertNull(move);
    }


    @Test
    public void testCreate_normal_succeeds() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveJump move = CheckersMoveJump.Create(
                piece, DiagonalDirection.BACKWARD_RIGHT);
        assertNotNull(move);
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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertTrue(jumped.isBlank());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_blackJumpBlack_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_testExecute_blackJumpRed_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
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

    // Second neighbor is blank

    @Test
    public void testIsValid_jumpToPiece_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);
        new CheckersPiece(PlayerType.RED, dest);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertFalse(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_testExecute_jumpToBlank_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
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

    // --------------------------------
    // KINGING BEHAVIOR
    // --------------------------------

    @Test
    public void testKinging_jumpMidBoard_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertFalse(jumperPiece.isKing());
    }

    @Test
    public void testKinging_jumpBlackTop_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(2, 5);
        CheckersTile jumped = board.getCheckersTile(1, 6);
        CheckersTile dest = board.getCheckersTile(0, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertTrue(jumperPiece.isKing());
    }

    @Test
    public void testKinging_jumpRedBottom_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(5, 4);
        CheckersTile jumped = board.getCheckersTile(6, 5);
        CheckersTile dest = board.getCheckersTile(7, 6);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.BLACK, jumped);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertTrue(dest.isBlank());

        CheckersMoveJump move = CheckersMoveJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertTrue(jumperPiece.isKing());
    }

}

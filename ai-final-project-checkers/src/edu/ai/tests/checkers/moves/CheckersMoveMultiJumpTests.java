package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMoveMultiJump;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveMultiJump class
 *
 * TODO update which are still to-dos
 * Tests methods:
 *  - Create
 *  - isValid TODO
 *  - execute TODO
 *  Also tests kinging behavior TODO
 *
 * @author Nathan Swartz
 */
public class CheckersMoveMultiJumpTests {

    public CheckersMoveMultiJumpTests() {}

    // --------------------------------
    // CREATE
    // --------------------------------

    @Test
    public void testCreate_pieceNull_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			}
		CheckersMoveMultiJump move =
				CheckersMoveMultiJump.Create(null, dirs);
        assertNull(move);
    }

    @Test
    public void testCreate_dirNull_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, null);
        assertNull(move);
    }
	
    @Test
    public void testCreate_emptyDirs_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[];
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreate_oneDir_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT
			}
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
    
    @Test
    public void testCreate_jumpedOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			}
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreate_destOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			}
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(2, 1));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreate_normalConditions_succeeds() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			}
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNotNull(move);
    }

    // --------------------------------
    // IS VALID
    // --------------------------------

    // normal black pieces cannot jump backwards

    @Test
    public void testIsValid_normalBackward_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_kingBackward_true() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertTrue(move.isValid());
    }

    // First neighbor has piece of opposite player

    @Test
    public void testIsValid_jumpBlank_false() {
		// TODO
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile jumped = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertTrue(jumped.isBlank());
        assertTrue(dest.isBlank());

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_blackJumpBlack_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    // middle destinations are blank

    @Test
    public void testIsValid_middleDestinationNotBlank_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_finalDestinationNotBlank_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }
	
    @Test
    public void testIsValid_normalInAndOutOfKingRow_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalConditions2_true() {
		// TODO
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
		CheckersTile midDest = board.getCheckersTile(4, 5);
		CheckersTile jumped2 = board.getCheckersTile(3, 4);
        CheckersTile dest = board.getCheckersTile(2, 3);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
		CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
		DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_RIGHT,
				DiagonalDirection.FORWARD_LEFT
			}
		
        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
		assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(midDest.isBlank());
		assertTrue(dest.isBlank());
		
		// TODO

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalConditions3_true() {
		// TODO
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 5);
        CheckersTile jumped1 = board.getCheckersTile(5, 6);
		CheckersTile midDest = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece = new CheckersPiece(PlayerType.RED, jumped);
        new CheckersPiece(PlayerType.RED, dest);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece, jumped.getCheckersPiece());
        assertFalse(dest.isBlank());

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    // --------------------------------
    // KINGING BEHAVIOR
    // --------------------------------

    @Test
    public void testKinging_blackDestMidBoard_false() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertFalse(jumperPiece.isKing());
    }

    @Test
    public void testKinging_blackDestTop_true() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertTrue(jumperPiece.isKing());
    }

    @Test
    public void testKinging_redDestBottom_true() {
		// TODO
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

        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);

        assertTrue(move.isValid());
        move.execute();

        assertTrue(jumperPiece.isKing());
    }

}

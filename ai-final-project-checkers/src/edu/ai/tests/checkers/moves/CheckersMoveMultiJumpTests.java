package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMoveMultiJump;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveMultiJump class
 *
 * Tests methods:
 *  - Create
 *  - isValid
 *  - execute
 *  Also tests kinging behavior
 *
 * @author Nathan Swartz
 */
public class CheckersMoveMultiJumpTests {

    public CheckersMoveMultiJumpTests() {}

    // --------------------------------
    // CREATE direction array
    // --------------------------------

    @Test
    public void testCreateDirArray_pieceNull_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersMoveMultiJump move =
				CheckersMoveMultiJump.Create(null, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirArray_dirNull_null() {
        DiagonalDirection[] dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreateDirArray_emptyDirs_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[0];
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreateDirArray_oneDir_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
    
    @Test
    public void testCreateDirArray_jumpedOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreateDirArray_destOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(2, 1));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirArray_normalConditions_succeeds() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNotNull(move);
    }

    // --------------------------------
    // CREATE directions iterable
    // --------------------------------

    @Test
    public void testCreateDirIter_pieceNull_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        dirs.add(DiagonalDirection.FORWARD_LEFT);

        CheckersMoveMultiJump move =
                CheckersMoveMultiJump.Create(null, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_dirNull_null() {
        List<DiagonalDirection> dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_emptyDirs_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_oneDir_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_jumpedOffBoard_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_destOffBoard_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(2, 1));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_normalConditions_succeeds() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNotNull(move);
    }

    // --------------------------------
    // CREATE prepend
    // --------------------------------

    // TODO
    @Test
    public void testCreatePrepend_pieceNull_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_LEFT
        };
        CheckersMoveMultiJump move =
                CheckersMoveMultiJump.Create(null, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_dirNull_null() {
        DiagonalDirection[] dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_emptyDirs_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[0];
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_oneDir_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_LEFT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_jumpedOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_LEFT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_destOffBoard_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_LEFT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(2, 1));
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_normalConditions_succeeds() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_LEFT
        };
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
    public void testIsValid_normalBackward1_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.BACKWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(6, 5);
        CheckersTile jumped2 = board.getCheckersTile(5, 6);
        CheckersTile destFinal = board.getCheckersTile(4, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalBackward2_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.BACKWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(3, 4);
        CheckersTile destMid = board.getCheckersTile(2, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(4, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_kingBackward_true() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.BACKWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(3, 4);
        CheckersTile destMid = board.getCheckersTile(2, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(4, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK,
                board.getCheckersTile(0, 1));
        jumperPiece.moveTo(start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertTrue(move.isValid());
    }

    @Test
    public void testIsValid_jumpBlank_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertTrue(jumped1.isBlank());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_blackJumpBlack_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.BLACK, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.BLACK, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_middleDestinationNotBlank_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);
        new CheckersPiece(PlayerType.RED, destMid);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_finalDestinationNotBlank_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);
        new CheckersPiece(PlayerType.RED, destFinal);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }
	
    @Test
    public void testIsValid_normalInAndOutOfKingRow_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.BACKWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(2, 3);
        CheckersTile jumped1 = board.getCheckersTile(1, 4);
        CheckersTile destMid = board.getCheckersTile(0, 5);
        CheckersTile jumped2 = board.getCheckersTile(1, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_revisitTile_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.BACKWARD_LEFT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(5, 4);
        CheckersTile destFinal = board.getCheckersTile(6, 3);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalConditions2_true() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertTrue(move.isValid());
    }

    @Test
    public void testIsValid_normalConditions3_true() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(7, 3);
        CheckersTile jumped1 = board.getCheckersTile(6, 4);
        CheckersTile destMid1 = board.getCheckersTile(5, 5);
        CheckersTile jumped2 = board.getCheckersTile(4, 4);
        CheckersTile destMid2 = board.getCheckersTile(3, 3);
        CheckersTile jumped3 = board.getCheckersTile(2, 4);
        CheckersTile destFinal = board.getCheckersTile(1, 5);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersPiece jumpedPiece3 = new CheckersPiece(PlayerType.RED, jumped3);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid1.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destMid2.isBlank());
        assertEquals(jumpedPiece3, jumped3.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertTrue(move.isValid());
    }


    // --------------------------------
    // EXECUTE
    // --------------------------------

    @Test
    public void testExecute_normalConditions2() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_LEFT
            };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 4);
        CheckersTile destFinal = board.getCheckersTile(2, 3);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertTrue(start.isBlank());
        assertTrue(jumped1.isBlank());
        assertNull(jumpedPiece1.getCheckersTile());
        assertTrue(destMid.isBlank());
        assertTrue(jumped2.isBlank());
        assertNull(jumpedPiece2.getCheckersTile());
        assertFalse(destFinal.isBlank());
        assertEquals(jumperPiece, destFinal.getCheckersPiece());
        assertEquals(jumperPiece.getCheckersTile(), destFinal);
    }

    @Test
    public void testExecute_normalConditions3() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_LEFT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(7, 3);
        CheckersTile jumped1 = board.getCheckersTile(6, 4);
        CheckersTile destMid1 = board.getCheckersTile(5, 5);
        CheckersTile jumped2 = board.getCheckersTile(4, 4);
        CheckersTile destMid2 = board.getCheckersTile(3, 3);
        CheckersTile jumped3 = board.getCheckersTile(2, 4);
        CheckersTile destFinal = board.getCheckersTile(1, 5);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersPiece jumpedPiece3 = new CheckersPiece(PlayerType.RED, jumped3);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid1.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destMid2.isBlank());
        assertEquals(jumpedPiece3, jumped3.getCheckersPiece());
        assertTrue(destFinal.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertTrue(start.isBlank());
        assertTrue(jumped1.isBlank());
        assertNull(jumpedPiece1.getCheckersTile());
        assertTrue(destMid1.isBlank());
        assertTrue(jumped2.isBlank());
        assertNull(jumpedPiece2.getCheckersTile());
        assertTrue(destMid2.isBlank());
        assertTrue(jumped3.isBlank());
        assertNull(jumpedPiece3.getCheckersTile());
        assertFalse(destFinal.isBlank());
        assertEquals(jumperPiece, destFinal.getCheckersPiece());
        assertEquals(jumperPiece.getCheckersTile(), destFinal);
    }

    // --------------------------------
    // KINGING BEHAVIOR
    // --------------------------------

    @Test
    public void testKinging_blackDestMidBoard_false() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertFalse(jumperPiece.isKing());
    }

    @Test
    public void testKinging_blackDestTop_true() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.FORWARD_RIGHT,
                DiagonalDirection.FORWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(3, 4);
        CheckersTile destMid = board.getCheckersTile(2, 5);
        CheckersTile jumped2 = board.getCheckersTile(1, 6);
        CheckersTile destFinal = board.getCheckersTile(0, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertTrue(jumperPiece.isKing());
    }

    @Test
    public void testKinging_redDestBottom_true() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
                DiagonalDirection.BACKWARD_RIGHT,
                DiagonalDirection.BACKWARD_RIGHT
        };
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(3, 2);
        CheckersTile jumped1 = board.getCheckersTile(4, 3);
        CheckersTile destMid = board.getCheckersTile(5, 4);
        CheckersTile jumped2 = board.getCheckersTile(6, 5);
        CheckersTile destFinal = board.getCheckersTile(7, 6);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.BLACK, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.BLACK, jumped2);
        CheckersMoveMultiJump move = CheckersMoveMultiJump.Create(jumperPiece, dirs);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertTrue(jumperPiece.isKing());
    }

}

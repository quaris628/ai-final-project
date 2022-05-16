package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMoveJumpMulti;
import edu.ai.mainproj.checkers.moves.CheckersMoveJumpSingle;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveJumpMulti class
 * <p>
 * Tests methods:
 * - Create
 * - isValid
 * - execute
 * Also tests kinging behavior
 *
 * @author Nathan Swartz
 */

public class CheckersMoveJumpMultiTests {

    public CheckersMoveJumpMultiTests() {
    }

    // --------------------------------
    // CREATE AS JOIN
    // --------------------------------
    // TODO

    @Test
    public void createAsJoin_nullAndNull_null() {

    }

    @Test
    public void createAsJoin_jumpAndNull_null() {

    }

    @Test
    public void createAsJoin_nullAndJump_null() {

    }

    @Test
    public void createAsJoin_dest1NotEqualsStart2_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 5);
        CheckersTile jumped1 = board.getCheckersTile(5, 6);
        CheckersTile midDest1 = board.getCheckersTile(4, 7);
        CheckersTile midDest2 = board.getCheckersTile(4, 3);
        CheckersTile jumped2 = board.getCheckersTile(3, 2);
        CheckersTile dest = board.getCheckersTile(2, 1);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, midDest2, DiagonalDirection.FORWARD_LEFT);

        // sanity check
        assertNotNull(jump1);
        assertNotNull(jump2);

        CheckersMoveJumpMulti multiJump = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

        assertNull(multiJump);
    }

    @Test
    public void createAsJoin_normal_notNull() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 5);
        CheckersTile jumped1 = board.getCheckersTile(5, 6);
        CheckersTile midDest = board.getCheckersTile(4, 7);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile dest = board.getCheckersTile(2, 5);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, midDest, DiagonalDirection.FORWARD_LEFT);

        // sanity check
        assertNotNull(jump1);
        assertNotNull(jump2);

        CheckersMoveJumpMulti multiJump = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

        assertNotNull(multiJump);
    }

    // other
    // TODO move vvv below method vvv to a new category

    @Test
    public void testCreationByAppend_revisitTile_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(0, 3);
        CheckersTile jumped1 = board.getCheckersTile(1, 2);
        CheckersTile dest1 = board.getCheckersTile(2, 1);
        CheckersTile jumped2 = board.getCheckersTile(3, 2);
        CheckersTile dest2 = board.getCheckersTile(4, 3);
        CheckersTile jumped3 = board.getCheckersTile(3, 4);
        CheckersTile dest3 = board.getCheckersTile(2, 5);
        CheckersTile jumped4 = board.getCheckersTile(1, 4);
        CheckersTile dest4 = start;
        CheckersTile jumped5 = jumped1;
        CheckersTile destFinal = dest1;
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersPiece jumpedPiece3 = new CheckersPiece(PlayerType.RED, jumped3);
        CheckersPiece jumpedPiece4 = new CheckersPiece(PlayerType.RED, jumped4);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.BACKWARD_LEFT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, dest1, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpSingle jump3 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, dest2, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump4 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, dest3, DiagonalDirection.FORWARD_LEFT);
        CheckersMoveJumpSingle jump5 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, dest4, DiagonalDirection.BACKWARD_LEFT);
        CheckersMoveJumpMulti initialMultiMove = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);
        CheckersMoveJumpMulti move = initialMultiMove.append(jump3).append(jump4).append(jump5);

        // check starting conditions, for my sanity
        // TODO

        assertNull(move);
    }


    // --------------------------------
    // BUIDER
    // --------------------------------
    // TODO much later b/c I don't think the builder pattern is ever used
    // but maybe these commented out methods would be helpful in planning
    //     and writing the tests if I do get around to it
    /*
    @Test
    public void testCreateDirArray_pieceNull_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[] {
				DiagonalDirection.FORWARD_LEFT,
				DiagonalDirection.FORWARD_LEFT
			};
		CheckersMoveJumpMulti move =
				CheckersMoveJumpMulti.Create(null, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirArray_dirNull_null() {
        DiagonalDirection[] dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
        assertNull(move);
    }
	
    @Test
    public void testCreateDirArray_emptyDirs_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[0];
		CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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

        CheckersMoveJumpMulti move =
                CheckersMoveJumpMulti.Create(null, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_dirNull_null() {
        List<DiagonalDirection> dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_emptyDirs_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
        assertNull(move);
    }

    @Test
    public void testCreateDirIter_oneDir_null() {
        List<DiagonalDirection> dirs = new LinkedList<DiagonalDirection>();
        dirs.add(DiagonalDirection.FORWARD_LEFT);
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move =
                CheckersMoveJumpMulti.Create(null, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_dirNull_null() {
        DiagonalDirection[] dirs = null;
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
        assertNull(move);
    }

    // TODO
    @Test
    public void testCreatePrepend_emptyDirs_null() {
        DiagonalDirection[] dirs = new DiagonalDirection[0];
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
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
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.Create(piece, dirs);
        assertNotNull(move);
    }
    //*/

    // --------------------------------
    // IS VALID
    // --------------------------------

    // normal black pieces cannot jump backwards

    @Test
    public void testIsValid_normalBackward1_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(6, 5);
        CheckersTile jumped2 = board.getCheckersTile(5, 6);
        CheckersTile destFinal = board.getCheckersTile(4, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(3, 4);
        CheckersTile destMid = board.getCheckersTile(2, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(4, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.BLACK, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.BLACK, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);
        new CheckersPiece(PlayerType.RED, destMid);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertFalse(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertTrue(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_finalDestinationNotBlank_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);
        new CheckersPiece(PlayerType.RED, destFinal);

        // check starting conditions, for my sanity
        assertEquals(jumperPiece, start.getCheckersPiece());
        assertEquals(jumpedPiece1, jumped1.getCheckersPiece());
        assertTrue(destMid.isBlank());
        assertEquals(jumpedPiece2, jumped2.getCheckersPiece());
        assertFalse(destFinal.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalInAndOutOfKingRow_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(2, 3);
        CheckersTile jumped1 = board.getCheckersTile(1, 4);
        CheckersTile destMid = board.getCheckersTile(0, 5);
        CheckersTile jumped2 = board.getCheckersTile(1, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(7, 2);
        CheckersTile jumped1 = board.getCheckersTile(6, 3);
        CheckersTile destMid1 = board.getCheckersTile(5, 4);
        CheckersTile jumped2 = board.getCheckersTile(4, 3);
        CheckersTile destMid2 = board.getCheckersTile(3, 2);
        CheckersTile jumped3 = board.getCheckersTile(2, 3);
        CheckersTile destFinal = board.getCheckersTile(1, 4);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersPiece jumpedPiece3 = new CheckersPiece(PlayerType.RED, jumped3);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid1, DiagonalDirection.FORWARD_LEFT);
        CheckersMoveJumpSingle jump3 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid2, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti subMove = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);
        CheckersMoveJumpMulti move = subMove.append(jump3);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 4);
        CheckersTile destFinal = board.getCheckersTile(2, 3);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_LEFT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(7, 2);
        CheckersTile jumped1 = board.getCheckersTile(6, 3);
        CheckersTile destMid1 = board.getCheckersTile(5, 4);
        CheckersTile jumped2 = board.getCheckersTile(4, 3);
        CheckersTile destMid2 = board.getCheckersTile(3, 2);
        CheckersTile jumped3 = board.getCheckersTile(2, 3);
        CheckersTile destFinal = board.getCheckersTile(1, 4);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersPiece jumpedPiece3 = new CheckersPiece(PlayerType.RED, jumped3);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid1, DiagonalDirection.FORWARD_LEFT);
        CheckersMoveJumpSingle jump3 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid2, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti subMove = CheckersMoveJumpMulti.CreateAsJoin(jump2, jump3);
        CheckersMoveJumpMulti move = subMove.prepend(jump1);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(6, 3);
        CheckersTile jumped1 = board.getCheckersTile(5, 4);
        CheckersTile destMid = board.getCheckersTile(4, 5);
        CheckersTile jumped2 = board.getCheckersTile(3, 6);
        CheckersTile destFinal = board.getCheckersTile(2, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 3);
        CheckersTile jumped1 = board.getCheckersTile(3, 4);
        CheckersTile destMid = board.getCheckersTile(2, 5);
        CheckersTile jumped2 = board.getCheckersTile(1, 6);
        CheckersTile destFinal = board.getCheckersTile(0, 7);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.RED, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.RED, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.FORWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(3, 2);
        CheckersTile jumped1 = board.getCheckersTile(4, 3);
        CheckersTile destMid = board.getCheckersTile(5, 4);
        CheckersTile jumped2 = board.getCheckersTile(6, 5);
        CheckersTile destFinal = board.getCheckersTile(7, 6);
        CheckersPiece jumperPiece = new CheckersPiece(PlayerType.RED, start);
        CheckersPiece jumpedPiece1 = new CheckersPiece(PlayerType.BLACK, jumped1);
        CheckersPiece jumpedPiece2 = new CheckersPiece(PlayerType.BLACK, jumped2);
        CheckersMoveJumpSingle jump1 = CheckersMoveJumpSingle.Create(
                jumperPiece, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpSingle jump2 = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(
                jumperPiece, destMid, DiagonalDirection.BACKWARD_RIGHT);
        CheckersMoveJumpMulti move = CheckersMoveJumpMulti.CreateAsJoin(jump1, jump2);

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

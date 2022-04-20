package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMoveNormal;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersMoveNormal class
 *
 * Tests methods:
 *  - Create
 *  - isValid
 *  - execute
 * Also tests kinging behavior
 *
 * @author Nathan Swartz
 */
public class CheckersMoveNormalTests {

    public CheckersMoveNormalTests() {}

    // --------------------------------
    // CREATE
    // --------------------------------

    @Test
    public void testCreate_allNull_null() {
        CheckersMoveNormal move = CheckersMoveNormal.Create(null, null);
        assertNull(move);
    }

    @Test
    public void testCreate_pieceNull_null() {
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                null, DiagonalDirection.FORWARD_RIGHT);
        assertNull(move);
    }

    @Test
    public void testCreate_dirNull_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveNormal move = CheckersMoveNormal.Create(piece, null);
        assertNull(move);
    }

    @Test
    public void testCreate_piece01dirForwardLeft_null() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(1, 0));
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_LEFT);
        assertNull(move);
    }

    @Test
    public void testConstructor_piece45dirBackwardRight_succeeds() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_RIGHT);
        assertNotNull(move);
    }

    // --------------------------------
    // IS VALID
    // --------------------------------

    // NORMAL PIECE

    // forward/backwards directions

    @Test
    public void testIsValid_normalPieceBlack45dirForwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
    }

    @Test
    public void testIsValid_normalPieceBlack45dirBackwardRight_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalPieceRed45dirForwardRight_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_normalPieceRed45dirBackwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_RIGHT);

        assertTrue(move.isValid());
    }

    // KING PIECE

    @Test
    public void testIsValid_kingPieceBlack45dirForwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(0, 1));
        piece.moveTo(board.getTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);

        assertTrue(move.isValid());
    }

    @Test
    public void testIsValid_kingPieceBlack45dirBackwardLeft_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(0, 1));
        piece.moveTo(board.getTile(4, 5));

        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_LEFT);

        assertTrue(move.isValid());
    }

    // --------------------------------
    // EXECUTE
    // --------------------------------

    @Test
    public void testExecute_normalBlack45ForwardLeft() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(4, 5);
        CheckersTile dest = board.getCheckersTile(3, 4);
        CheckersPiece piece = new CheckersPiece(PlayerType.BLACK, start);
        CheckersMoveNormal move = CheckersMoveNormal.Create(piece, DiagonalDirection.FORWARD_LEFT);

        assertEquals(piece, start.getCheckersPiece());
        assertTrue(dest.isBlank());

        assertTrue(move.isValid());
        move.execute();

        assertTrue(start.isBlank());
        assertEquals(piece, dest.getCheckersPiece());
    }

    @Test
    public void testExecute_normalRed56BackwardLeft() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(5, 6);
        CheckersTile dest = board.getCheckersTile(6, 5);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, start);
        CheckersMoveNormal move = CheckersMoveNormal.Create(piece, DiagonalDirection.BACKWARD_LEFT);

        assertEquals(piece, start.getCheckersPiece());
        assertTrue(dest.isBlank());

        assertTrue(move.isValid());
        move.execute();

        assertTrue(start.isBlank());
        assertEquals(piece, dest.getCheckersPiece());
    }


    @Test
    public void testExecute_normalRed56ForwardLeft_exception() {
        CheckersBoard board = new CheckersBoard();
        CheckersTile start = board.getCheckersTile(5, 6);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, start);
        CheckersMoveNormal move = CheckersMoveNormal.Create(piece, DiagonalDirection.FORWARD_LEFT);

        assertFalse(move.isValid());
        try {
            move.execute();
            fail();
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

    // --------------------------------
    // KINGING BEHAVIOR
    // --------------------------------

    @Test
    public void testKinging_normalBlack10_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(1, 0));

        assertFalse(piece.isKing());
    }

    @Test
    public void testKinging_normalBlack63ForwardRight_false() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(6, 3));
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);

        move.execute();

        assertFalse(piece.isKing());
    }

    @Test
    public void testKinging_normalBlack10ForwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(1, 0));
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);

        move.execute();

        assertTrue(piece.isKing());
    }

    @Test
    public void testKinging_normalBlack10ForwardRightBackwardLeft_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.BLACK, board.getCheckersTile(1, 0));
        CheckersMoveNormal move1 = CheckersMoveNormal.Create(
                piece, DiagonalDirection.FORWARD_RIGHT);
        move1.execute();
        CheckersMoveNormal move2 = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_LEFT);
        move2.execute();

        assertTrue(piece.isKing());
    }

    @Test
    public void testKinging_normalRed61BackwardRight_true() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, board.getCheckersTile(6, 1));
        CheckersMoveNormal move = CheckersMoveNormal.Create(
                piece, DiagonalDirection.BACKWARD_RIGHT);

        move.execute();

        assertTrue(piece.isKing());
    }

}

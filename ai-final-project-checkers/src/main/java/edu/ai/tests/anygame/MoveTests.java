package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Move class
 * <p>
 * Tests methods:
 * - Constructor
 * - isValid
 * - execute
 * - unexecute
 * - getStartingTile
 *
 * @author Nathan Swartz
 */
public class MoveTests {

    public MoveTests() {
    }

    // --------------------------------
    // CONSTRUCTOR
    // --------------------------------

    @Test
    public void testConstructor_allNull_illegalArgumentException() {
        try {
            Move move = new Move(null, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testConstructor_pieceNull_illegalArgumentException() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);

        try {
            Move move = new Move(null, dest);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testConstructor_dirNull_illegalArgumentException() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);

        try {
            Move move = new Move(piece, null);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testConstructor_normal_succeeds() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);

        try {
            Move move = new Move(piece, dest);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    // --------------------------------
    // IS VALID
    // --------------------------------

    @Test
    public void testIsValid_destinationIsStart_false() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Piece piece = new Piece(start);

        Move move = new Move(piece, start);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_destinationNotBlank_false() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);
        Move move = new Move(piece, dest);
        new Piece(dest);
        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertFalse(dest.isBlank());

        assertFalse(move.isValid());
    }

    @Test
    public void testIsValid_destinationBlank_true() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);
        Move move = new Move(piece, dest);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertTrue(dest.isBlank());

        assertTrue(move.isValid());
    }

    // --------------------------------
    // EXECUTE
    // --------------------------------

    @Test
    public void testExecute_destinationNotBlank_exception() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);
        Move move = new Move(piece, dest);
        new Piece(dest);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertFalse(dest.isBlank());
        assertFalse(move.isValid());

        try {
            move.execute();
            fail();
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testExecute_destinationBlank_succeeds() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Tile dest = board.getTile(0, 2);
        Piece piece = new Piece(start);
        Move move = new Move(piece, dest);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertTrue(dest.isBlank());
        assertTrue(move.isValid());

        try {
            move.execute();
            assertTrue(true);
        } catch (IllegalStateException e) {
            fail();
        }
    }

    @Test
    public void testExecute_destinationBlank_movesPiece() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Piece piece = new Piece(start);
        Tile dest = board.getTile(0, 2);
        Move move = new Move(piece, dest);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertTrue(dest.isBlank());
        assertTrue(move.isValid());

        move.execute();

        assertTrue(start.isBlank());
        assertFalse(dest.isBlank());
        assertEquals(dest.getPiece(), piece);
        assertEquals(piece.getTile(), dest);
    }

    // --------------------------------
    // UNEXECUTE
    // --------------------------------

    @Test
    public void testUnexecute_normal_movesPiece() {
        GridBoard board = new GridBoard(4, 4);
        Tile start = board.getTile(0, 0);
        Piece piece = new Piece(start);
        Tile dest = board.getTile(0, 2);
        Move move = new Move(piece, dest);

        // for my sanity
        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertTrue(dest.isBlank());
        assertTrue(move.isValid());

        move.execute();

        // for my sanity
        assertTrue(start.isBlank());
        assertFalse(dest.isBlank());
        assertEquals(dest.getPiece(), piece);
        assertEquals(piece.getTile(), dest);

        move.unexecute();

        assertFalse(start.isBlank());
        assertEquals(start.getPiece(), piece);
        assertEquals(piece.getTile(), start);
        assertTrue(dest.isBlank());
    }

}
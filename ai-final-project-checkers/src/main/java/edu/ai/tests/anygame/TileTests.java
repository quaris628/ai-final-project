package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Tile class
 * <p>
 * Tests methods:
 * - isBlank
 * Tests indirectly / assumes functional:
 * - Constructor
 * - setPiece
 * - removePiece
 * - getPiece
 *
 * @author Nathan Swartz
 */
public class TileTests {

    private static final GridBoard blankBoard = new GridBoard(8, 8);

    public TileTests() {
    }

    // --------------------------------
    // IS BLANK
    // --------------------------------

    @Test
    public void testIsBlank_NewTile_True() {
        Tile tile = new Tile(blankBoard, 9, 9);

        assertTrue(tile.isBlank());
    }

    @Test
    public void testIsBlank_CreatePiece_False() {
        Tile tile = new Tile(blankBoard, 9, 9);

        new Piece(tile);

        assertFalse(tile.isBlank());
    }

    @Test
    public void testIsBlank_CreatePieceRemovePiece_True() {
        Tile tile = new Tile(blankBoard, 9, 9);

        new Piece(tile);
        tile.removePiece();

        assertTrue(tile.isBlank());
    }

    @Test
    public void test_CreatePieceCreatePiece_illegalStateException() {
        Tile tile = new Tile(blankBoard, 9, 9);

        new Piece(tile);

        try {
            new Piece(tile);
            fail();
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

}
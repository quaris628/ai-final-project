package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for Tile class
 *
 * Tests methods:
 *  - isBlank
 * Tests indirectly / assumes functional:
 *  - Constructor
 *  - setPiece
 *  - removePiece
 *  - getPiece
 *
 * @author Nathan Swartz
 */
public class TileTests {

    private static final GridBoard initialBoard = new GridBoard(8, 8);

    public TileTests() {}

    // --------------------------------
    // IS BLANK
    // --------------------------------

    @Test
    public void testIsBlank_NewTile_True() {
        Tile tile = new Tile(initialBoard, 9, 9);

        assertTrue(tile.isBlank());
    }

    @Test
    public void testIsBlank_AddPiece_False() {
        Tile tile = new Tile(initialBoard, 9, 9);

        tile.setPiece(new Piece(tile));

        assertFalse(tile.isBlank());
    }

    @Test
    public void testIsBlank_AddPieceRemovePiece_True() {
        Tile tile = new Tile(initialBoard, 9, 9);

        tile.setPiece(new Piece(tile));
        tile.removePiece();

        assertTrue(tile.isBlank());
    }

}
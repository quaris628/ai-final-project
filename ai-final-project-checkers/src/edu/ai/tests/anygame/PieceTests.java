package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for Piece class
 *
 * Tests methods:
 *  - getTile
 *  - remove
 *  - moveTo
 * Tests indirectly / assumes functional:
 *  - Constructor
 *
 *
 * @author Nathan Swartz
 */
public class PieceTests {

    private final static GridBoard blankBoard = new GridBoard(8, 8);

    public PieceTests() {}

    // --------------------------------
    // GET TILE
    // --------------------------------

    @Test
    public void testGetTile() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(tile01);

        assertEquals(tile01, piece.getTile());
    }

    // --------------------------------
    // GET PIECE
    // --------------------------------

    @Test
    public void testGetPiece() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(tile01);

        assertEquals(piece, tile01.getPiece());
    }


    // --------------------------------
    // REMOVE
    // --------------------------------

    @Test
    public void testRemove() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(tile01);

        piece.remove();

        assertNull(piece.getTile());
        assertTrue(tile01.isBlank());
    }

    // --------------------------------
    // MOVE TO
    // --------------------------------

    @Test
    public void testMoveTo() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Tile tile10 = blankBoard.getTile(1, 0);
        Piece piece = new Piece(tile01);

        piece.moveTo(tile10);

        assertEquals(tile10, piece.getTile());
        assertTrue(tile01.isBlank());
        assertEquals(piece, tile10.getPiece());
    }

    @Test
    public void testMoveTo_DestinationNotBlank_Exception() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Tile tile10 = blankBoard.getTile(1, 0);
        Piece piece01 = new Piece(tile01);
        Piece piece10 = new Piece(tile10);

        boolean exceptionTriggered = false;
        try {
            piece01.moveTo(tile10);
        } catch (IllegalArgumentException e) {
            exceptionTriggered = true;
        }

        assertTrue(exceptionTriggered);
    }


}

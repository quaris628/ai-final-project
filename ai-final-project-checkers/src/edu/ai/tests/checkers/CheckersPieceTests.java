package edu.ai.tests.checkers;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.PlayerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Piece class
 *
 * Tests methods:
 *  - Constructor & getTile
 *  - moveTo
 *  - remove
 *  - isKing
 *  Tests indirectly / assumes functional:
 *   - king
 *  Does not test:
 *   - getPlayer
 *
 *
 * @author Nathan Swartz
 */
public class CheckersPieceTests {

    private final static GridBoard blankBoard =
            GridBoard.CreateBlankBoard();

    public CheckersPieceTests() {}

    // --------------------------------
    // CONSTRUCTOR / GET TILE
    // --------------------------------

    @Test
    public void testGetTile() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(PlayerType.RED, tile01);

        assertEquals(tile01, piece.getTile());
        assertEquals(piece, tile01.getPiece());
    }

    // --------------------------------
    // IS KING
    // --------------------------------

    @Test
    public void testIsKing_01Red_False() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(PlayerType.RED, tile01);

        assertFalse(piece.isKing());
    }

    @Test
    public void testIsKing_01Black_True() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(PlayerType.BLACK, tile01);

        assertTrue(piece.isKing());
    }

    @Test
    public void testIsKing_70Red_True() {
        Tile tile70 = blankBoard.getTile(7, 0);
        Piece piece = new Piece(PlayerType.RED, tile70);

        assertTrue(piece.isKing());
    }

    @Test
    public void testIsKing_70Black_False() {
        Tile tile70 = blankBoard.getTile(7, 0);
        Piece piece = new Piece(PlayerType.BLACK, tile70);

        assertFalse(piece.isKing());
    }

    @Test
    public void testIsKing_70Red_Moved61_True() {
        Tile tile70 = blankBoard.getTile(7, 0);
        Tile tile61 = blankBoard.getTile(6, 1);
        Piece piece = new Piece(PlayerType.RED, tile70);

        piece.moveTo(tile61);

        assertTrue(piece.isKing());
    }


    // --------------------------------
    // REMOVE
    // --------------------------------

    @Test
    public void testRemove() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Piece piece = new Piece(PlayerType.RED, tile01);

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
        Piece piece = new Piece(PlayerType.RED, tile01);

        piece.moveTo(tile10);

        assertEquals(tile10, piece.getTile());
        assertTrue(tile01.isBlank());
        assertEquals(piece, tile10.getPiece());
    }

    @Test
    public void testMoveTo_DestinationNotBlank_Exception() {
        Tile tile01 = blankBoard.getTile(0, 1);
        Tile tile10 = blankBoard.getTile(1, 0);
        Piece piece01 = new Piece(PlayerType.RED, tile01);
        Piece piece10 = new Piece(PlayerType.RED, tile10);

        boolean exceptionTriggered = false;
        try {
            piece01.moveTo(tile10);
        } catch (IllegalArgumentException e) {
            exceptionTriggered = true;
        }

        assertTrue(exceptionTriggered);
    }


}

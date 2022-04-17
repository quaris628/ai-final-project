package edu.ai.tests.checkers;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;
import edu.ai.mainproj.checkers.PlayerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for Piece class
 *
 * Tests methods:
 *  - Constructor / getCheckersTile
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

    private final static CheckersBoard initialBoard =
            CheckersBoard.CreateInitialBoard();

    public CheckersPieceTests() {}

    // --------------------------------
    // GET CHECKERS TILE
    // --------------------------------

    @Test
    public void testGetCheckersTile() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersPiece piece = new CheckersPiece(
                PlayerType.RED, tile01);

        assertEquals(tile01, piece.getCheckersTile());
        assertEquals(piece, tile01.getCheckersPiece());
    }

    // --------------------------------
    // IS KING
    // --------------------------------

    @Test
    public void testIsKing_01Red_False() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, tile01);

        assertFalse(piece.isKing());
    }

    @Test
    public void testIsKing_01Black_True() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersPiece piece = new CheckersPiece(PlayerType.BLACK, tile01);

        assertTrue(piece.isKing());
    }

    @Test
    public void testIsKing_70Red_True() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, tile70);

        assertTrue(piece.isKing());
    }

    @Test
    public void testIsKing_70Black_False() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        CheckersPiece piece = new CheckersPiece(PlayerType.BLACK, tile70);

        assertFalse(piece.isKing());
    }

    @Test
    public void testIsKing_70Red_Moved61_True() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        CheckersTile tile61 = initialBoard.getCheckersTile(6, 1);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, tile70);

        piece.moveTo(tile61);

        assertTrue(piece.isKing());
    }


    // --------------------------------
    // REMOVE
    // --------------------------------

    @Test
    public void testRemove() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, tile01);

        piece.remove();

        assertNull(piece.getTile());
        assertTrue(tile01.isBlank());
    }

    // --------------------------------
    // MOVE TO
    // --------------------------------

    @Test
    public void testMoveTo() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersTile tile10 = initialBoard.getCheckersTile(1, 0);
        CheckersPiece piece = new CheckersPiece(PlayerType.RED, tile01);

        piece.moveTo(tile10);

        assertEquals(tile10, piece.getTile());
        assertTrue(tile01.isBlank());
        assertEquals(piece, tile10.getPiece());
    }

    @Test
    public void testMoveTo_DestinationNotBlank_Exception() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        CheckersTile tile10 = initialBoard.getCheckersTile(1, 0);
        CheckersPiece piece01 = new CheckersPiece(PlayerType.RED, tile01);
        CheckersPiece piece10 = new CheckersPiece(PlayerType.RED, tile10);

        boolean exceptionTriggered = false;
        try {
            piece01.moveTo(tile10);
        } catch (IllegalArgumentException e) {
            exceptionTriggered = true;
        }

        assertTrue(exceptionTriggered);
    }


}

package edu.ai.tests.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersTile class
 *
 * Tests methods:
 * - getNeighbors
 * - doesKing
 * - setPiece (kinging behavior)
 * Tests indirectly / assumes functional:
 * - Constructor
 * - getPiece
 * - removePiece
 *
 * @author Nathan Swartz
 */
public class CheckersTileTests {

    private static final CheckersBoard initialBoard =
            CheckersBoard.CreateInitialBoard();

    public CheckersTileTests() {
    }

    // --------------------------------
    // GET NEIGHBOR AT
    // --------------------------------
    @Test
    public void testGetNeighborAt_23ForwardLeft_12() {
        CheckersTile tile23 = initialBoard.getCheckersTile(2, 3);
        CheckersTile tile12 = initialBoard.getCheckersTile(1, 2);

        CheckersTile tile = tile23.getNeighborAt(DiagonalDirection.FORWARD_LEFT);

        assertEquals(tile12, tile);
    }

    @Test
    public void testGetNeighborAt_70ForwardRight_61() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        CheckersTile tile61 = initialBoard.getCheckersTile(6, 1);

        CheckersTile tile = tile70.getNeighborAt(DiagonalDirection.FORWARD_RIGHT);

        assertEquals(tile61, tile);
    }

    @Test
    public void testGetNeighborAt_34BackwardRight_43() {
        CheckersTile tile34 = initialBoard.getCheckersTile(3, 4);
        CheckersTile tile45 = initialBoard.getCheckersTile(4, 5);

        CheckersTile tile = tile34.getNeighborAt(DiagonalDirection.BACKWARD_RIGHT);

        assertEquals(tile45, tile);
    }

    @Test
    public void testGetNeighborAt_36BackwardLeft_45() {
        CheckersTile tile36 = initialBoard.getCheckersTile(3, 6);
        CheckersTile tile45 = initialBoard.getCheckersTile(4, 5);

        CheckersTile tile = tile36.getNeighborAt(DiagonalDirection.BACKWARD_LEFT);

        assertEquals(tile45, tile);
    }

    @Test
    public void testGetNeighborAt_27ForwardRight_Null() {
        CheckersTile tile27 = initialBoard.getCheckersTile(2, 7);

        CheckersTile tile = tile27.getNeighborAt(DiagonalDirection.FORWARD_RIGHT);

        assertNull(tile);
    }

    // --------------------------------
    // GET NEIGHBORS
    // --------------------------------

    @Test
    public void testGetNeighbors_36_gets_25_45_27_47() {
        CheckersTile tile36 = initialBoard.getCheckersTile(3, 6);

        HashSet<CheckersTile> tiles = new HashSet<CheckersTile>();
        for (CheckersTile neighbor : tile36.getNeighbors()) {
            tiles.add(neighbor);
        }

        assertTrue(tiles.contains(initialBoard.getCheckersTile(2, 5)));
        assertTrue(tiles.contains(initialBoard.getCheckersTile(4, 5)));
        assertTrue(tiles.contains(initialBoard.getCheckersTile(2, 7)));
        assertTrue(tiles.contains(initialBoard.getCheckersTile(4, 7)));
        assertEquals(tiles.size(), 4);
    }

    // --------------------------------
    // DOES KING
    // --------------------------------

    @Test
    public void testDoesKing() {
        // top row should king only black
        for (Tile tile : initialBoard.getTilesInRow(0)) {
            assertTrue(((CheckersTile) tile).doesKing(PlayerType.BLACK));
            assertFalse(((CheckersTile) tile).doesKing(PlayerType.RED));
        }
        // middle rows shouldn't king anyone
        for (int i = 1; i < CheckersBoard.SIZE - 1; i++) {
            for (Tile tile : initialBoard.getTilesInRow(i)) {
                assertFalse(((CheckersTile) tile).doesKing(PlayerType.BLACK));
                assertFalse(((CheckersTile) tile).doesKing(PlayerType.RED));
            }
        }
        // bottom row should king only red
        for (Tile tile : initialBoard.getTilesInRow(CheckersBoard.SIZE - 1)) {
            assertFalse(((CheckersTile) tile).doesKing(PlayerType.BLACK));
            assertTrue(((CheckersTile) tile).doesKing(PlayerType.RED));
        }
    }

    /*
    // --------------------------------
    // SET PIECE
    // --------------------------------

    @Test
    public void testSetPiece_01Black_Kings() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        tile01.setPiece(new CheckersPiece(PlayerType.BLACK, tile01));

        assertFalse(tile01.isBlank());
        assertTrue(tile01.getCheckersPiece().isKing());
    }

    @Test
    public void testSetPiece_01Red_NotKings() {
        CheckersTile tile01 = initialBoard.getCheckersTile(0, 1);
        tile01.setPiece(new CheckersPiece(PlayerType.RED, tile01));

        assertFalse(tile01.isBlank());
        assertFalse(tile01.getCheckersPiece().isKing());
    }

    @Test
    public void testSetPiece_70Black_NotKings() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        tile70.setPiece(new CheckersPiece(PlayerType.BLACK, tile70));

        assertFalse(tile70.isBlank());
        assertFalse(tile70.getCheckersPiece().isKing());
    }

    @Test
    public void testSetPiece_70Red_Kings() {
        CheckersTile tile70 = initialBoard.getCheckersTile(7, 0);
        tile70.setPiece(new CheckersPiece(PlayerType.RED, tile70));

        assertFalse(tile70.isBlank());
        assertTrue(tile70.getCheckersPiece().isKing());
    }

    @Test
    public void testSetPiece_54Black_NotKings() {
        CheckersTile tile54 = initialBoard.getCheckersTile(5, 4);
        tile54.setPiece(new CheckersPiece(PlayerType.BLACK, tile54));

        assertFalse(tile54.isBlank());
        assertFalse(tile54.getCheckersPiece().isKing());
    }

    @Test
    public void testSetPiece_54Red_NotKings() {
        CheckersTile tile54 = initialBoard.getCheckersTile(5, 4);
        tile54.setPiece(new CheckersPiece(PlayerType.RED, tile54));

        assertFalse(tile54.isBlank());
        assertFalse(tile54.getCheckersPiece().isKing());
    }
     */

}
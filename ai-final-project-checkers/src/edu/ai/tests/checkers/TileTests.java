package edu.ai.tests.checkers;

import edu.ai.mainproj.checkers.*;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for Tile class
 *
 * Tests methods:
 *  - isBlank
 *  - doesKing
 *  - getNeighbors
 *  - setPiece
 * Tests indirectly / assumes functional:
 *  - Constructor
 *  - getPiece
 *  - removePiece
 *
 * @author Nathan Swartz
 */
public class TileTests {

    private static final Board initialBoard = Board.CreateCheckersInitialBoard();

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

        tile.setPiece(new Piece(PlayerType.BLACK,tile));

        assertFalse(tile.isBlank());
    }

    @Test
    public void testIsBlank_AddPieceRemovePiece_True() {
        Tile tile = new Tile(initialBoard, 9, 9);

        tile.setPiece(new Piece(PlayerType.BLACK,tile));
        tile.removePiece();

        assertTrue(tile.isBlank());
    }

    // --------------------------------
    // DOES KING
    // --------------------------------

    @Test
    public void testDoesKing() {
        // top row should king only black
        for (Tile tile : initialBoard.getTilesInRow(0)) {
            assertTrue(tile.doesKing(PlayerType.BLACK));
            assertFalse(tile.doesKing(PlayerType.RED));
        }
        // middle rows shouldn't king anyone
        for (int i = 1; i < initialBoard.getSize() - 1; i++) {
            for (Tile tile : initialBoard.getTilesInRow(i)) {
                assertFalse(tile.doesKing(PlayerType.BLACK));
                assertFalse(tile.doesKing(PlayerType.RED));
            }
        }
        // bottom row should king only red
        for (Tile tile : initialBoard.getTilesInRow(initialBoard.getSize() - 1)) {
            assertFalse(tile.doesKing(PlayerType.BLACK));
            assertTrue(tile.doesKing(PlayerType.RED));
        }
    }

    // --------------------------------
    // GET NEIGHBOR AT
    // --------------------------------
    @Test
    public void testGetNeighborAt_23ForwardLeft_12() {
        Tile tile23 = initialBoard.getTile(2, 3);
        Tile tile12 = initialBoard.getTile(1, 2);

        Tile tile = tile23.getNeighborAt(DiagonalDirection.FORWARD_LEFT);

        assertEquals(tile12, tile);
    }

    @Test
    public void testGetNeighborAt_70ForwardRight_61() {
        Tile tile70 = initialBoard.getTile(7, 0);
        Tile tile61 = initialBoard.getTile(6, 1);

        Tile tile = tile70.getNeighborAt(DiagonalDirection.FORWARD_RIGHT);

        assertEquals(tile61, tile);
    }

    @Test
    public void testGetNeighborAt_34BackwardRight_43() {
        Tile tile34 = initialBoard.getTile(3, 4);
        Tile tile45 = initialBoard.getTile(4, 5);

        Tile tile = tile34.getNeighborAt(DiagonalDirection.BACKWARD_RIGHT);

        assertEquals(tile45, tile);
    }

    @Test
    public void testGetNeighborAt_36BackwardLeft_45() {
        Tile tile36 = initialBoard.getTile(3, 6);
        Tile tile45 = initialBoard.getTile(4, 5);

        Tile tile = tile36.getNeighborAt(DiagonalDirection.BACKWARD_LEFT);

        assertEquals(tile45, tile);
    }

    @Test
    public void testGetNeighborAt_27ForwardRight_Null() {
        Tile tile27 = initialBoard.getTile(2, 7);

        Tile tile = tile27.getNeighborAt(DiagonalDirection.FORWARD_RIGHT);

        assertNull(tile);
    }

    // --------------------------------
    // GET NEIGHBORS
    // --------------------------------

    @Test
    public void testGetNeighbors_36_gets_25_45_27_47() {
        Tile tile36 = initialBoard.getTile(3, 6);

        HashSet<Tile> tiles = new HashSet<Tile>();
        for (Tile neighbor : tile36.getNeighbors()) {
            tiles.add(neighbor);
        }

        assertTrue(tiles.contains(initialBoard.getTile(2, 5)));
        assertTrue(tiles.contains(initialBoard.getTile(4, 5)));
        assertTrue(tiles.contains(initialBoard.getTile(2, 7)));
        assertTrue(tiles.contains(initialBoard.getTile(4, 7)));
        assertEquals(tiles.size(), 4);
    }

    // --------------------------------
    // SET PIECE
    // --------------------------------

    @Test
    public void testSetPiece_01Black_Kings() {
        Tile tile01 = initialBoard.getTile(0, 1);
        tile01.setPiece(new Piece(PlayerType.BLACK, tile01));

        assertFalse(tile01.isBlank());
        assertTrue(tile01.getPiece().isKing());
    }

    @Test
    public void testSetPiece_01Red_NotKings() {
        Tile tile01 = initialBoard.getTile(0, 1);
        tile01.setPiece(new Piece(PlayerType.RED, tile01));

        assertFalse(tile01.isBlank());
        assertFalse(tile01.getPiece().isKing());
    }

    @Test
    public void testSetPiece_70Black_NotKings() {
        Tile tile70 = initialBoard.getTile(7, 0);
        tile70.setPiece(new Piece(PlayerType.BLACK, tile70));

        assertFalse(tile70.isBlank());
        assertFalse(tile70.getPiece().isKing());
    }

    @Test
    public void testSetPiece_70Red_Kings() {
        Tile tile70 = initialBoard.getTile(7, 0);
        tile70.setPiece(new Piece(PlayerType.RED, tile70));

        assertFalse(tile70.isBlank());
        assertTrue(tile70.getPiece().isKing());
    }

    @Test
    public void testSetPiece_54Black_NotKings() {
        Tile tile54 = initialBoard.getTile(5, 4);
        tile54.setPiece(new Piece(PlayerType.BLACK, tile54));

        assertFalse(tile54.isBlank());
        assertFalse(tile54.getPiece().isKing());
    }

    @Test
    public void testSetPiece_54Red_NotKings() {
        Tile tile54 = initialBoard.getTile(5, 4);
        tile54.setPiece(new Piece(PlayerType.RED, tile54));

        assertFalse(tile54.isBlank());
        assertFalse(tile54.getPiece().isKing());
    }

}
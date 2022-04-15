package edu.ai.tests.checkers;

import edu.ai.mainproj.checkers.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TileTests {

    Board initialBoard;

    public TileTests() {}

    @Before
    public void setUp() {
        initialBoard = Board.CreateCheckersInitialBoard();
    }

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

    @After
    public void tearDown() {
        // if needed
    }
}
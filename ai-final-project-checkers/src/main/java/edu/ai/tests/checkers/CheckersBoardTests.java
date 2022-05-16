package edu.ai.tests.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.PlayerType;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersBoard class
 * <p>
 * TODO update this list
 * Tests instantiation methods (constructor is private):
 *  - CreateCheckersInitialBoard
 *  - CreateBlankBoard
 * Tests methods:
 *  - getTilesInRow
 *  - getTilesInColumn
 *  - getAllTiles
 * Tests indirectly / assumes functional:
 *  - getTile
 *  - getSize
 * Does not test:
 *  - toString()
 *  - toString(rowDelimiter)
 *
 * @author Nathan Swartz
 */
public class CheckersBoardTests {

    public CheckersBoardTests() {
    }

    //--------------------------------
    // INITIAL BOARD
    // --------------------------------

    @Test
    public void testInitialBoard_NullTilePositions() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        // tiles that should be null are null
        for (int i = 0; i < CheckersBoard.SIZE; i += 2) {
            for (int j = 0; j < CheckersBoard.SIZE; j += 2) {
                assertNull(initialBoard.getTile(i, j));
            }
        }
        for (int i = 1; i < CheckersBoard.SIZE; i += 2) {
            for (int j = 1; j < CheckersBoard.SIZE; j += 2) {
                assertNull(initialBoard.getTile(i, j));
            }
        }
        // tiles that should not be null are not null
        for (int i = 1; i < CheckersBoard.SIZE; i += 2) {
            for (int j = 0; j < CheckersBoard.SIZE; j += 2) {
                assertNotNull(initialBoard.getTile(i, j));
            }
        }
        for (int i = 0; i < CheckersBoard.SIZE; i += 2) {
            for (int j = 1; j < CheckersBoard.SIZE; j += 2) {
                assertNotNull(initialBoard.getTile(i, j));
            }
        }
    }

    @Test
    public void testInitialBoard_BlankTilePositions() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        for (int j = 0; j < CheckersBoard.SIZE; j += 2) {
            // first 3 rows are not blank
            assertFalse(initialBoard.getTile(0, j + 1).isBlank());
            assertFalse(initialBoard.getTile(1, j).isBlank());
            assertFalse(initialBoard.getTile(2, j + 1).isBlank());

            // middle 2 rows are blank
            assertTrue(initialBoard.getTile(3, j).isBlank());
            assertTrue(initialBoard.getTile(4, j + 1).isBlank());

            // last 3 rows are not blank
            assertFalse(initialBoard.getTile(5, j).isBlank());
            assertFalse(initialBoard.getTile(6, j + 1).isBlank());
            assertFalse(initialBoard.getTile(7, j).isBlank());
        }
    }

    @Test
    public void testInitialBoard_RedPieces() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        for (int j = 0; j < CheckersBoard.SIZE; j += 2) {
            CheckersPiece piece0 = initialBoard.getCheckersTile(0, j + 1).getCheckersPiece();
            assertEquals(PlayerType.RED, piece0.getPlayer());
            CheckersPiece piece1 = initialBoard.getCheckersTile(1, j).getCheckersPiece();
            assertEquals(PlayerType.RED, piece1.getPlayer());
            CheckersPiece piece2 = initialBoard.getCheckersTile(2, j + 1).getCheckersPiece();
            assertEquals(PlayerType.RED, piece2.getPlayer());
        }
    }


    @Test
    public void testInitialBoard_BlackPieces() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        for (int j = 0; j < CheckersBoard.SIZE; j += 2) {
            CheckersPiece piece0 = initialBoard.getCheckersTile(5, j).getCheckersPiece();
            assertEquals(PlayerType.BLACK, piece0.getPlayer());
            CheckersPiece piece1 = initialBoard.getCheckersTile(6, j + 1).getCheckersPiece();
            assertEquals(PlayerType.BLACK, piece1.getPlayer());
            CheckersPiece piece2 = initialBoard.getCheckersTile(7, j).getCheckersPiece();
            assertEquals(PlayerType.BLACK, piece2.getPlayer());
        }
    }

    // --------------------------------
    // GETTING ROWS
    // --------------------------------

    @Test
    public void testGetTilesInRow1() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        int row = 1;

        Iterable<Tile> tilesInRow = initialBoard.getTilesInRow(row);

        Iterator<Tile> iter = tilesInRow.iterator();
        assertEquals(initialBoard.getTile(row, 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 2 + 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 4 + 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 6 + 1 - row % 2), iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testGetTilesInRow5() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        int row = 5;

        Iterable<Tile> tilesInRow = initialBoard.getTilesInRow(row);

        Iterator<Tile> iter = tilesInRow.iterator();
        assertEquals(initialBoard.getTile(row, 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 2 + 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 4 + 1 - row % 2), iter.next());
        assertEquals(initialBoard.getTile(row, 6 + 1 - row % 2), iter.next());
        assertFalse(iter.hasNext());
    }

    // --------------------------------
    // GETTING COLUMNS
    // --------------------------------

    @Test
    public void testGetTilesInColumns() {
        for (int i = 0; i < CheckersBoard.SIZE; i++) {
            testGetTilesInColumn(i);
        }
    }

    public void testGetTilesInColumn(int column) {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        Iterable<Tile> tilesInColumn = initialBoard.getTilesInColumn(column);

        Iterator<Tile> iter = tilesInColumn.iterator();
        for (int i = 1 - column % 2; i < CheckersBoard.SIZE; i += 2) {
            assertEquals(initialBoard.getTile(i, column), iter.next());
        }
        assertFalse(iter.hasNext());
    }

    //--------------------------------
    // GETTING ALL TILES
    // --------------------------------

    @Test
    public void testGetAllTiles() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        Iterable<Tile> allTiles = initialBoard.getAllTiles();
        int blankCount = 0;
        int redCount = 0;
        int blackCount = 0;
        for (Tile tile : allTiles) {
            if (tile.isBlank()) {
                blankCount++;
            } else if (((CheckersPiece) tile.getPiece()).getPlayer() == PlayerType.RED) {
                redCount++;
            } else if (((CheckersPiece) tile.getPiece()).getPlayer() == PlayerType.BLACK) {
                blackCount++;
            } else {
                fail();
            }
        }
        assertEquals(8, blankCount);
        assertEquals(12, blackCount);
        assertEquals(12, redCount);
    }

    @Test
    public void hashCode_dependentOnPieces() {
        CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();
        CheckersBoard blankBoard = new CheckersBoard();

        assertNotEquals(initialBoard.hashCode(), blankBoard.hashCode());

    }
}
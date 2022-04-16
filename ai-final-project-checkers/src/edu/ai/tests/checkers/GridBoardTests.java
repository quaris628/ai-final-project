package edu.ai.tests.checkers;


import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for Board class
 *
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
public class GridBoardTests {

    private static final GridBoard initialBoard = GridBoard.CreateCheckersInitialBoard();
    private static final GridBoard blankBoard = GridBoard.CreateBlankBoard(6);

    public GridBoardTests() {}

    //--------------------------------
    // INITIAL BOARD
    // --------------------------------

    @Test
    public void testInitialBoard_NullTilePositions() {
        // tiles that should be null are null
        for (int i = 0; i < initialBoard.getSize(); i += 2) {
            for (int j = 0; j < initialBoard.getSize(); j += 2) {
                assertNull(initialBoard.getTile(i,j));
            }
        }
        for (int i = 1; i < initialBoard.getSize(); i += 2) {
            for (int j = 1; j < initialBoard.getSize(); j += 2) {
                assertNull(initialBoard.getTile(i,j));
            }
        }
        // tiles that should not be null are not null
        for (int i = 1; i < initialBoard.getSize(); i += 2) {
            for (int j = 0; j < initialBoard.getSize(); j += 2) {
                assertNotNull(initialBoard.getTile(i,j));
            }
        }
        for (int i = 0; i < initialBoard.getSize(); i += 2) {
            for (int j = 1; j < initialBoard.getSize(); j += 2) {
                assertNotNull(initialBoard.getTile(i,j));
            }
        }
    }

    @Test
    public void testInitialBoard_BlankTilePositions() {
        for (int j = 0; j < initialBoard.getSize(); j += 2) {
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
        for (int j = 0; j < initialBoard.getSize(); j += 2) {
            assertEquals(PlayerType.RED, initialBoard.getTile(0, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(1, j).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(2, j + 1).getPiece().getPlayer());
        }
    }


    @Test
    public void testInitialBoard_BlackPieces() {
        for (int j = 0; j < initialBoard.getSize(); j += 2) {
            assertEquals(PlayerType.BLACK, initialBoard.getTile(5, j).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(6, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(7, j).getPiece().getPlayer());
        }
    }

    // --------------------------------
    // BLANK BOARD
    // --------------------------------

    @Test
    public void testBlankBoard_NullTilePositions() {
        // tiles that should be null are null
        for (int i = 0; i < blankBoard.getSize(); i += 2) {
            for (int j = 0; j < blankBoard.getSize(); j += 2) {
                assertNull(blankBoard.getTile(i,j));
            }
        }
        for (int i = 1; i < blankBoard.getSize(); i += 2) {
            for (int j = 1; j < blankBoard.getSize(); j += 2) {
                assertNull(blankBoard.getTile(i,j));
            }
        }
        // tiles that should not be null are not null
        for (int i = 1; i < blankBoard.getSize(); i += 2) {
            for (int j = 0; j < blankBoard.getSize(); j += 2) {
                assertNotNull(blankBoard.getTile(i,j));
            }
        }
        for (int i = 0; i < blankBoard.getSize(); i += 2) {
            for (int j = 1; j < blankBoard.getSize(); j += 2) {
                assertNotNull(blankBoard.getTile(i,j));
            }
        }
    }

    @Test
    public void testBlankBoard_BlankTilePositions() {
        for (int i = 1; i < blankBoard.getSize(); i += 2) {
            for (int j = 0; j < blankBoard.getSize(); j += 2) {
                assertTrue(blankBoard.getTile(i, j).isBlank());
            }
        }
        for (int i = 0; i < blankBoard.getSize(); i += 2) {
            for (int j = 1; j < blankBoard.getSize(); j += 2) {
                assertTrue(blankBoard.getTile(i, j).isBlank());
            }
        }
    }

    // --------------------------------
    // GETTING ROWS
    // --------------------------------

    @Test
    public void testGetTilesInRow1() {
        int row = 1;

        // Arrange
        GridBoard board = GridBoard.CreateBlankBoard();

        // Act
        Iterable<Tile> tilesInRow = board.getTilesInRow(row);

        // Assert
        Iterator<Tile> iter = tilesInRow.iterator();
        assertEquals(board.getTile(row, 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 2 + 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 4 + 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 6 + 1 - row % 2), iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void testGetTilesInRow5() {
        int row = 5;

        // Arrange
        GridBoard board = GridBoard.CreateBlankBoard();

        // Act
        Iterable<Tile> tilesInRow = board.getTilesInRow(row);

        // Assert
        Iterator<Tile> iter = tilesInRow.iterator();
        assertEquals(board.getTile(row, 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 2 + 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 4 + 1 - row % 2), iter.next());
        assertEquals(board.getTile(row, 6 + 1 - row % 2), iter.next());
        assertFalse(iter.hasNext());
    }

    // --------------------------------
    // GETTING COLUMNS
    // --------------------------------

    @Test
    public void testGetTilesInColumns() {
        for (int i = 0; i < initialBoard.getSize(); i++) {
            testGetTilesInColumn(i);
        }
    }

    public void testGetTilesInColumn(int column) {
        // Arrange
        GridBoard board = GridBoard.CreateBlankBoard();

        // Act
        Iterable<Tile> tilesInColumn = board.getTilesInColumn(column);

        // Assert
        Iterator<Tile> iter = tilesInColumn.iterator();
        for (int i = 1 - column % 2; i < initialBoard.getSize(); i += 2) {
            assertEquals(board.getTile(i, column), iter.next());
        }
        assertFalse(iter.hasNext());
    }

    //--------------------------------
    // GETTING ALL TILES
    // --------------------------------

    @Test
    public void testGetAllTiles() {
        Iterable<Tile> allTiles = initialBoard.getAllTiles();
        int blankCount = 0;
        int redCount = 0;
        int blackCount = 0;
        for (Tile tile : allTiles) {
            if (tile.isBlank()) {
                blankCount++;
            } else if (tile.getPiece().getPlayer() == PlayerType.RED) {
                redCount++;
            } else if (tile.getPiece().getPlayer() == PlayerType.BLACK) {
                blackCount++;
            } else {
                fail();
            }
        }
        assertEquals(8, blankCount);
        assertEquals(12, blackCount);
        assertEquals(12, redCount);
    }

}
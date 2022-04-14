package edu.ai.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.ai.mainproj.checkers.*;

import java.util.Iterator;

class BoardTests {

    Board initialBoard;
    Board blankBoard;

    @BeforeEach
    void setUp() {
        initialBoard = Board.CreateCheckersInitialBoard();
        blankBoard = Board.CreateBlankBoard(6);
    }

    //--------------------------------
    // INITIAL BOARD
    // --------------------------------

    @Test
    void testInitialBoard_NullTilePositions() {
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
    void testInitialBoard_BlankTilePositions() {
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
    void testInitialBoard_RedPieces() {
        for (int j = 0; j < initialBoard.getSize(); j += 2) {
            assertEquals(PlayerType.RED, initialBoard.getTile(0, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(1, j).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(2, j + 1).getPiece().getPlayer());
        }
    }


    @Test
    void testInitialBoard_BlackPieces() {
        for (int j = 0; j < initialBoard.getSize(); j += 2) {
            assertEquals(PlayerType.BLACK, initialBoard.getTile(5, j).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(6, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(7, j).getPiece().getPlayer());
        }
    }

    //--------------------------------
    // BLANK BOARD
    // --------------------------------

    @Test
    void testBlankBoard_NullTilePositions() {
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
    void testBlankBoard_BlankTilePositions() {
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
    void testGetTilesInRow1() {
        int row = 1;

        // Arrange
        Board board = Board.CreateBlankBoard();

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
    void testGetTilesInRow5() {
        int row = 5;

        // Arrange
        Board board = Board.CreateBlankBoard();

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
    void testGetTilesInColumns() {
        for (int i = 0; i < initialBoard.getSize(); i++) {
            testGetTilesInColumn(i);
        }
    }

    void testGetTilesInColumn(int column) {
        // Arrange
        Board board = Board.CreateBlankBoard();

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
    void testGetAllTiles() {
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

    @AfterEach
    void tearDown() {
        // if needed
    }
}
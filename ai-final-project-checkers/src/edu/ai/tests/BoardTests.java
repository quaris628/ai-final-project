package edu.ai.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.ai.mainproj.checkers.*;

class BoardTests {

    Board initialBoard;
    Board blankBoard;

    @BeforeEach
    void setUp() {
        initialBoard = Board.CreateInitialBoard();
        blankBoard = Board.CreateBlankBoard();
    }

    //--------------------------------
    // TEST INITIAL BOARD
    // --------------------------------

    @Test
    void testInitialBoard_NullTilePositions() {
        // tiles that should be null are null
        for (int i = 0; i < Board.SIZE; i += 2) {
            for (int j = 0; j < Board.SIZE; j += 2) {
                assertNull(initialBoard.getTile(i,j));
            }
        }
        for (int i = 1; i < Board.SIZE; i += 2) {
            for (int j = 1; j < Board.SIZE; j += 2) {
                assertNull(initialBoard.getTile(i,j));
            }
        }
        // tiles that should not be null are not null
        for (int i = 1; i < Board.SIZE; i += 2) {
            for (int j = 0; j < Board.SIZE; j += 2) {
                assertNotNull(initialBoard.getTile(i,j));
            }
        }
        for (int i = 0; i < Board.SIZE; i += 2) {
            for (int j = 1; j < Board.SIZE; j += 2) {
                assertNotNull(initialBoard.getTile(i,j));
            }
        }
    }

    @Test
    void testInitialBoard_BlankTilePositions() {
        for (int j = 0; j < Board.SIZE; j += 2) {
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
        for (int j = 0; j < Board.SIZE; j += 2) {
            assertEquals(PlayerType.RED, initialBoard.getTile(0, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(1, j).getPiece().getPlayer());
            assertEquals(PlayerType.RED, initialBoard.getTile(2, j + 1).getPiece().getPlayer());
        }
    }


    @Test
    void testInitialBoard_BlackPieces() {
        for (int j = 0; j < Board.SIZE; j += 2) {
            assertEquals(PlayerType.BLACK, initialBoard.getTile(5, j).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(6, j + 1).getPiece().getPlayer());
            assertEquals(PlayerType.BLACK, initialBoard.getTile(7, j).getPiece().getPlayer());
        }
    }

    //--------------------------------
    // TEST BLANK BOARD
    // --------------------------------

    @Test
    void testBlankBoard_NullTilePositions() {
        // tiles that should be null are null
        for (int i = 0; i < Board.SIZE; i += 2) {
            for (int j = 0; j < Board.SIZE; j += 2) {
                assertNull(blankBoard.getTile(i,j));
            }
        }
        for (int i = 1; i < Board.SIZE; i += 2) {
            for (int j = 1; j < Board.SIZE; j += 2) {
                assertNull(blankBoard.getTile(i,j));
            }
        }
        // tiles that should not be null are not null
        for (int i = 1; i < Board.SIZE; i += 2) {
            for (int j = 0; j < Board.SIZE; j += 2) {
                assertNotNull(blankBoard.getTile(i,j));
            }
        }
        for (int i = 0; i < Board.SIZE; i += 2) {
            for (int j = 1; j < Board.SIZE; j += 2) {
                assertNotNull(blankBoard.getTile(i,j));
            }
        }
    }

    @Test
    void testBlankBoard_BlankTilePositions() {
        for (int i = 1; i < Board.SIZE; i += 2) {
            for (int j = 0; j < Board.SIZE; j += 2) {
                assertTrue(blankBoard.getTile(i, j).isBlank());
            }
        }
        for (int i = 0; i < Board.SIZE; i += 2) {
            for (int j = 1; j < Board.SIZE; j += 2) {
                assertTrue(blankBoard.getTile(i, j).isBlank());
            }
        }
    }


    @AfterEach
    void tearDown() {
        // not a to-do, use only if needed
    }
}
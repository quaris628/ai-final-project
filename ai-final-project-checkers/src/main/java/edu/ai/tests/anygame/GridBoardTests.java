package edu.ai.tests.anygame;

import edu.ai.mainproj.anygame.GridBoard;
import edu.ai.mainproj.anygame.Tile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for GridBoard class
 * <p>
 * Tests methods:
 * - Constructor
 * - getTilesInRow
 * - getTilesInColumn
 * - getAllTiles
 * Tests indirectly / assumes functional:
 * - getTile
 * - getNumRows
 * - getNumColumns
 * Does not test:
 * - toString()
 * - toString(rowDelimiter)
 *
 * @author Nathan Swartz
 */
public class GridBoardTests {

    private static final GridBoard blankBoard = new GridBoard(8, 8);

    public GridBoardTests() {
    }

    // --------------------------------
    // BOARD CONSTRUCTOR
    // --------------------------------

    @Test
    public void testGridBoard_HasAllTiles() {
        for (int i = 1; i < blankBoard.getNumRows(); i++) {
            for (int j = 0; j < blankBoard.getNumColumns(); j++) {
                assertNotNull(blankBoard.getTile(i, j));
            }
        }
    }

    @Test
    public void testGridBoard_AllTilesBlank() {
        for (int i = 1; i < blankBoard.getNumRows(); i++) {
            for (int j = 0; j < blankBoard.getNumColumns(); j++) {
                assertTrue(blankBoard.getTile(i, j).isBlank());
            }
        }
    }

    @Test
    public void testGridBoard_TilesOutsideAreNull() {
        assertNull(blankBoard.getTile(-1, 2));
        assertNull(blankBoard.getTile(2, -1));
        assertNull(blankBoard.getTile(8, 5));
        assertNull(blankBoard.getTile(2, 8));
    }

    // --------------------------------
    // GETTING ROWS
    // --------------------------------

    @Test
    public void testGetTilesInRows() {
        for (int j = 0; j < blankBoard.getNumColumns(); j++) {
            testGetTilesInRow(j);
        }
    }

    private void testGetTilesInRow(int row) {
        Iterable<Tile> tilesInRow = blankBoard.getTilesInRow(row);

        Iterator<Tile> iter = tilesInRow.iterator();
        for (int j = 0; j < blankBoard.getNumRows(); j++) {
            assertEquals(blankBoard.getTile(row, j), iter.next());
        }
        assertFalse(iter.hasNext());
    }

    // --------------------------------
    // GETTING COLUMNS
    // --------------------------------

    @Test
    public void testGetTilesInColumns() {
        for (int i = 0; i < blankBoard.getNumRows(); i++) {
            testGetTilesInColumn(i);
        }
    }

    private void testGetTilesInColumn(int column) {
        Iterable<Tile> tilesInColumn = blankBoard.getTilesInColumn(column);

        Iterator<Tile> iter = tilesInColumn.iterator();
        for (int i = 0; i < blankBoard.getNumRows(); i++) {
            assertEquals(blankBoard.getTile(i, column), iter.next());
        }
        assertFalse(iter.hasNext());
    }

    //--------------------------------
    // GET ALL TILES
    // --------------------------------

    @Test
    public void testGetAllTiles() {
        Iterable<Tile> allTiles = blankBoard.getAllTiles();

        Set<Tile> tilesSet = new HashSet<Tile>();
        for (Tile tile : allTiles) {
            tilesSet.add(tile);
        }

        for (int i = 0; i < blankBoard.getNumRows(); i++) {
            for (int j = 0; j < blankBoard.getNumColumns(); j++) {
                assertTrue(tilesSet.contains(blankBoard.getTile(i, j)));
            }
        }
        int expectedNumTiles = blankBoard.getNumRows()
                * blankBoard.getNumColumns();
        assertEquals(expectedNumTiles, tilesSet.size());
    }

}
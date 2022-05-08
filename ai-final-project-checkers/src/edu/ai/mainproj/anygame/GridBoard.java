package edu.ai.mainproj.anygame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A rectangular grid board, consisting of tiles.
 * Supports any rectangular dimensions larger than 1x1.
 *
 * @author Nathan Swartz
 */
public class GridBoard {

    // rows are first argument / outside array, columns are second argument
    protected final Tile[][] board;

    /**
     * Creates a new grid board, with all tiles blank.
     * @param rows number of rows in the grid board
     * @param columns number of columns in the grid board
     */
    public GridBoard(int rows, int columns) {
        board = new Tile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Tile(this, i, j);
            }
        }
    }

    /**
     * Get Iterable over all tiles in the specified row
     * Tile order should start from column 0 with increasing column index
     * @param row
     * @return iterable of all tiles in row
     */
    public Iterable<Tile> getTilesInRow(int row) {
        return Arrays.asList(board[row]);
    }

    /**
     * Get Iterable over all tiles in the specified column
     * Tile order should start from row 0 with increasing row index
     * @param column
     * @return iterable of all tiles in column
     */
    public Iterable<Tile> getTilesInColumn(int column) {
        List<Tile> columnTiles = new LinkedList<Tile>();
        for (int i = 0; i < board[0].length; i++) {
            columnTiles.add(getTile(i, column));
        }
        return columnTiles;
    }

    /**
     * Get Iterable over all tiles in the board
     * Tile order should start with row 0,
     *     iterate through all tiles in row 0 by increasing column index,
     *     and repeat likewise for increasing row index.
     * In other words, like english reads, if 0,0 is top left.
     * @return iterable of all tiles in the board
     */
    public Iterable<Tile> getAllTiles() {
        List<Tile> allTiles = new LinkedList<Tile>();
        for (Tile[] row : board) {
            allTiles.addAll(Arrays.asList(row));
        }
        return allTiles;
    }

    /**
     * Gets tile at a row and column
     * @param row
     * @param column
     * @return tile at row, column
     */
    public Tile getTile(int row, int column) {
        if (row < 0 || board.length <= row
            || column < 0 || board[0].length <= column) {
            return null;
        }
        return board[row][column];
    }

    /**
     * Get number of rows in the board
     * @return number of rows
     */
    public int getNumRows() { return board.length; }

    /**
     * Get number of columns in the board
     * @return number of columns
     */
    public int getNumColumns() { return board[0].length; }

    /**
     * Returns a string representation of the board, with rows
     *     delimited by new lines.
     * "#" = null tile
     * @return string representation of the board
     */
    @Override
    public String toString() {
        return toString("\n");
    }

    /**
     * Returns a string representation of the board, with rows
     *     delimited by the specified string
     * "#" = null tile
     * @return string representation of the board
     */
    public String toString(String rowDelimiter) {
        StringBuilder s = new StringBuilder();
        // TODO maybe refactor to use iterators
        //  in getTilesInColumn/Row or getAllTiles ?
        //  (since these can be overriden by children)
        //  But this current code should work for any rectangular grid anyway so...
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                Tile tile = this.getTile(i, j);
                if (tile == null) {
                    s.append("\u001B[47m \u001B[0m");
                } else {
                    s.append("\u001B[40m");
                    s.append(tile);
                    s.append("\u001B[0m");
                }
            }
            s.append(rowDelimiter);
        }
        return s.toString();
    }

}

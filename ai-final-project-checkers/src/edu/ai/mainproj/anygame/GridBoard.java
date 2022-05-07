package edu.ai.mainproj.anygame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A rectangular grid board, consisting of tiles
 *
 * Also supports square boards of any even-numbered size, default 8x8.
 *
 * @author Nathan Swartz
 */
public class GridBoard {

    protected final Tile[][] board;

    public GridBoard(int rows, int columns) {
        board = new Tile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Tile(this, i, j);
            }
        }
    }

    public Iterable<Tile> getTilesInRow(int row) {
        return Arrays.asList(board[row]);
    }

    public Iterable<Tile> getTilesInColumn(int column) {
        List<Tile> columnTiles = new LinkedList<Tile>();
        for (int i = 0; i < board[0].length; i++) {
            columnTiles.add(getTile(i, column));
        }
        return columnTiles;
    }

    public Iterable<Tile> getAllTiles() {
        List<Tile> allTiles = new LinkedList<Tile>();
        for (Tile[] row : board) {
            allTiles.addAll(Arrays.asList(row));
        }
        return allTiles;
    }

    public Tile getTile(int row, int column) {
        if (row < 0 || board.length <= row
            || column < 0 || board[0].length <= column) {
            return null;
        }
        return board[row][column];
    }

    public int getNumRows() { return board.length; }
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

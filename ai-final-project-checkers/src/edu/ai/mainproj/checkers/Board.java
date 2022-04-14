package edu.ai.mainproj.checkers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A checkers board
 * The board consists of a grid of tiles, where only half of the
 *     tiles, in a checkerboard pattern, are in-play. The others
 *     are never used.
 * The tile at position 0,0 is out-of-play.
 * The tiles at positions 0,1 and 1,0 are in-play.
 *
 * Also supports square boards of any even-numbered size, default 8.
 *
 * @author Nathan Swartz
 */
public class Board {
    // Even sizes only
    // If it's less than 8, CreateCheckersInitialBoard will fail
    public final static int DEFAULT_SIZE = 8;

    private final Tile[][] board;
    private final int size;

    // creates a new board of specified size
    // initializes tiles to be all blank, does not add pieces
    private Board(int size) {
        if (size % 2 != 0) {
            throw new IllegalArgumentException("Size must be even");
        }
        this.size = size;
        board = new Tile[size][size / 2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                board[i][j] = new Tile(this, i, 2 * j + (1 - i % 2));
            }
        }
    }

    // creates a new board of default size 8
    // initializes tiles to be all blank, does not add pieces
    private Board() {
        size = DEFAULT_SIZE;
        board = new Tile[size][size / 2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                board[i][j] = new Tile(this, i, 2 * j + (1 - i % 2));
            }
        }
    }

    /**
     * Creates a new board, with pieces placed according to
     *     the traditional checkers initial board state.
     * @return new board in starting state for checkers
     */
    public static Board CreateCheckersInitialBoard() {
        Board toReturn = new Board();
        // Place Red pieces
        for (Tile tile : toReturn.getTilesInRow(0)) {
            tile.setPiece(new Piece(PlayerType.RED, tile));
        }
        for (Tile tile : toReturn.getTilesInRow(1)) {
            tile.setPiece(new Piece(PlayerType.RED, tile));
        }
        for (Tile tile : toReturn.getTilesInRow(2)) {
            tile.setPiece(new Piece(PlayerType.RED, tile));
        }
        // Place Black pieces
        for (Tile tile : toReturn.getTilesInRow(5)) {
            tile.setPiece(new Piece(PlayerType.BLACK, tile));
        }
        for (Tile tile : toReturn.getTilesInRow(6)) {
            tile.setPiece(new Piece(PlayerType.BLACK, tile));
        }
        for (Tile tile : toReturn.getTilesInRow(7)) {
            tile.setPiece(new Piece(PlayerType.BLACK, tile));
        }
        return toReturn;
    }

    /**
     * Creates a new board of the default size 8, with all tiles blank
     * @return new blank board
     */
    public static Board CreateBlankBoard() {
        return new Board();
    }

    /**
     * Creates a new, blank board of a specified size.
     * Size must be even.
     * @param size
     */
    public static Board CreateBlankBoard(int size) {
        return new Board(size);
    }

    /**
     * Gets the tile at a location on the board, specified by
     *     the row and column of that tile's location
     * 0,0 is the upper left
     * @param row
     * @param column
     * @return tile located at row, column
     */
    public Tile getTile(int row, int column) {
        if (row < 0 || size <= row) {
            throw new IndexOutOfBoundsException(
                    "Row must be >= 0 and < " + size);
        } else if (column < 0 || size <= column) {
            throw new IndexOutOfBoundsException(
                    "Column must be >= 0 and < " + size);
        }

        if (column % 2 != row % 2) {
            return board[row][column / 2];
        } else {
            return null;
        }
    }

    /**
     * Returns all in-play tiles in a specified row of the board
     * 0 corresponds to the topmost row
     * Maintains order--starts at left edge and goes right
     * @param row
     * @return Iterable of all tiles in the row
     */
    public Iterable<Tile> getTilesInRow(int row) {
        return Arrays.asList(board[row]);
    }

    /**
     * Returns all in-play tiles in a specified column of the board
     * 0 corresponds to the leftmost column
     * Maintains order--starts at top edge and goes down
     * @param column
     * @return Iterable of all tiles in the column
     */
    public Iterable<Tile> getTilesInColumn(int column) {
        List<Tile> columnTiles = new LinkedList<Tile>();
        for (int i = 1 - column % 2; i < size; i+=2) {
            columnTiles.add(getTile(i, column));
        }
        return columnTiles;
    }

    /**
     * Returns all in-play tiles on the board
     * Maintains order--starts in upper left and goes
     *     rightwards before downwards (like english reading)
     * @return Iterable of all tiles on the board
     */
    public Iterable<Tile> getAllTiles() {
        List<Tile> allTiles = new LinkedList<Tile>();
        for (Tile[] row : board) {
            allTiles.addAll(Arrays.asList(row));
        }
        return allTiles;
    }

    /**
     * Gets the size of the board. Default 8.
     * @return board size
     */
    public int getSize() { return size;}

    /**
     * Returns a string representation of the board, with
     *     a specified delimiter between the rows.
     * "#" = out-of-play tile
     * " " = blank tile
     * "O" = red piece
     * "X" = black piece
     * @return string representation of the board
     */
    @Override
    public String toString() {
        return toString("\n");
    }

    /**
     * Returns a string representation of the board, with rows
     *     delimited by new lines.
     * "#" = out-of-play tile
     * " " = blank tile
     * "O" = red piece
     * "X" = black piece
     * @return string representation of the board
     */
    public String toString(String rowDelimiter) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = this.getTile(i, j);
                if (tile == null) {
                    s.append("#");
                } else if (tile.isBlank()) {
                    s.append(" ");
                } else if (tile.getPiece().getPlayer() == PlayerType.RED) {
                    s.append("O");
                } else if (tile.getPiece().getPlayer() == PlayerType.BLACK) {
                    s.append("X");
                } else {
                    // just as a safety / catch-all
                    s.append("?");
                }
            }
            s.append(rowDelimiter);
        }
        return s.toString();
    }

}

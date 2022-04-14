package edu.ai.mainproj.checkers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {
    // Supports any square board size
    public final static int SIZE = 8;

    private final Tile[][] board;
    // The 0,0 tile should not be in play
    // 0,1 and 1,0 should be playable tiles

    private Board() {
        board = new Tile[SIZE][SIZE / 2];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                board[i][j] = new Tile(this);
            }
        }
    }

    public static Board CreateInitialBoard() {
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

    // Probably useful for testing purposes
    public static Board CreateBlankBoard() {
        return new Board();
    }

    public Tile getTile(int row, int column) {
        if (row < 0 || SIZE <= row) {
            throw new IndexOutOfBoundsException(
                    "Row must be >= 0 and < " + SIZE);
        } else if (column < 0 || SIZE <= column) {
            throw new IndexOutOfBoundsException(
                    "Column must be >= 0 and < " + SIZE);
        }

        if (column % 2 != row % 2) {
            return board[row][column / 2];
        } else {
            return null;
        }
    }

    public Iterable<Tile> getTilesInRow(int row) {
        return Arrays.asList(board[row]);
    }

    // untested
    public Iterable<Tile> getTilesInColumn(int column) {
        List<Tile> columnTiles = new LinkedList<Tile>();
        for (int i = 0; i < SIZE; i+=2) {
            columnTiles.add(getTile(i, column));
        }
        return columnTiles;
    }

    // untested
    public Iterable<Tile> getAllTiles() {
        List<Tile> allTiles = new LinkedList<Tile>();
        for (Tile[] row : board) {
            allTiles.addAll(Arrays.asList(row));
        }
        return allTiles;
    }

    @Override
    public String toString() {
        return toString("\n");
    }
    public String toString(String rowDelimiter) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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

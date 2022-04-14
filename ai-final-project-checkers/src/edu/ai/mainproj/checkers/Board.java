package edu.ai.mainproj.checkers;

public class Board {
    // Assumes board is square
    public final static int SIZE = 8;

    private Tile[][] board;
    // The 0,0 tile should not be in play
    // 0,1 and 1,0 should be playable tiles

    private Board() {
        board = new Tile[SIZE][SIZE / 2];
        // TODO initialize board tiles
        // have null values in places where black tiles are?
    }

    public static Board CreateInitialBoard() {
        Board toReturn = new Board();
        // TODO initialize pieces
        return toReturn;
    }

    // Probably useful for testing purposes
    public static Board CreateBlankBoard() {
        return new Board();
    }

    public Tile getTile(int row, int column) {
        // TODO
        return null;
    }

    public Iterable<Tile> getAllTiles() {
        // TODO
        return null;
    }

}

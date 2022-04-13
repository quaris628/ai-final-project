package edu.ai.checkers;

public class Board {
    // Assumes board is square
    public final static int SIZE = 8;

    private Tile[][] board;

    private Board() {
        board = new Tile[SIZE][SIZE];
        // TODO initialize board tiles
        // have null values in places where black tiles are?
    }

    public Board CreateInitialBoard() {
        Board toReturn = new Board();
        // TODO initialize pieces
        return toReturn;
    }

    // Probably useful for testing purposes
    public Board CreateBlankBoard() {
        return new Board();
    }

}

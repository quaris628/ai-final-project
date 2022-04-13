package edu.ai.checkers;

public class Tile {

    private Board board;
    private Piece piece; // null means blank

    public Tile(Board board) {
        this.board = board;
        piece = null;
    }

    public Tile[] getNeighbors() {
        // TODO
        return null;
    }

    public Tile getNeighborAt(DiagonalDirection direction) {
        // TODO
        return null;
    }

    public boolean isKingerFor(PlayerType player) {
        // TODO return if player's pieces should be kinged on this tile
        return false;
    }

    // simple getters/setters
    public boolean isBlank() { return piece == null; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

package edu.ai.mainproj.anygame;

/**
 * A tile on a grid board
 * May contain a piece
 *
 * @author Nathan Swartz
 */
public class Tile {

    public final GridBoard board;
    public final int row;
    public final int column;
    private Piece piece; // null means blank

    public Tile(GridBoard board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
        piece = null;
    }

    public boolean isBlank() { return piece == null; }
    public void setPiece(Piece piece) { this.piece = piece; }
    public Piece getPiece() { return piece; }
    public void removePiece() { this.piece = null; }

    @Override
    public String toString() {
        if (isBlank()) {
            return " ";
        } else {
            return piece.toString();
        }
    }
}

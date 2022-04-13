package edu.ai.checkers;

public class Piece {

    private Tile tile;
    private PlayerType playerType;
    private boolean king;

    public Piece(PlayerType playerType, Tile tile) {
        this.tile = tile;
        this.playerType = playerType;
        this.king = false;
    }

    public void moveTo(Tile tile) {
        this.tile.setPiece(null);
        this.tile = tile;
        this.tile.setPiece(this);
        // TODO do more stuff here?
        // e.g. only allow valid moves?
        // complete all consequences of a move, such as removing other pieces or being kinged
        // but this method might get too big if we add too much to it
    }

    // simple getters/setters
    public boolean isKing() { return king; }
    public PlayerType getPlayer() { return playerType; }
}

package edu.ai.mainproj.checkers;

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
        // Validation of moves, etc is in the Move class
    }
	
	public void remove() {
		this.tile.setPiece(null);
		this.tile = null;
	}
	
    // simple getters/setters
    public boolean isKing() { return king; }
	public void king() { king = true; }
    public PlayerType getPlayer() { return playerType; }
	public Tile getTile() { return tile; }
}

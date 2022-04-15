package edu.ai.mainproj.checkers;

public class CheckersPiece extends Piece {

    private final PlayerType player;
    private boolean king;

    public CheckersPiece(PlayerType player, Tile tile) {
        super(tile);
        this.player = player;

    }

    /**
     * Kings this piece
     */
    public void king() { king = true; }

    public boolean isKing() { return king; }


}

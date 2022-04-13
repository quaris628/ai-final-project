package edu.ai.mainproj.checkers;

public enum PlayerType {
    RED,
    BLACK;
	
	public boolean isOpposite(PlayerType player) {
		if (this == RED) { return player == BLACK; }
		if (this == BLACK) { return player == RED; }
		return false;
	}
}

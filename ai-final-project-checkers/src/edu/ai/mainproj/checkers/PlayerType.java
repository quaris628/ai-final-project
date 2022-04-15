package edu.ai.mainproj.checkers;

/**
 * Enum for player types/colors
 * Black goes first and starts on the bottom
 * Red goes second and starts on the top
 *
 * @author Nathan Swartz
 */
public enum PlayerType {
    RED,
    BLACK;

	/**
	 * Returns whether the passed player is the
	 *     opposite color / opponent of this player
	 * @param player
	 * @return true if player is opposite color, else false
	 */
	public boolean isOpposite(PlayerType player) {
		return this.ordinal() != player.ordinal();
	}
}

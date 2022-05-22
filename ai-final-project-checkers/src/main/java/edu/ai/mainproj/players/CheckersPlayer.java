package edu.ai.mainproj.players;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.game.GameRunner;

/**
 * @author Nathan, Myles
 */
public interface CheckersPlayer {

    /**
     * Select a move, given a game state.
     * @param game
     * @return
     */
    CheckersMove selectMove(CheckersGamePlayable game);

    /**
     * Method called in a GameRunner's construction where
     *     this player can subscribe to whatever events it needs
     * @param gameRunner that contains this player object
     */
    void initialize(GameRunner gameRunner);
}

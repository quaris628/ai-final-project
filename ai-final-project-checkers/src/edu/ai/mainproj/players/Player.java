package edu.ai.mainproj.players;

import edu.ai.mainproj.checkers.CheckersGamePlayable;

/**
 *
 * @author Nathan, Myles
 */
public interface Player {

    void executeTurn(CheckersGamePlayable game);

    void notifyGameEnd(CheckersGamePlayable endGame);
}

package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;

/**
 *
 * @author Nathan, Myles
 */
public interface Player {

    void executeTurn(CheckersGamePlayable game);

    void notifyGameEnd(CheckersGamePlayable endGame);
}

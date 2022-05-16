package edu.ai.mainproj.players;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.moves.CheckersMove;

/**
 * @author Nathan, Myles
 */
public interface CheckersPlayer {

    CheckersMove selectMove(CheckersGamePlayable game);

    void notifyGameEnd(CheckersGamePlayable endGame);
}

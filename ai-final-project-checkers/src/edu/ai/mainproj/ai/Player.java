package edu.ai.mainproj.ai;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;

public interface Player {

    public void executeTurn(CheckersGamePlayable game);

    void receiveResult(PlayerType winner);
}

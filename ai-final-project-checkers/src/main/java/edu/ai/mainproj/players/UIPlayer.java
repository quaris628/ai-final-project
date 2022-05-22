package edu.ai.mainproj.players;

import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.game.GameRunner;

public class UIPlayer implements CheckersPlayer {

    private PlayerType color;

    public UIPlayer(PlayerType color) {
        this.color = color;
    }

    @Override
    public CheckersMove selectMove(CheckersGamePlayable game) {
        // TODO
        return game.getPossibleMoves().get(0);
    }

    @Override
    public void initialize(GameRunner gameRunner) {
        // TODO
    }
}

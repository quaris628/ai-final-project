package edu.ai.mainproj.ai;

import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMove;

import java.util.LinkedList;
import java.util.List;

public class AutoDifficultyAIPlayer extends AIPlayer {

    // null = draw?
    private List<Boolean> aiWins;
    private int numAIWins;
    private int numOpponentWins;

    public AutoDifficultyAIPlayer(PlayerType playerType, int depth) {
        super(playerType, depth);
        aiWins = new LinkedList<Boolean>();
        numAIWins = 0;
        numOpponentWins = 0;
    }

    @Override
    public void receiveResult(PlayerType winner) {
        // TODO record winner of this game
        // adjust difficulty (depth) accordingly
    }

}

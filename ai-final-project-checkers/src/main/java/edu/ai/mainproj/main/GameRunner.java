package edu.ai.mainproj.main;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.other.CheckersGameEvent;
import edu.ai.mainproj.players.CheckersPlayer;

public class GameRunner {

    private CheckersGamePlayable game;
    private CheckersPlayer black;
    private CheckersPlayer red;
    private CheckersGameEvent turnStart;
    private CheckersGameEvent turnComplete;
    private CheckersGameEvent gameComplete;

    public GameRunner(CheckersPlayer black, CheckersPlayer red) {
        this.game = new CheckersGame();
        this.black = black;
        this.red = red;
        this.turnStart = new CheckersGameEvent();
        this.turnComplete = new CheckersGameEvent();
        this.gameComplete = new CheckersGameEvent();
        gameComplete.subscribe(() -> black.notifyGameEnd(this.getGame()));
        gameComplete.subscribe(() -> red.notifyGameEnd(this.getGame()));
    }

    public GameRunner(CheckersGamePlayable game,
                      CheckersPlayer black, CheckersPlayer red) {
        this.game = game;
        this.black = black;
        this.red = red;
        this.turnStart = new CheckersGameEvent();
        this.turnComplete = new CheckersGameEvent();
        this.gameComplete = new CheckersGameEvent();
        gameComplete.subscribe(() -> black.notifyGameEnd(this.getGame()));
        gameComplete.subscribe(() -> red.notifyGameEnd(this.getGame()));
    }

    public void run() {
        while (!game.isDone()) {
            turnStart.broadcast();
            // Red's turn
            if (game.getTurn() == PlayerType.RED) {
                CheckersMove move = red.selectMove(game);
                if (!game.execute(move)) {
                    throw new IllegalArgumentException(
                            "Invalid move from Red: " + move.toString());
                }
                // Black's turn
            } else if (game.getTurn() == PlayerType.BLACK) {
                CheckersMove move = black.selectMove(game);
                if (!game.execute(move)) {
                    throw new IllegalArgumentException(
                            "Invalid move from Black: " + move.toString());
                }
                // If turn invalid
            } else {
                throw new IllegalStateException(
                        "Game has invalid player turn: "
                                + (game.getTurn() == null ? "null"
                                : game.getTurn().toString()));

            }
            turnComplete.broadcast();
        }
        gameComplete.broadcast();
    }

    public CheckersGamePlayable getGame() {
        return game;
    }

    public CheckersPlayer getRed() {
        return red;
    }

    public CheckersPlayer getBlack() {
        return black;
    }

    public CheckersGameEvent getTurnStart() {
        return turnStart;
    }

    public CheckersGameEvent getTurnComplete() {
        return turnComplete;
    }

    public CheckersGameEvent getGameComplete() {
        return gameComplete;
    }

    public void setGame(CheckersGamePlayable game) {
        this.game = game;
    }

    public void setRed(CheckersPlayer red) {
        this.red = red;
    }

    public void setBlack(CheckersPlayer black) {
        this.black = black;
    }
}

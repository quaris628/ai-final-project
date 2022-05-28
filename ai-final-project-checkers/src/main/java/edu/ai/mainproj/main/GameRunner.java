package edu.ai.mainproj.main;

import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.CheckersGamePlayable;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.events.ConsumerEvent;
import edu.ai.mainproj.events.RunnableEvent;
import edu.ai.mainproj.players.CheckersPlayer;

public class GameRunner extends Thread {

    private CheckersGamePlayable game;
    private CheckersPlayer black;
    private CheckersPlayer red;
    private RunnableEvent gameStart;
    private ConsumerEvent<PlayerType> turnStart;
    private ConsumerEvent<PlayerType> turnInvalidChoice;
    private ConsumerEvent<PlayerType> turnComplete;
    private RunnableEvent gameComplete;

    public GameRunner(CheckersPlayer black, CheckersPlayer red) {
        this.game = new CheckersGame();
        this.black = black;
        this.red = red;
        this.gameStart = new RunnableEvent();
        this.turnStart = new ConsumerEvent<PlayerType>();
        this.turnInvalidChoice = new ConsumerEvent<PlayerType>();
        this.turnComplete = new ConsumerEvent<PlayerType>();
        this.gameComplete = new RunnableEvent();
        black.initialize(this);
        red.initialize(this);
    }

    public GameRunner(CheckersGamePlayable game,
                      CheckersPlayer black, CheckersPlayer red) {
        this.game = game;
        this.black = black;
        this.red = red;
        this.gameStart = new RunnableEvent();
        this.turnStart = new ConsumerEvent<PlayerType>();
        this.turnInvalidChoice = new ConsumerEvent<PlayerType>();
        this.turnComplete = new ConsumerEvent<PlayerType>();
        this.gameComplete = new RunnableEvent();
        black.initialize(this);
        red.initialize(this);
    }

    @Override
    public void run() {
        gameStart.broadcast();
        while (!game.isDone()) {
            turnStart.broadcast(game.getTurn());
            if (game.getTurn() == PlayerType.RED) {
                doTurn(red);
            } else if (game.getTurn() == PlayerType.BLACK) {
                doTurn(black);
            } else {
                // If turn invalid
                throw new IllegalStateException(
                        "Game has invalid player turn: "
                                + (game.getTurn() == null ? "null"
                                : game.getTurn().toString()));

            }
            turnComplete.broadcast(game.getTurn());
        }
        gameComplete.broadcast();
    }

    private void doTurn(CheckersPlayer player) {
        CheckersMove move = player.selectMove(game);

        // executing move and updating display's copy of the pieces
        //     should not be done concurrently
        // See CanvasRenderer.initialize()
        boolean succeeded;
        synchronized (game) {
            succeeded = ((CheckersGame)game).executeLite(move);
        }
        // it's ok to recalculate the valid moves async, so move it out
        //     b/c it's computationally intensive
        ((CheckersGame)game).calculateValidMoves();

        if (!succeeded) {
            turnInvalidChoice.broadcast(game.getTurn());
            throw new IllegalArgumentException(
                    "Invalid move: " + move.toString());
        }
    }

    public CheckersGamePlayable getGame() { return game; }
    public CheckersPlayer getRed() { return red; }
    public CheckersPlayer getBlack() { return black; }
    public void setGame(CheckersGamePlayable game) { this.game = game; }
    public void setRed(CheckersPlayer red) { this.red = red; }
    public void setBlack(CheckersPlayer black) { this.black = black; }

    public RunnableEvent getGameStart() { return gameStart; }
    public ConsumerEvent<PlayerType> getTurnStart() { return turnStart; }
    public ConsumerEvent<PlayerType> getTurnInvalidChoice() { return turnInvalidChoice; }
    public ConsumerEvent<PlayerType> getTurnComplete() { return turnComplete; }
    public RunnableEvent getGameComplete() { return gameComplete; }

}

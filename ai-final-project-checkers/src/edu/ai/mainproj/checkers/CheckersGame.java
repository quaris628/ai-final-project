package edu.ai.mainproj.checkers;

import edu.ai.mainproj.checkers.moves.CheckersMove;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CheckersGame implements CheckersGamePlayable {

    private final CheckersBoard board;
    private final List<CheckersMove> moveHistory;
    private PlayerType turn;

    public CheckersGame() {
        board = CheckersBoard.CreateInitialBoard();
        moveHistory = new LinkedList<CheckersMove>();
    }

    /**
     * Gets the current board state
     *
     * @return board
     */
    @Override
    public CheckersBoard getBoardState() { return board; }

    /**
     * Get whose turn it is (either Red or Black)
     *
     * @return player whose turn it is
     */
    @Override
    public PlayerType getTurn() { return turn; }

    /**
     * Get all valid moves that could be made on this turn
     * (for whichever player's turn it is)
     *
     * @return collection of valid moves
     */
    @Override
    public Collection<CheckersMove> getPossibleMoves() {
        // TODO
        return null;
    }

    /**
     * Checks if a move is valid or not for the current game state
     *
     * @param move to check
     * @return true if move is valid, otherwise false
     */
    @Override
    public boolean isValid(CheckersMove move) {
        // TODO
        return false;
    }

    /**
     * Executes the given move.
     * If the move is invalid, no changes to the game state is made
     * and method returns false.
     * If the move is valid, the game state is updated accordingly
     * and this method returns true.
     *
     * @param move to make
     * @return true if move is valid and succeeds, false otherwise
     */
    @Override
    public boolean execute(CheckersMove move) {
        // TODO
        return false;
    }

    /**
     * Checks if the game is done or not
     *
     * @return true if game is done, otherwise false
     */
    @Override
    public boolean isDone() {
        // TODO
        return false;
    }

    /**
     * Gets the winner of the game
     *
     * @return player who won, or null if draw or game incomplete
     */
    @Override
    public PlayerType getWinner() {
        // TODO
        return null;
    }

    /**
     * Gets the list of all moves made during the game
     * Useful for reviewing the game after it's finished
     *
     * @return list of all moves
     */
    @Override
    public List<CheckersMove> getMoveHistory() { return moveHistory; }
}

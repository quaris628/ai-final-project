package edu.ai.mainproj.checkers;

import java.util.Collection;
import java.util.List;

/**
 * All behavior a checkers game must implement.
 * @author Nathan Swartz
 */
public interface CheckersGamePlayable {
    /**
     * Gets the current board state
     * @return board
     */
    Board getBoardState();

    /**
     * Get whose turn it is (either Red or Black)
     * @return player whose turn it is
     */
    PlayerType getTurn();

    /**
     * Get all valid moves that could be made on this turn
     * (for whichever player's turn it is)
     * @return collection of valid moves
     */
    Collection<Move> getPossibleMoves();

    /**
     * Checks if a move is valid or not for the current game state
     * @param move to check
     * @return true if move is valid, otherwise false
     */
    boolean isValid(Move move);

    /**
     * Executes the given move.
     * If the move is invalid, no changes to the game state is made
     *     and method returns false.
     * If the move is valid, the game state is updated accordingly
     *     and this method returns true.
     * @param move to make
     * @return true if move is valid and succeeds, false otherwise
     */
    boolean execute(Move move);

    /**
     * Checks if the game is done or not
     * @return true if game is done, otherwise false
     */
    boolean isDone();

    /**
     * Gets the winner of the game
     * @return player who won, or null if draw or game incomplete
     */
    PlayerType getWinner();

    /**
     * Gets the list of all moves made during the game
     * Useful for reviewing the game after it's finished
     * @return list of all moves
     */
    List<Move> getMoves();
}

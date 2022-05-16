package edu.ai.mainproj.checkers;

import edu.ai.mainproj.checkers.moves.CheckersMove;

import java.util.Collection;
import java.util.List;

/**
 * All behavior a checkers game must implement.
 *
 * @author Nathan Swartz
 */
public interface CheckersGamePlayable {
    /**
     * Gets the current board state
     *
     * @return board
     */
    CheckersBoard getBoardState();

    /**
     * Get whose turn it is (either Red or Black)
     *
     * @return player whose turn it is
     */
    PlayerType getTurn();

    /**
     * Get all valid moves that could be made on this turn
     * (for whichever player's turn it is)
     *
     * @return list of valid moves
     */
    List<? extends CheckersMove> getPossibleMoves();

    /**
     * Checks if a move is valid or not for the current game state
     * (May go unused, but it's easy enough to include so it'll stay here for now.)
     *
     * @param move to check
     * @return true if move is valid, otherwise false
     */
    boolean isValid(CheckersMove move);

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
    boolean execute(CheckersMove move);

    /**
     * Unexecutes the last move.
     * Pops the move at the end of the move history list,
     * and unexecutes it.
     */
    void unexecute();

    /**
     * Checks if the game is done or not
     *
     * @return true if game is done, otherwise false
     */
    boolean isDone();

    /**
     * Gets the winner of the game
     *
     * @return player who won, or null if draw or game incomplete
     */
    PlayerType getWinner();

    /**
     * Gets the list of all moves made during the game
     * Useful for reviewing the game after it's finished
     *
     * @return list of all moves
     */
    List<? extends CheckersMove> getMoveHistory();
}

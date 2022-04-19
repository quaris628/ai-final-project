package edu.ai.mainproj.checkers;

import edu.ai.mainproj.checkers.moves.CheckersMove;

import java.util.*;

public class CheckersGame implements CheckersGamePlayable {

    private final CheckersBoard board;
    private final List<CheckersMove> moveHistory;
    private PlayerType turn;
    private Set<CheckersMove> possibleValidMoves;
    private PlayerType winner;

    public CheckersGame() {
        board = CheckersBoard.CreateInitialBoard();
        moveHistory = new LinkedList<CheckersMove>();
        turn = PlayerType.BLACK;
        possibleValidMoves = new HashSet<CheckersMove>();
        winner = null;
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

    private Set<CheckersMove> generatePossibleValidMoves() {
        // TODO
        return null;
    }

    // one-liner getters
    @Override
    public boolean isValid(CheckersMove move) { return possibleValidMoves.contains(move); }
    @Override
    public CheckersBoard getBoardState() { return board; }
    @Override
    public PlayerType getTurn() { return turn; }
    @Override
    public Collection<CheckersMove> getPossibleMoves() { return possibleValidMoves; }
    @Override
    public boolean isDone() { return winner != null; }
    @Override
    public PlayerType getWinner() { return winner; }
    @Override
    public List<CheckersMove> getMoveHistory() { return moveHistory; }
}

package edu.ai.mainproj.players.aiplayer;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.main.GameRunner;
import edu.ai.mainproj.players.CheckersPlayer;

import java.util.*;

public class AIPlayer implements CheckersPlayer {

    private static final boolean PRINT_SEARCH_RESULTS = false;
    private static final long RAND_SEED = 10249712912L;

    // positive means in favor of black, negative in favor of red
    private static final Map<Integer, Heuristic> gameStateResultsMap =
            new HashMap<Integer, Heuristic>();

    private final PlayerType playerColor;
    protected int depth;
    private final Random random;

    // TODO a progress indicator variable

    public AIPlayer(PlayerType playerType, int depth) {
        this.playerColor = playerType;
        this.depth = depth;
        this.random = new Random(RAND_SEED);
    }

    @Override
    public CheckersMove selectMove(CheckersGamePlayable game) {
        assert game.getTurn() == playerColor;
        SearchResult result = search(game, depth, null, null);
        if (PRINT_SEARCH_RESULTS) {
            System.out.println(result);
        }
        return result.move;
    }

    // the search algorithm itself
    protected SearchResult search(CheckersGamePlayable game, int depth,
                                  Heuristic alpha_myBestPathValue,
                                  Heuristic beta_theirBestPathValue) {
        List<CheckersMove> bestMoves = new LinkedList<>();
        Heuristic heuristicOfBestMove = null;
        for (CheckersMove move : game.getPossibleMoves()) {
            game.execute(move);

            Heuristic moveHeuristic;
            // if game is done
            if (game.isDone()) {
                moveHeuristic = Heuristic.valueOf(game);
                gameStateResultsMap.put(game.hashCode(), moveHeuristic);
            // if maximum depth reached
            } else if (depth == 0) {
                moveHeuristic = Heuristic.valueOf(game);
            // if result is already known (with certainty)
            } else if (gameStateResultsMap.containsKey(game.hashCode())) {
                moveHeuristic = gameStateResultsMap.get(game.hashCode());
            // otherwise, recursively search
            } else {
                SearchResult result = search(game, depth - 1, alpha_myBestPathValue, beta_theirBestPathValue);
                moveHeuristic = result.heuristic;
            }

            assert game.getLastMove().equals(move);
            game.unexecute();

            int compareResult = moveHeuristic.isBetterThan(heuristicOfBestMove, game.getTurn());
            // if result is better than others found so far
            if (compareResult == 1) {
                heuristicOfBestMove = moveHeuristic;
                bestMoves = new LinkedList<CheckersMove>();
                bestMoves.add(move);
            // if result is the same as the others found so far
            } else if (compareResult == 0) {
                bestMoves.add(move);
            }

            // TODO test that the alphabeta pruning is implemented correctly
            // alphabeta pruning
            if (game.getTurn() == playerColor
                    && 1 == heuristicOfBestMove.isBetterThan(alpha_myBestPathValue, game.getTurn())) {
                alpha_myBestPathValue = heuristicOfBestMove;
            } else if (game.getTurn() != playerColor
                    && 1 == heuristicOfBestMove.isBetterThan(beta_theirBestPathValue, game.getTurn())) {
                beta_theirBestPathValue = heuristicOfBestMove;
            }

            if (beta_theirBestPathValue != null && 1 == beta_theirBestPathValue.isBetterThan(alpha_myBestPathValue, PlayerType.BLACK))
                break;
        }

        // if best move has certain heuristic value, add to hashmap
        if (heuristicOfBestMove.isCertain) {
            gameStateResultsMap.put(game.hashCode(), heuristicOfBestMove);
        }

        CheckersMove chosenMove = bestMoves.get(0);
        // or if more than one, make a random pick
        if (bestMoves.size() > 1) {
            chosenMove = bestMoves.get(random.nextInt(bestMoves.size()));
        }
        return new SearchResult(heuristicOfBestMove, chosenMove);
    }

    // do nothing, doesn't matter to this class
    @Override
    public void initialize(GameRunner gameRunner) {}

    public PlayerType getPlayerColor() { return playerColor; }

}

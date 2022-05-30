package edu.ai.mainproj.players.aiplayer;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.main.GameRunner;
import edu.ai.mainproj.other.Pair;
import edu.ai.mainproj.players.CheckersPlayer;

import java.util.*;

public class AIPlayer implements CheckersPlayer {

    private static final boolean PRINT_IN_MIN_MAX = false;
    private static final boolean PRINT_MOVE_TRACE = false;
    private static final boolean PRINT_SEARCH_RESULTS = false;
    // constants for values of each piece type for heuristic calculation
    private static final float NORMAL_PIECE_HEURISTIC_FRAC_OF_KING = 0.7f;
    private static final int NUM_PIECES_EACH_SIDE = 12;

    private static final float SELF_WIN_STATE = 1.0f;
    private static final float OPP_WIN_STATE = -1.0f;
    private static final float DRAW_STATE = 0.0f;

    // positive means in favor of black, negative in favor of red
    private static final Map<Integer, Float> gameStateResultsMap =
            new HashMap<Integer, Float>();

    private final PlayerType playerColor;
    protected int depth;

    // TODO a progress indicator variable

    public AIPlayer(PlayerType playerType, int depth) {
        this.playerColor = playerType;
        this.depth = depth;
    }

    @Override
    public CheckersMove selectMove(CheckersGamePlayable game) {
        SearchResult result = search(game, depth,
                Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        if (PRINT_SEARCH_RESULTS) {
            System.out.println(result);
        }
        return result.move;
    }

    // the search algorithm itself
    private SearchResult search(CheckersGamePlayable game, int depth, float alpha, float beta) {
        PlayerType player = game.getTurn();

        boolean maxNode = player == playerColor;
        List<SearchResult> bestResults = new LinkedList<>();
        float heuristicOfBestMove = maxNode ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        for (CheckersMove move : game.getPossibleMoves()) {
            game.execute(move);

            SearchResult result;
            // if game is done
            if (game.isDone()) {
                float heuristicValue = calcualteHeuristicGameDone(game, player);
                gameStateResultsMap.put(game.hashCode(), heuristicValue);
                result = new SearchResult(heuristicValue, true, null);
            // if maximum depth reached
            } else if (depth == 0) {
                float heuristicValue = calculateHeuristic(game, player);
                result = new SearchResult(heuristicValue, false, null);
            // if result is already known
            } else if (gameStateResultsMap.containsKey(game.hashCode())) {
                float heuristicValue = gameStateResultsMap.get(game.hashCode());
                result = new SearchResult(heuristicValue, true, null);
            // otherwise, recursively search
            } else {
                result = search(game, depth - 1, alpha, beta);
            }

            assert game.getLastMove().equals(move);
            game.unexecute();

            // if result is better than others found so far
            if (maxNode && heuristicOfBestMove < result.heuristic
                || !maxNode && heuristicOfBestMove > result.heuristic) {
                heuristicOfBestMove = heuristicIfMoveTaken;
            // if result is the same as others found so far
            } else if (maxNode && heuristicOfBestMove == result.heuristic
                    || !maxNode && heuristicOfBestMove == result.heuristic) {
                heuristicOfBestMove = heuristicIfMoveTaken;
            }

            // alphabeta pruning
            // TODO fix, currently no pruning happens b/c alpha and beta are never updated
            if ((maxNode && heuristicOfBestMove >= beta)
                || (!maxNode && heuristicOfBestMove <= alpha))
                break;
        }

        CheckersMove chosenMove = bestMoves.get(0);
        // if more than one, make a random pick
        if (bestMoves.size() > 1) {
            chosenMove = bestMoves.get(new Random().nextInt(bestMoves.size()));
        }
        return new SearchResult(heuristicOfBestMove, isCertain, chosenMove);
    }

    // TODO utilize new Heuristic class!
    private static float calcualteHeuristicGameDone(CheckersGamePlayable game, PlayerType player) {
        assert game.isDone();
        if (game.getWinner() == player)
            return SELF_WIN_STATE;
        else if (game.getWinner() != null) {
            return OPP_WIN_STATE;
        } else {
            return DRAW_STATE;
        }
    }

    private static float calculateHeuristic(CheckersGamePlayable game, PlayerType player) {
        int heuristic = 0;
        for (CheckersTile tile : game.getBoardState().getAllCheckersTiles()) {
            if (!tile.isBlank()) {
                CheckersPiece piece = tile.getCheckersPiece();

                float pieceHeuristic; // anywhere b/t 0.0 to 1.0
                if (piece.isKing()) {
                    pieceHeuristic = 1.0f;
                } else {
                    // distance moved across board
                    float distanceHeuristic;
                    int numRows = game.getBoardState().getNumRows();
                    if (piece.getPlayer() == PlayerType.BLACK) {
                        distanceHeuristic = (numRows - piece.getCheckersTile().row) / (float) numRows;
                    } else {
                        distanceHeuristic = piece.getCheckersTile().row / (float) numRows;
                    }
                    pieceHeuristic = distanceHeuristic * NORMAL_PIECE_HEURISTIC_FRAC_OF_KING;
                }

                if (piece.getPlayer() == player)
                    heuristic += pieceHeuristic;
                else
                    heuristic -= pieceHeuristic;
            }
        }
        return heuristic / (1.0f + NUM_PIECES_EACH_SIDE);
    }

    // do nothing, doesn't matter to this class
    @Override
    public void initialize(GameRunner gameRunner) {}

    public PlayerType getPlayerColor() {
        return playerColor;
    }

    private static class SearchResult {
        public final float heuristic;
        public final boolean isCertain;
        public final CheckersMove move;
        public SearchResult(float heuristic, boolean isCertain, CheckersMove move) {
            this.heuristic = heuristic;
            this.isCertain = isCertain;
            this.move = move;
        }
        @Override
        public String toString() {
            return move.toString()
                    + " (" + String.format("%.02f", heuristic)
                    + ", " + (isCertain ? "Certain" : "Uncertain") + ")";
        }
    }

}

package edu.ai.mainproj.players;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.main.GameRunner;
import edu.ai.mainproj.other.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AIPlayer implements CheckersPlayer {

    private static final boolean PRINT_IN_MIN_MAX = false;
    private static final boolean PRINT_MOVE_TRACE = false;
    private static final boolean PRINT_SEARCH_RESULTS = false;
    // constants for values of each piece type for heuristic calculation
    private static final float OWN_PIECE_VALUE = 0.7f;
    private static final float OWN_KING_VALUE = 1.0f;
    private static final float OPP_PIECE_VALUE = -0.7f;
    private static final float OPP_KING_VALUE = -1.0f;
    private static final int NUM_PIECES_EACH_SIDE = 12;

    private static final float SELF_WIN_STATE = 1.0f;
    private static final float OPP_WIN_STATE = -1.0f;
    private static final float DRAW_STATE = 0.0f;

    private final PlayerType playerColor;
    protected int depth;

    // TODO a progress indicator variable

    public AIPlayer(PlayerType playerType, int depth) {
        this.playerColor = playerType;
        this.depth = depth;
    }

    @Override
    public CheckersMove selectMove(CheckersGamePlayable game) {
        Pair<CheckersMove, Float> result = search(game, depth,
                Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        if (PRINT_SEARCH_RESULTS) {
            System.out.print(result.getLeft().toString()
                    + " " + result.getRight().toString());
        }
        return result.getLeft();
    }

    // the search algorithm itself
    public Pair<CheckersMove, Float> search(CheckersGamePlayable game, int depth, float alpha, float beta) {
        PlayerType player = game.getTurn();
        // bottom of the search
        if (depth == 0) {
            // TODO if heuristic value is with certainty (win/loss/draw),
            //     store results in hashmap of <game state hash, heuristic value>
            //     (for performance improvement)
            //     ?: make hashmap static so both black and red AI players can access?
            //        would have to have oppositely signed heuristic value result tho
            //        maybe assign - to red and + to black
            return new Pair<>(game.getLastMove(), calculateHeuristic(game, player));
        }

        float heuristicOfBestMove;
        List<CheckersMove> bestMoves = new LinkedList<>();

        boolean maxNode = player == playerColor;
        heuristicOfBestMove = maxNode ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        for (CheckersMove move : game.getPossibleMoves()) {
            game.execute(move);

            // do search
            Pair<CheckersMove, Float> tmp = search(game, depth - 1, alpha, beta);
            float futureMovesHeuristic = tmp.getRight();

            if (maxNode) {
                heuristicOfBestMove = Math.max(heuristicOfBestMove, futureMovesHeuristic);
            } else {
                heuristicOfBestMove = Math.min(heuristicOfBestMove, futureMovesHeuristic);
            }

            // if these future moves are the best found so far
            if (heuristicOfBestMove == futureMovesHeuristic) {
                bestMoves.add(move);
            }

            assert game.getLastMove().equals(move);
            game.unexecute();

            // alphabeta pruning
            if ((maxNode && heuristicOfBestMove >= beta)
                || (!maxNode && heuristicOfBestMove <= alpha))
                break;
        }

        CheckersMove chosenMove = null;
        if (bestMoves.size() != 0) {
            chosenMove = bestMoves.get(new Random().nextInt(bestMoves.size()));
        }
        return new Pair<>(chosenMove, heuristicOfBestMove);
    }

    private static float calculateHeuristic(CheckersGamePlayable game, PlayerType player) {
        CheckersBoard board = game.getBoardState();
        // if game is over
        if (game.isDone()) {
            // return values under certainty
            if (game.getWinner() == player)
                return SELF_WIN_STATE;
            else if (game.getWinner() != null) {
                return OPP_WIN_STATE;
            } else {
                return DRAW_STATE;
            }
        // if game is in progress
        } else {
            // return heuristic
            int ret = 0;
            for (Tile tile : board.getAllTiles()) {
                if (tile.getPiece() instanceof CheckersPiece piece) {
                    if (piece.isKing()) {
                        if (piece.getPlayer() == player)
                            ret += OWN_KING_VALUE;
                        else
                            ret += OPP_KING_VALUE;
                    } else {
                        if (piece.getPlayer() == player)
                            ret += OWN_PIECE_VALUE;
                        else
                            ret += OPP_PIECE_VALUE;
                    }
                }
            }
            return ret / (1.0f + NUM_PIECES_EACH_SIDE);
        }
    }

    // do nothing, doesn't matter to this class
    @Override
    public void initialize(GameRunner gameRunner) {}

    public PlayerType getPlayerColor() {
        return playerColor;
    }

}

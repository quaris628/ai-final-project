package edu.ai.mainproj.players;

import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.other.Pair;

import java.util.LinkedList;
import java.util.List;

public class AIPlayer implements CheckersPlayer {

    private static final boolean PRINT_IN_MIN_MAX = false;
    private static final boolean PRINT_MOVE_TRACE = false;
    private static final boolean PRINT_SEARCH_RESULTS = true;
    // constants for values of each piece type for heuristic calculation
    private static final float OWN_PIECE_VALUE = 4f;
    private static final float OPP_PIECE_VALUE = 1f;
    private static final float OWN_KING_VALUE = 8f;
    private static final float OPP_KING_VALUE = 0f;

    private static final float SELF_WIN_STATE = 10000.23f;
    private static final float OPP_WIN_STATE = -10000.23f;

    private static final float OWN_NO_MOVES_STATE = 100.23f;
    private static final float OPP_NO_MOVES_STATE = 100.23f;

    private final PlayerType playerColor;
    protected int depth;

    private int printMovesStartIndex = 0;

    public AIPlayer(PlayerType playerType, int depth) {
        this.playerColor = playerType;
        this.depth = depth;
    }

    @Override
    public CheckersMove selectMove(CheckersGamePlayable game) {
        printMovesStartIndex = game.getMoveHistory().size();
        Pair<List<CheckersMove>, Float> result = search(game, depth, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);

        if (PRINT_SEARCH_RESULTS) {
            if (result.getRight() == OWN_NO_MOVES_STATE) {
                System.out.println("DRAW");
            }
            System.out.println(result.getRight());
        }

        return result.getLeft().get(result.getLeft().size() - 1);
    }

    // the search algorithm itself
    public Pair<List<CheckersMove>, Float> search(CheckersGamePlayable game, int depth, float alpha, float beta) {
        PlayerType player = game.getTurn();
        // bottom of the search
        if (depth == 0) {
            if (PRINT_MOVE_TRACE) {
                System.out.println("END OF DEPTH PRINT");
                List<? extends CheckersMove> moveHistory = game.getMoveHistory();
                for (int i = printMovesStartIndex; i < moveHistory.size(); i++)
                    System.out.print(moveHistory.get(i) + " ");
                System.out.println(calculateHerustics(game, player));
            }
            List<CheckersMove> ret = new LinkedList<>();
            ret.add(game.getMoveHistory().get(game.getMoveHistory().size() - 1));
            return new Pair<>(ret, calculateHerustics(game, player));
        }

        float value;
        // list of best moves to return
        List<CheckersMove> bestMoves = new LinkedList<>();
        if (player == playerColor) { // max player
            value = Float.NEGATIVE_INFINITY;
            for (CheckersMove move : game.getPossibleMoves()) {
                game.execute(move);
                if (PRINT_IN_MIN_MAX) {
                    System.out.println(game);
                    System.out.println(calculateHerustics(game, player));
                }
                Pair<List<CheckersMove>, Float> tmp = search(game, depth - 1, alpha, beta);
                value = Math.max(value, tmp.getRight());
                if (value == tmp.getRight()) {
                    if (!tmp.getLeft().isEmpty())
                        tmp.getLeft().remove(tmp.getLeft().size() - 1);
                    tmp.getLeft().add(move);
                    bestMoves = tmp.getLeft();
                }
                if (!game.getMoveHistory().get(game.getMoveHistory().size() - 1).equals(move))
                    throw new IllegalStateException("attempting to undo the wrong move");
                game.unexecute();
                if (PRINT_IN_MIN_MAX) System.out.println("UNDO");
                if (value >= beta)
                    break; // cutoff
            }
        } else { // min player
            value = Float.POSITIVE_INFINITY;
            for (CheckersMove move : game.getPossibleMoves()) {
                game.execute(move);
                if (PRINT_IN_MIN_MAX) {
                    System.out.println(game);
                    System.out.println(calculateHerustics(game, player));
                }
                Pair<List<CheckersMove>, Float> tmp = search(game, depth - 1, alpha, beta);
                value = Math.min(value, tmp.getRight());
                if (value == tmp.getRight()) {
                    if (!tmp.getLeft().isEmpty())
                        tmp.getLeft().remove(tmp.getLeft().size() - 1);
                    tmp.getLeft().add(move);
                    bestMoves = tmp.getLeft();
                }
                if (!game.getMoveHistory().get(game.getMoveHistory().size() - 1).equals(move))
                    throw new IllegalStateException("attempting to undo the wrong move");
                game.unexecute();
                if (PRINT_IN_MIN_MAX) System.out.println("UNDO");
                if (value <= alpha)
                    break; // cutoff
            }
        }

        return new Pair<>(bestMoves, value);
    }

    private static float calculateHerustics(CheckersGamePlayable boardP, PlayerType player) {
        CheckersBoard board = boardP.getBoardState();
        // win condition
        if (boardP.getWinner() != null) {
            if (boardP.getWinner() == player)
                return SELF_WIN_STATE;
            return OPP_WIN_STATE;
        }
        // draw condition
        if (boardP.getPossibleMoves().isEmpty()) {
            List<CheckersMove> ret = new LinkedList<>();
            ret.add(boardP.getMoveHistory().get(boardP.getMoveHistory().size() - 1));
            if (boardP.getTurn() == player)
                return OWN_NO_MOVES_STATE;
            else
                return OPP_NO_MOVES_STATE;
        }
        // calculate heuristic for the board otherwise
        int ret = 0;
        for (Tile gtile : board.getAllTiles()) {
            Piece gpiece = gtile.getPiece();
            if (gpiece instanceof CheckersPiece) {
                CheckersPiece piece = (CheckersPiece) gpiece;
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
        return ret;
    }

    // do nothing, doesn't matter to this class
    @Override
    public void notifyGameEnd(CheckersGamePlayable endGame) {
    }

    public PlayerType getPlayerColor() {
        return playerColor;
    }


}

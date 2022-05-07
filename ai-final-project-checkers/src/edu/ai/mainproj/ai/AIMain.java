package edu.ai.mainproj.ai;

import edu.ai.mainproj.anygame.Piece;
import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;

import java.util.Map;

public class AIMain {

    // nodes visited during the search
    private static long visited = 0;

    // constants for values of each piece type for heuristic calcuation
    private static final float OWN_PIECE_VALUE = 1f;
    private static final float OPP_PIECE_VALUE = -1f;
    private static final float OWN_KING_VALUE = 1.5f;
    private static final float OPP_KING_VALUE = -1.5f;

    private static final float BLACK_WIN_STATE = 1.23f;
    private static final float RED_WIN_STATE = -1.23f;

    public static void main(String... args) {
        CheckersGame game = new CheckersGame();
        CheckersGamePlayable gamep = (CheckersGamePlayable) game;
        CheckersPiece prevPrinted = null;
        for (CheckersMove move : game.getPossibleMoves()) {
            CheckersPiece piece = move.piece;
            CheckersTile dest = move.destination;
            CheckersTile pieceTile = piece.getCheckersTile();
            if (prevPrinted != piece)
                System.out.println("piece at " + pieceTile.column + ", " + pieceTile.row);
            prevPrinted = piece;
            System.out.println("  moves to " + dest.column + ", " + dest.row);
        }

        search(game, 10, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    // the search algorithm itself
    public static float search(CheckersGamePlayable game, int depth, float alpha, float beta) {
        visited++;
        PlayerType player = game.getTurn();
        if (depth == 0 || game.getPossibleMoves().isEmpty())
            return calculateHerustics(game, player);
        if (player == PlayerType.BLACK) { // max player
            float value = Float.NEGATIVE_INFINITY;
            for (CheckersMove move : game.getPossibleMoves()) {
                game.execute(move);
                System.out.println(game);
                float tmp = search(game, depth - 1, alpha, beta);
                value = Math.max(value, tmp);
                if (value != tmp) {
                    if (!game.getMoveHistory().get(game.getMoveHistory().size()-1).equals(move))
                        throw new IllegalStateException("attempting to undo the wrong move");
                    game.unexecute();
                }
                if (value >= beta)
                    break; // cutoff
            }
            return value;
        } else { // min player
            float value = Float.POSITIVE_INFINITY;
            for (CheckersMove move : game.getPossibleMoves()) {
                game.execute(move);
                System.out.println(game);
                float tmp = search(game, depth - 1, alpha, beta);
                value = Math.min(value, tmp);
                if (value != tmp) {
                    if (!game.getMoveHistory().get(game.getMoveHistory().size()-1).equals(move))
                        throw new IllegalStateException("attempting to undo the wrong move");
                    game.unexecute();
                }
                if (value <= alpha)
                    break; // cutoff
            }
            return value;
        }
    }

    private static float calculateHerustics(CheckersGamePlayable boardP, PlayerType player) {
        CheckersBoard board = boardP.getBoardState();
        if (boardP.getWinner() != null) {
            if (boardP.getWinner() == PlayerType.BLACK)
                return BLACK_WIN_STATE;
            return RED_WIN_STATE;
        }
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

}

package edu.ai.mainproj.players.aiplayer;

import edu.ai.mainproj.checkers.moves.CheckersMove;

public class SearchResult {
    public final Heuristic heuristic;
    public final CheckersMove move;
    public SearchResult(Heuristic heuristic, CheckersMove move) {
        this.heuristic = heuristic;
        this.move = move;
    }
    @Override
    public String toString() {
        return move.toString() + " " + heuristic.toString();
    }
}

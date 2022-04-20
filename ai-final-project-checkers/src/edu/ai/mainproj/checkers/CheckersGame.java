package edu.ai.mainproj.checkers;

import edu.ai.mainproj.checkers.moves.*;

import java.util.*;

public class CheckersGame implements CheckersGamePlayable {

    private final CheckersBoard board;
    private final List<CheckersMove> moveHistory;
    private PlayerType turn;
    private PlayerType winner;
    // caches
    private List<CheckersMove> possibleValidMoves;
    private List<CheckersPiece> blackPieces;
    private List<CheckersPiece> redPieces;


    public CheckersGame() {
        board = CheckersBoard.CreateInitialBoard();
        moveHistory = new LinkedList<CheckersMove>();
        turn = PlayerType.BLACK;
        winner = null;
        possibleValidMoves = calculateValidMoves();
        // TODO black and red pieces lists
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

        possibleValidMoves = calculateValidMoves();
        return false;
    }

    private List<CheckersMove> calculateValidMoves() {
        List<CheckersPiece> pieces =
                turn == PlayerType.BLACK ? blackPieces : redPieces;
        // if any pieces are off the board, remove them from the list
        pieces.removeIf(piece -> piece.getCheckersTile() == null);
        // if there are any jumps, just return those
        List<CheckersMove> jumps = getValidJumps(pieces);
        if (jumps.size() > 0) {
            return jumps;
        }
        return getValidNormalMoves(pieces);
    }

    // multi- and single, so type must be CheckersMove
    private List<CheckersMove> getValidJumps(List<CheckersPiece> pieces) {
        List<CheckersMove> moves = new LinkedList<CheckersMove>();
        for (CheckersPiece piece : pieces) {
            for (DiagonalDirection dir : DiagonalDirection.values()) {
                CheckersMoveJump move = CheckersMoveJump.Create(piece, dir);
                if (move != null && move.isValid()) {
                    List<CheckersMoveMultiJump> multiJumps = getValidMultiJumpsAfter(move);
                    if (multiJumps.size() > 0) {
                        moves.addAll(multiJumps);
                    } else {
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    private List<CheckersMoveMultiJump> getValidMultiJumpsAfter(CheckersMoveJump jump) {
        Set<CheckersTile> alreadyVisited = new HashSet<CheckersTile>();
        alreadyVisited.add(jump.startingTile);
        alreadyVisited.add(jump.destination);
        return getValidMultiJumpsAfterRecursive(jump.destination, jump.piece, alreadyVisited);
    }

    private List<CheckersMoveMultiJump> getValidMultiJumpsAfterRecursive(
            CheckersTile tile, CheckersPiece piece, Set<CheckersTile> alreadyVisited) {
        List<CheckersMoveMultiJump> moves = new LinkedList<CheckersMoveMultiJump>();
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            CheckersMoveJump jump = CheckersMoveJump.CreateAsPartOfMultiJump(piece, tile, dir);
            if (jump != null && jump.isValid()) {
                alreadyVisited.add(jump.destination);
                List<CheckersMoveMultiJump> multiJumps = getValidMultiJumpsAfterRecursive(
                        jump.destination, piece, alreadyVisited);
                if (multiJumps.size() > 0) {
                    for (CheckersMoveMultiJump multiJump : multiJumps) {
                        moves.add(CheckersMoveMultiJump.Create(jump, multiJump));
                    }
                    moves.addAll(multiJumps);
                } else {
                    moves.add(CheckersMoveMultiJump.Create(jump));
                }
            }
        }
        return moves;
    }

    // return as a list of regular moves to be compatible with
    //     calculateValidMoves returning this result
    private List<CheckersMove> getValidNormalMoves(List<CheckersPiece> pieces) {
        List<CheckersMove> moves = new LinkedList<CheckersMove>();
        for (CheckersPiece piece : pieces) {
            for (DiagonalDirection dir : DiagonalDirection.values()) {
                CheckersMoveNormal move = CheckersMoveNormal.Create(piece, dir);
                if (move != null && move.isValid()) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    // one-liner getters
    @Override
    public boolean isValid(CheckersMove move) { return possibleValidMoves.contains(move); }
    @Override
    public CheckersBoard getBoardState() { return board; }
    @Override
    public PlayerType getTurn() { return turn; }
    @Override
    public List<CheckersMove> getPossibleMoves() { return possibleValidMoves; }
    @Override
    public boolean isDone() { return winner != null; }
    @Override
    public PlayerType getWinner() { return winner; }
    @Override
    public List<CheckersMove> getMoveHistory() { return moveHistory; }
}

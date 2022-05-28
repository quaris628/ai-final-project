package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.moves.*;

import java.util.*;

public class CheckersGame implements CheckersGamePlayable {

    public static final int REPETITION_MAX = 3;

    private final CheckersBoard board;
    private final Map<Integer, Integer> boardHistory;
    // last element is most recent, first element is oldest
    private final List<CheckersMove> moveHistory;
    private PlayerType turn;
    private boolean done;
    private PlayerType winner;
    // caches
    private List<? extends CheckersMove> possibleValidMoves;
    private List<CheckersPiece> blackPieces;
    private List<CheckersPiece> redPieces;

    public CheckersGame() {
        board = CheckersBoard.CreateInitialBoard();
        boardHistory = new HashMap<Integer, Integer>();
        moveHistory = new LinkedList<CheckersMove>();
        turn = PlayerType.BLACK;
        done = false;
        winner = null;
        boardHistory.put(getGameStateHash(), 1);
        calculateValidMoves();
    }

    public CheckersGame(CheckersBoard initialBoard) {
        board = initialBoard;
        boardHistory = new HashMap<Integer, Integer>();
        moveHistory = new LinkedList<CheckersMove>();
        turn = PlayerType.BLACK;
        done = false;
        winner = null;
        boardHistory.put(getGameStateHash(), 1);
        calculateValidMoves();
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
        boolean success = executeLite(move);
        // re-calculate valid moves
        calculateValidMoves();
        return success;
    }

    /**
     * ONLY USE IF YOU KNOW WHAT YOU'RE DOING
     * Immediately call calculateValidMoves afterwards!
     */
    public boolean executeLite(CheckersMove move) {
        if (!move.isValid() && !done) {
            return false;
        }
        move.execute();
        moveHistory.add(move);
        // change whose turn it is
        turn = turn == PlayerType.RED ? PlayerType.BLACK : PlayerType.RED;

        // check current board state against previous board states for repetitions
        int hash = getGameStateHash();
        if (boardHistory.containsKey(hash)) {
            int oldValue = boardHistory.get(hash);
            boardHistory.replace(hash, oldValue + 1);
            if (oldValue + 1 >= REPETITION_MAX) {
                done = true;
                winner = null;
            }
        } else {
            boardHistory.put(hash, 1);
        }
        return true;
    }

    /**
     * Unexecutes the last move made.
     * Returns the game state to what it was before the last move.
     * If no moves have been made yet, does nothing.
     */
    @Override
    public void unexecute() {
        if (done) {
            done = false;
            winner = null;
        }
        if (moveHistory.size() == 0) {
            return;
        }

        // decrement number of times the current state has appeared in the game
        int hash = getGameStateHash();
        boardHistory.replace(hash, boardHistory.get(hash) - 1);

        // pop last move from list and unexecute it
        CheckersMove move = moveHistory.remove(moveHistory.size() - 1);
        move.unexecute();
        // change whose turn it is
        turn = turn == PlayerType.RED ? PlayerType.BLACK : PlayerType.RED;

        // re-calculate valid moves
        calculateValidMoves();
    }

    /**
     * Calculates all valid moves for the current board state.
     * Supporting function.
     * Called whenever the valid moves change, i.e. Constructor and execute()
     *
     * @return List of all possible and valid CheckersMove's
     */
    public List<? extends CheckersMove> calculateValidMoves() {
        refreshBlackRedPieces();
        // if one of the pieces lists was empty in refreshBlackRedPieces
        if (isDone()) {
            possibleValidMoves = new LinkedList<CheckersMove>();
            return possibleValidMoves;
        }

        List<CheckersPiece> pieces =
                turn == PlayerType.BLACK ? blackPieces : redPieces;
        // if any pieces are off the board, remove them from the list
        pieces.removeIf(piece -> piece.getCheckersTile() == null);
        // if there are any jumps, just return those
        List<CheckersMoveJump> jumps = new LinkedList<CheckersMoveJump>();
        for (CheckersPiece piece : pieces) {
            jumps.addAll(getValidJumpsFor(piece));
        }

        if (jumps.size() > 0) {
            possibleValidMoves = jumps;
        } else {
            List<CheckersMoveNormal> normalMoves = getValidNormalMoves(pieces);
            if (normalMoves.isEmpty()) {
                // Draw
                done = true;
                winner = null;
            }
            possibleValidMoves = normalMoves;
        }
        return possibleValidMoves;
    }

    /**
     * Calculates all valid jumps for one piece, given the current board state
     * Supporting function.
     * Called only in calculateValidMoves
     * public only so it can be tested. Won't break anything to use it outside
     * of unit tests though
     *
     * @param piece to get jumps for
     * @return List of possible valid jumps moves for the piece
     */
    public List<CheckersMoveJump> getValidJumpsFor(CheckersPiece piece) {
        Set<CheckersTile> alreadyVisited = new HashSet<CheckersTile>();
        alreadyVisited.add(piece.getCheckersTile());
        return getValidJumpsRecursive(piece.getCheckersTile(), piece, alreadyVisited);
    }

    /**
     * Recursive part of getValidJumpsFor
     * Supporting function
     *
     * @param tile           to start looking for jumps from
     * @param piece          that will be making the jumps
     * @param alreadyVisited tiles already explored, so we don't re-visit any
     * @return list of possible jumps from the given tile
     */
    private List<CheckersMoveJump> getValidJumpsRecursive(
            CheckersTile tile, CheckersPiece piece, Set<CheckersTile> alreadyVisited) {
        List<CheckersMoveJump> toReturn = new LinkedList<CheckersMoveJump>();
        // For each direction from this tile
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            // What if we jump?
            CheckersMoveJumpSingle jump;
            if (piece.getCheckersTile() == tile) {
                // if this is the first jump, it's just a single jump
                jump = CheckersMoveJumpSingle.Create(piece, dir);
            } else {
                // if this is a subsequent possible jump, it'll end up as part of a multi-jump move
                jump = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(piece, tile, dir);
            }

            // if it's possible, valid, and we haven't already visited this destination tile
            if (jump != null && jump.isValid() && !alreadyVisited.contains(jump.destination)) {
                // record that we're visiting this tile
                alreadyVisited.add(jump.destination);

                // look at all possible jumps from this location
                List<CheckersMoveJump> nextJumps = getValidJumpsRecursive(
                        jump.destination, piece, alreadyVisited);
                // for each one, prepend this jump onto each next jump and add it to toReturn
                for (CheckersMoveJump nextJump : nextJumps) {
                    CheckersMoveJumpMulti prependJump = nextJump.prepend(jump);
                    if (prependJump != null && prependJump.isValid()) {
                        toReturn.add(prependJump);
                    }
                }
                // also add this jump to toReturn if no other jumps are possible
                if (toReturn.size() == 0) {
                    toReturn.add(jump);
                }
            }
        }
        return toReturn;
    }

    /**
     * Calcualte all valid normal moves, for all of the given pieces
     * Supporting function
     * Called only by calculateValidMoves
     *
     * @param pieces to calculate valid moves for
     * @return all valid moves for the given pieces
     */
    private List<CheckersMoveNormal> getValidNormalMoves(List<CheckersPiece> pieces) {
        List<CheckersMoveNormal> moves = new LinkedList<CheckersMoveNormal>();
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

    /**
     * Refreshes the blackPieces and redPieces lists
     * Supporting function
     * Called only by calculateValidMoves
     * Iterates through every tile on the gameboard, and
     * if that tile has a peice, the piece is added to the
     * blackPieces or redPieces list according to its player
     */
    private void refreshBlackRedPieces() {
        blackPieces = new LinkedList<CheckersPiece>();
        redPieces = new LinkedList<CheckersPiece>();
        for (Tile btile : board.getAllTiles()) {
            if (btile instanceof CheckersTile) {
                CheckersTile tile = (CheckersTile) btile;
                if (!tile.isBlank()) {
                    CheckersPiece piece = tile.getCheckersPiece();
                    if (piece.getPlayer() == PlayerType.BLACK) {
                        blackPieces.add(piece);
                    } else if (piece.getPlayer() == PlayerType.RED) {
                        redPieces.add(piece);
                    }
                }
            }
        }
        if (blackPieces.isEmpty() || redPieces.isEmpty()) {
            done = true;
            if (blackPieces.isEmpty() && redPieces.isEmpty()) {
                winner = null;
            } else if (blackPieces.isEmpty()) {
                winner = PlayerType.RED;
            } else {
                winner = PlayerType.BLACK;
            }
        }
    }

    /**
     * Returns a hash combining board.hashCode() and turn.hashCode()
     * Useful for determining repetitions of the same game state
     *
     * @return hash combining board.hashCode() and turn.hashCode()
     */
    private int getGameStateHash() {
        return board.hashCode() * 29 + turn.hashCode();
    }

    public Iterable<CheckersPiece> getRedPieces() { return redPieces; }
    public Iterable<CheckersPiece> getBlackPieces() { return blackPieces; }

    /**
     * Returns all pieces currently on the board
     * List is a deep copy--Piece elements are still shallow copies
     * @return list of all checkers pieces on the board
     */
    public List<CheckersPiece> getPieces() {
        List<CheckersPiece> pieces = new LinkedList<CheckersPiece>();
        pieces.addAll(redPieces);
        pieces.addAll(blackPieces);
        return pieces;
    }

    @Override
    public boolean isValid(CheckersMove move) {
        return possibleValidMoves.contains(move);
    }

    @Override
    public CheckersBoard getBoardState() {
        return board;
    }

    @Override
    public PlayerType getTurn() {
        return turn;
    }

    @Override
    public List<? extends CheckersMove> getPossibleMoves() {
        return possibleValidMoves;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public PlayerType getWinner() {
        return winner;
    }

    @Override
    public List<? extends CheckersMove> getMoveHistory() {
        return moveHistory;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (getTurn() == PlayerType.BLACK) ret.append("\u001B[37m");
        else ret.append("\u001B[31m");
        ret.append(turn);
        ret.append("\u001B[0m");
        ret.append("\n");
        ret.append(board.toString());
        ret.append("B: ");
        ret.append(blackPieces.size());
        ret.append(" R: ");
        ret.append(redPieces.size());
        return ret.toString();
    }
}

package edu.ai.mainproj.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.moves.*;

import java.util.*;

public class CheckersGame implements CheckersGamePlayable {

    private final CheckersBoard board;
    private final List<CheckersMove> moveHistory;
    private PlayerType turn;
    private PlayerType winner;
    // caches
    private List<? extends CheckersMove> possibleValidMoves;
    private List<CheckersPiece> blackPieces;
    private List<CheckersPiece> redPieces;


    public CheckersGame() {
        board = CheckersBoard.CreateInitialBoard();
        moveHistory = new LinkedList<CheckersMove>();
        turn = PlayerType.BLACK;
        winner = null;
        possibleValidMoves = calculateValidMoves();
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
        // TODO is this method missing any code for parts that need to be done?
		
		// check if valid
		if (!move.isValid()) { return false; }
		
		// execute
		move.execute();
		
		// if any pieces were removed, remove from black/red lists
		if (move instanceof CheckersMoveJump) {
			CheckersMoveJump jump = (CheckersMoveJump)move;
			
		}
		
		// re-calculate valid moves
        possibleValidMoves = calculateValidMoves();
		
		// record move in move history
		moveHistory.add(move);

        // change whose turn it is
        turn = turn == PlayerType.RED ? PlayerType.BLACK : PlayerType.RED;
		
        return true;
    }

    private List<? extends CheckersMove> calculateValidMoves() {
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
            return jumps;
        }
        return getValidNormalMoves(pieces);
    }

    // multi- and single, so type must be CheckersMove
    private List<CheckersMoveJump> getValidJumpsFor(CheckersPiece piece) {
        /*
        List<CheckersMoveJump> moves = new LinkedList<CheckersMoveJump>();
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            CheckersMoveJumpSingle move = CheckersMoveJumpSingle.Create(piece, dir);
            if (move != null && move.isValid()) {
                List<CheckersMoveJumpMulti> multiJumps = getValidMultiJumpsAfter(move);
                if (multiJumps.size() > 0) {
                    moves.addAll(multiJumps);
                } else {
                    moves.add(move);
                }
            }
        }
        return moves;
        //*/
        Set<CheckersTile> alreadyVisited = new HashSet<CheckersTile>();
        alreadyVisited.add(piece.getCheckersTile());
        return getValidJumpsRecursive(piece.getCheckersTile(), piece, alreadyVisited);
    }

    private List<CheckersMoveJump> getValidJumpsRecursive(
            CheckersTile tile, CheckersPiece piece, Set<CheckersTile> alreadyVisited) {
        List<CheckersMoveJump> toReturn = new LinkedList<CheckersMoveJump>();
        // For each direction from this tile
        for (DiagonalDirection dir : DiagonalDirection.values()) {
            // What if we jump?
            CheckersMoveJumpSingle jump = CheckersMoveJumpSingle.CreateAsPartOfMultiJump(piece, tile, dir);
            // if it's possible, valid, and we haven't already visited this destination tile
            if (jump != null && jump.isValid() && !alreadyVisited.contains(jump.destination)) {
                // look at all possible jumps from this location
                List<CheckersMoveJump> nextJumps = getValidJumpsRecursive(
                        jump.destination, piece, alreadyVisited);
                // for each one, prepend this jump onto each next jump and add it to toReturn
                for (CheckersMoveJump nextJump : nextJumps) {
                    toReturn.add(nextJump.prepend(jump));
                }
                // record that we've visited this tile
                alreadyVisited.add(jump.destination);
            }
        }
        return toReturn;
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
    public List<? extends CheckersMove> getPossibleMoves() { return possibleValidMoves; }
    @Override
    public boolean isDone() { return winner != null; }
    @Override
    public PlayerType getWinner() { return winner; }
    @Override
    public List<? extends CheckersMove> getMoveHistory() { return moveHistory; }
}

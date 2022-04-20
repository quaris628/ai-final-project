package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

/**
 * An extra layer in the inheritance tree for all
 *     checkers moves to extend from.
 *
 * Any move that could be made even if it breaks the rules of
 *     checkers should be able to be constructed.
 * Any move that may be invalid depending on checkers
 *     rules and current state of the board should be able to
 *     be constructed, but isValid should return false.
 *
 * If a move is invalid, execute() should fail and
 *     throw an IllegalStateException
 * @author Nathan Swartz
 */
public class CheckersMove extends Move {

    public final CheckersPiece piece;
    public final CheckersTile destination;

    public CheckersMove(CheckersPiece piece, CheckersTile destination) {
        super(piece, destination);
        this.piece = piece;
        this.destination = destination;
    }

    // TODO good hash function
}

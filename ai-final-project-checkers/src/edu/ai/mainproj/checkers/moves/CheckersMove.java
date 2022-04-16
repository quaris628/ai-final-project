package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

/**
 * An extra layer in the inheritance tree for all
 *     checkers moves to extend from.
 * @author Nathan Swartz
 */
public class CheckersMove extends Move {

    protected final CheckersPiece piece;
    protected final CheckersTile destination;

    public CheckersMove(CheckersPiece piece, CheckersTile destination) {
        super(piece, destination);
        this.piece = piece;
        this.destination = destination;
    }
}

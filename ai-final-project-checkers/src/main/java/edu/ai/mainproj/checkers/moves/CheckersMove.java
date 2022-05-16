package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.anygame.Move;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

/**
 * An extra layer in the inheritance tree for all
 * checkers moves to extend from.
 * <p>
 * Any move that could be made even if it breaks the rules of
 * checkers should be able to be constructed.
 * Any move that may be invalid depending on checkers
 * rules and current state of the board should be able to
 * be constructed, but isValid should return false.
 * <p>
 * If a move is invalid, execute() should fail and
 * throw an IllegalStateException
 *
 * @author Nathan Swartz
 */
public class CheckersMove extends Move {

    public final CheckersPiece piece;
    public final CheckersTile destination;
    public boolean didKing;
    protected String stringRepresentation;

    public CheckersMove(CheckersPiece piece, CheckersTile destination) {
        super(piece, destination);
        this.piece = piece;
        this.destination = destination;
    }

    @Override
    public void execute() {
        super.execute();
        didKing = !piece.isKing()
                && destination.doesKing(piece.getPlayer());
        if (didKing) {
            piece.setKing(true);
        }
    }

    @Override
    public void unexecute() {
        super.unexecute();
        if (didKing) {
            piece.setKing(false);
        }
    }

    @Override
    public int hashCode() {
        return destination.hashCode() * 823 ^ piece.getTile().hashCode();
    }
}

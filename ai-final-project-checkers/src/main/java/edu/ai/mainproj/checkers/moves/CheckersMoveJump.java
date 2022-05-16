package edu.ai.mainproj.checkers.moves;

import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.CheckersTile;

public abstract class CheckersMoveJump extends CheckersMove {

    public CheckersMoveJump(CheckersPiece piece, CheckersTile destination) {
        super(piece, destination);
    }

    public abstract CheckersMoveJumpMulti append(CheckersMoveJumpSingle jump);

    public abstract CheckersMoveJumpMulti prepend(CheckersMoveJumpSingle jump);
}

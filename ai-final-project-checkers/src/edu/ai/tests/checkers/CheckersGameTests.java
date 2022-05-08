package edu.ai.tests.checkers;

import edu.ai.mainproj.anygame.Tile;
import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersBoard class
 *
 * TODO update this list
 * Tests methods:
 *  - TODO
 * Tests indirectly / assumes functional:
 *  - TODO
 * Does not test:
 *  - TODO
 *
 * @author Nathan Swartz
 */
public class CheckersGameTests {

    public CheckersGameTests() {}

    @Test
    public void getValidJumpsFor_oneJump_thatJump() {
        CheckersGame game = new CheckersGame();

        CheckersPiece jumper = game.getBoardState().getCheckersTile(5, 0).getCheckersPiece();
        CheckersMoveNormal jumperMove1 = CheckersMoveNormal.Create(
                jumper, DiagonalDirection.FORWARD_RIGHT);
        assertNotNull(jumperMove1);
        assertTrue(jumperMove1.isValid());
        assertTrue(game.execute(jumperMove1));

        CheckersPiece jumped = game.getBoardState().getCheckersTile(2, 3).getCheckersPiece();
        CheckersMoveNormal jumpedMove = CheckersMoveNormal.Create(
                jumped, DiagonalDirection.BACKWARD_LEFT);
        assertNotNull(jumpedMove);
        assertTrue(jumpedMove.isValid());
        assertTrue(game.execute(jumpedMove));

        List<CheckersMoveJump> possJumps =  game.getValidJumpsFor(jumper);
        CheckersMove actualJump = possJumps.get(0);
        assertTrue(actualJump instanceof CheckersMoveJumpSingle);

        // the suggested move is this particular jump
        CheckersMoveJumpSingle expectedJump = CheckersMoveJumpSingle.Create(
                jumper, DiagonalDirection.FORWARD_RIGHT);
        assertTrue(expectedJump.isValid());

        assertEquals(actualJump.hashCode(), expectedJump.hashCode());
        // only jumps can be made
        assertEquals(1, possJumps.size());
    }
}
package edu.ai.tests.checkers;

import edu.ai.mainproj.checkers.CheckersBoard;
import edu.ai.mainproj.checkers.CheckersGame;
import edu.ai.mainproj.checkers.CheckersPiece;
import edu.ai.mainproj.checkers.PlayerType;
import edu.ai.mainproj.checkers.moves.*;
import org.junit.Test;

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

    @Test
    public void threefoldRepetition_draw() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece red = new CheckersPiece(PlayerType.RED, board.getCheckersTile(7, 6));
        CheckersPiece black = new CheckersPiece(PlayerType.BLACK, board.getCheckersTile(0, 1));
        CheckersGame game = new CheckersGame(board);



        // destinations of the repetitive moves
        int[] x = new int[] {1, 6, 0, 7};
        int[] y = new int[] {0, 7, 1, 6};

        for (int i = 0; i < 3 * CheckersGame.REPETITION_MAX; i++) {
            List<? extends CheckersMove> moves = game.getPossibleMoves();
            assertEquals(2, moves.size());
            boolean loopCompleted = true;
            for (CheckersMove move : moves) {
                if (move.destination.equals(board.getCheckersTile(x[i % 4], y[i % 4]))) {
                    assertTrue(game.execute(move));
                    loopCompleted = false;
                    break;
                }
            }
            assertFalse(loopCompleted);
        }

        assertTrue(game.isDone());
        assertNull(game.getWinner());

    }
}
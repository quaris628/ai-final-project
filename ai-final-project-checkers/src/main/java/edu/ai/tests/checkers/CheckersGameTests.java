package edu.ai.tests.checkers;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Tests for CheckersBoard class
 * <p>
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

        List<CheckersMoveJump> possJumps = game.getValidJumpsFor(jumper);
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
    public void getPossibleMoves_initialState_7NormalMoves() {
        CheckersGame game = new CheckersGame();

        List<? extends CheckersMove> possMoves =  game.getPossibleMoves();

        for (CheckersMove move : possMoves) {
            assertTrue(move instanceof CheckersMoveNormal);
        }
        assertEquals(7, possMoves.size());
    }

    @Test
    public void threefoldRepetition_draw() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece red = new CheckersPiece(PlayerType.RED, board.getCheckersTile(7, 6));
        CheckersPiece black = new CheckersPiece(PlayerType.BLACK, board.getCheckersTile(0, 1));
        CheckersGame game = new CheckersGame(board);

        // destinations of the repetitive moves
        int[] x = new int[]{1, 6, 0, 7};
        int[] y = new int[]{0, 7, 1, 6};

        // MAX - 1 because initial state counts
        for (int i = 0; i < 4 * (CheckersGame.REPETITION_MAX - 1); i++) {
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

    @Test
    public void getPossibleMoves_JumpNormalOrJumpKing_bothJumps() {
        // illustration (upper left quadrant of board):
        //   # # # ...
        //    #r#R#
        //   # #b#
        //    # # #
        //   ...
        // all other squares are empty
        // it's black's turn
        // lowercase = normal piece, uppercase = king
        //
        // result should be:
        //  - jump move left
        //  - jump move right

        // setup
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(2, 3));
        CheckersPiece normalRedPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(1, 2));
        CheckersPiece kingRedPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(1, 4));
        kingRedPiece.setKing(true);
        CheckersGamePlayable game = new CheckersGame(board);

        System.out.println(game);

        // first move (black)
        List<? extends CheckersMove> moves = game.getPossibleMoves();

        assertEquals(2, moves.size());
        assertTrue(moves.get(0) instanceof CheckersMoveJumpSingle);
        assertTrue(moves.get(1) instanceof CheckersMoveJumpSingle);
        CheckersMoveJumpSingle move1 = (CheckersMoveJumpSingle)(moves.get(0));
        CheckersMoveJumpSingle move2 = (CheckersMoveJumpSingle)(moves.get(1));
        assertEquals(blackPiece, move1.piece);
        assertEquals(blackPiece, move2.piece);
        assertTrue((DiagonalDirection.FORWARD_RIGHT == move1.direction
                && DiagonalDirection.FORWARD_LEFT == move2.direction)
                || (DiagonalDirection.FORWARD_LEFT == move1.direction
                && DiagonalDirection.FORWARD_RIGHT == move2.direction));
    }
}

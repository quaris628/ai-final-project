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

    private static final CheckersBoard initialBoard = CheckersBoard.CreateInitialBoard();

    public CheckersGameTests() {}

    @Test
    public void getValidJumpsFor_oneJump_thatJump() {
        CheckersGame game = new CheckersGame();
        System.out.println(game);

        CheckersPiece jumper = initialBoard.getCheckersTile(5, 2).getCheckersPiece();
        CheckersMoveNormal jumperMove1 = CheckersMoveNormal.Create(
                jumper, DiagonalDirection.FORWARD_RIGHT);
        assertNotNull(jumperMove1);
        assertTrue(jumperMove1.isValid());
        assertTrue(game.execute(jumperMove1));
        assertEquals(1, game.getMoveHistory().size());
        System.out.println(game);

        CheckersPiece jumped = initialBoard.getCheckersTile(2, 3).getCheckersPiece();
        CheckersMoveNormal jumpedMove = CheckersMoveNormal.Create(
                jumped, DiagonalDirection.BACKWARD_LEFT);
        assertNotNull(jumpedMove);
        assertTrue(jumpedMove.isValid());
        assertTrue(game.execute(jumpedMove));
        assertEquals(2, game.getMoveHistory().size());
        System.out.println(game);

        List<? extends CheckersMove> possMoves =  game.getPossibleMoves();

        // should be the same as this jump
        CheckersMoveJumpSingle jump = CheckersMoveJumpSingle.Create(
                jumper, DiagonalDirection.FORWARD_RIGHT);

        assertEquals(1, possMoves.size());
        CheckersMove move = possMoves.get(0);
        assertEquals(move.hashCode(), jump.hashCode());

    }

}
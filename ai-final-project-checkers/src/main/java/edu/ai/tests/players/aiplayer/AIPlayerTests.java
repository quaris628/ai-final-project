package edu.ai.tests.players.aiplayer;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.CheckersMove;
import edu.ai.mainproj.checkers.moves.CheckersMoveJumpSingle;
import edu.ai.mainproj.checkers.moves.CheckersMoveNormal;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import edu.ai.mainproj.players.CheckersPlayer;
import edu.ai.mainproj.players.aiplayer.AIPlayer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AIPlayer class
 *
 * TODO description
 * Tests methods:
 *  - selectMove
 *  - TODO search
 * Tests indirectly / assumes functional:
 *  - Constructor
 * Does not test:
 *  - initialize (no need, does nothing)
 *  - getPlayerColor maybe TODO?
 *
 * Should implement alphabeta pruning
 *
 *
 * @author Nathan Swartz
 */
public class AIPlayerTests {

    public AIPlayerTests() {}

    @Test
    public void selectMove_depth3normalJumpWin() {
        final int DEPTH = 3;
        // set up an endgame with an obvious win
        // illustration (upper left quadrant of board):
        //   # # # ...
        //   r# #
        //   # # #
        //    # #
        //   # #b#
        //   ...
        // all other squares are empty
        // it's black's turn
        // neither piece is a king
        //
        // result should be:
        //  - black moves left
        //  - red moves right
        //  - black jumps and wins

        // setup
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(4, 3));
        CheckersPiece redPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(1, 0));
        CheckersGamePlayable game = new CheckersGame(board);
        CheckersPlayer black = new AIPlayer(PlayerType.BLACK, DEPTH);
        CheckersPlayer red = new AIPlayer(PlayerType.RED, DEPTH);

        // first move (black)
        CheckersMove move1 = black.selectMove(game);
        // should be moving the black piece to the left
        assertTrue(move1 instanceof CheckersMoveNormal);
        assertEquals(blackPiece, move1.piece);
        assertEquals(DiagonalDirection.FORWARD_LEFT,
                ((CheckersMoveNormal)move1).direction);
        assertEquals(board.getCheckersTile(3, 2), move1.destination);

        game.execute(move1);

        // second move (red)
        CheckersMove move2 = red.selectMove(game);
        // should be moving the red piece to the right
        assertTrue(move2 instanceof CheckersMoveNormal);
        assertEquals(redPiece, move2.piece);
        assertEquals(DiagonalDirection.BACKWARD_RIGHT,
                ((CheckersMoveNormal)move2).direction);
        assertEquals(board.getCheckersTile(2, 1), move2.destination);

        game.execute(move2);

        // third move (black)
        CheckersMove move3 = black.selectMove(game);
        // should be jumping the black piece to the left
        assertTrue(move3 instanceof CheckersMoveJumpSingle);
        assertEquals(blackPiece, move3.piece);
        assertEquals(DiagonalDirection.FORWARD_LEFT,
                ((CheckersMoveJumpSingle)move3).direction);
        assertEquals(board.getCheckersTile(1, 0), move3.destination);

        game.execute(move3);

        // game should be done, with black winning
        assertTrue(game.isDone());
        assertEquals(PlayerType.BLACK, game.getWinner());
    }

    @Test
    public void selectMove_depth2JumpNormalOrJumpKing_JumpsKing() {
        final int DEPTH = 2;
        // set up a choice between jumping a normal piece or
        //     jumping a king
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
        //  - black moves left
        //  - red moves right
        //  - black jumps and wins

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
        CheckersPlayer blackPlayer = new AIPlayer(PlayerType.BLACK, DEPTH);

        System.out.println(game);

        // first move (black)
        CheckersMove move = blackPlayer.selectMove(game);
        // should be a jump to the right
        assertTrue(move instanceof CheckersMoveJumpSingle);
        assertEquals(blackPiece, move.piece);
        assertEquals(DiagonalDirection.FORWARD_RIGHT,
                ((CheckersMoveJumpSingle)move).direction);
        assertEquals(board.getCheckersTile(0, 5), move.destination);
    }

}

package edu.ai.tests.players.aiplayer;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.players.aiplayer.Heuristic;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Heuristic class
 *
 * Tests methods:
 *  - isBetterThan
 *  - valueOf
 * Tests indirectly / assumes functional:
 *  - Constructor
 *  - isMoreCertainThan
 *  - valueOfCompletedGame
 *  - valueOfIncompleteGame
 * Does not test:
 *  - toString
 *
 * @author Nathan Swartz
 */
public class HeuristicTest {

    public HeuristicTest() {}

    @Test
    public void valueOf_blackWon_pos1Certain() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(4, 3));
        CheckersGamePlayable game = new CheckersGame(board);
        assertTrue(game.isDone());

        Heuristic heuristic = Heuristic.valueOf(game);
        assertEquals(1.0f, heuristic.value, Heuristic.FLOAT_DELTA);
        assertTrue(heuristic.isCertain);
    }

    @Test
    public void valueOf_redWon_neg1Certain() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece redPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(4, 3));
        CheckersGamePlayable game = new CheckersGame(board);
        assertTrue(game.isDone());

        Heuristic heuristic = Heuristic.valueOf(game);
        assertEquals(-1.0f, heuristic.value, Heuristic.FLOAT_DELTA);
        assertTrue(heuristic.isCertain);
    }

    @Test
    public void valueOf_oneBlackKingOneRedKing_0Certain() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(0, 3));
        CheckersPiece redPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(7, 2));
        CheckersGamePlayable game = new CheckersGame(board);
        assertFalse(game.isDone());

        Heuristic heuristic = Heuristic.valueOf(game);
        assertEquals(0.0f, heuristic.value, Heuristic.FLOAT_DELTA);
        assertFalse(heuristic.isCertain);
    }

    @Test
    public void valueOf_twoBlackKingOneRedKing_greaterThan0Uncertain() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece1 = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(0, 3));
        CheckersPiece blackPiece2 = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(0, 5));
        CheckersPiece redPiece = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(7, 2));
        CheckersGamePlayable game = new CheckersGame(board);
        assertFalse(game.isDone());

        Heuristic heuristic = Heuristic.valueOf(game);
        assertTrue(0.0f < heuristic.value);
        assertFalse(heuristic.isCertain);
    }

    @Test
    public void valueOf_oneBlackKingTwoRedKing_lessThan0Uncertain() {
        CheckersBoard board = new CheckersBoard();
        CheckersPiece blackPiece = new CheckersPiece(
                PlayerType.BLACK,
                board.getCheckersTile(0, 3));
        CheckersPiece redPiece1 = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(7, 4));
        CheckersPiece redPiece2 = new CheckersPiece(
                PlayerType.RED,
                board.getCheckersTile(7, 2));
        CheckersGamePlayable game = new CheckersGame(board);
        assertFalse(game.isDone());

        Heuristic heuristic = Heuristic.valueOf(game);
        assertTrue(heuristic.value < 0.0f);
        assertFalse(heuristic.isCertain);
    }

    @Test
    public void isBetterThan_morePositive_betterForBlack() {
        Heuristic test = new Heuristic(0.5f, false);
        Heuristic against = new Heuristic(0.0f, false);

        assertEquals(1, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(-1, test.isBetterThan(against, PlayerType.RED));
    }

    @Test
    public void isBetterThan_moreNegative_betterForRed() {
        Heuristic test = new Heuristic(-0.5f, false);
        Heuristic against = new Heuristic(0.0f, false);

        assertEquals(-1, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(1, test.isBetterThan(against, PlayerType.RED));
    }

    @Test
    public void isBetterThan_equalValueBothUncertain_equalForBoth() {
        Heuristic test = new Heuristic(0.0f, false);
        Heuristic against = new Heuristic(0.0f, false);

        assertEquals(0, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(0, test.isBetterThan(against, PlayerType.RED));
    }

    @Test
    public void isBetterThan_equalValueMoreCertain_betterForBoth() {
        Heuristic test = new Heuristic(0.0f, true);
        Heuristic against = new Heuristic(0.0f, false);

        assertEquals(1, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(1, test.isBetterThan(against, PlayerType.RED));
    }

    @Test
    public void isBetterThan_equalValueBothCertain_equalForBoth() {
        Heuristic test = new Heuristic(0.0f, true);
        Heuristic against = new Heuristic(0.0f, false);

        assertEquals(1, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(1, test.isBetterThan(against, PlayerType.RED));
    }

    @Test
    public void isBetterThan_compareToNull_betterForBoth() {
        Heuristic test = new Heuristic(0.0f, true);
        Heuristic against = null;

        assertEquals(1, test.isBetterThan(against, PlayerType.BLACK));
        assertEquals(1, test.isBetterThan(against, PlayerType.RED));
    }

}

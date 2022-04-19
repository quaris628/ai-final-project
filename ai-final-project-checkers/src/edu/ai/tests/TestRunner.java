package edu.ai.tests;

import edu.ai.tests.anygame.*;
import edu.ai.tests.checkers.*;
import edu.ai.tests.checkers.moves.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(
                GridBoardTests.class,
                PieceTests.class,
                TileTests.class,
                CheckersMoveJumpTests.class,
                CheckersMoveNormalTests.class,
                DiagonalDirectionTests.class,
                CheckersBoardTests.class,
                CheckersPieceTests.class,
                CheckersTileTests.class,
                PlayerTypeTests.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());

    }

}

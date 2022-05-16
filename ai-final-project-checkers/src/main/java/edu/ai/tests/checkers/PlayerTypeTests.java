package edu.ai.tests.checkers;

import edu.ai.mainproj.checkers.PlayerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for PlayerType enum
 * <p>
 * Tests methods:
 * - isOpposite
 *
 * @author Nathan Swartz
 */
public class PlayerTypeTests {

    public PlayerTypeTests() {
    }

    // --------------------------------
    // IS OPPOSITE
    // --------------------------------

    @Test
    public void testIsOpposite_RedRed_False() {
        assertFalse(PlayerType.RED.isOpposite(PlayerType.RED));
    }

    @Test
    public void testIsOpposite_RedBlack_True() {
        assertTrue(PlayerType.RED.isOpposite(PlayerType.BLACK));
    }

    @Test
    public void testIsOpposite_BlackRed_True() {
        assertTrue(PlayerType.BLACK.isOpposite(PlayerType.RED));
    }

    @Test
    public void testIsOpposite_BlackBlack_False() {
        assertFalse(PlayerType.BLACK.isOpposite(PlayerType.BLACK));
    }

}

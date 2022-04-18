package edu.ai.tests.checkers.moves;

import edu.ai.mainproj.checkers.*;
import edu.ai.mainproj.checkers.moves.DiagonalDirection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for DiagonalDirection enum
 *
 * Tests fields:
 *  - rowDetla
 *  - columnDelta
 * Tests methods:
 *  - isForwardsFor
 * Tests indirectly / assumes functional:
 *  - Constructor
 *
 * @author Nathan Swartz
 */
public class DiagonalDirectionTests {

    public DiagonalDirectionTests() {}

    // --------------------------------
    // ROW DELTA
    // --------------------------------

    @Test
    public void testRowDelta_Forwards_neg1() {
        assertEquals(-1, DiagonalDirection.FORWARD_LEFT.rowDelta);
        assertEquals(-1, DiagonalDirection.FORWARD_RIGHT.rowDelta);
    }

    @Test
    public void testRowDelta_Backwards_1() {
        assertEquals(1, DiagonalDirection.BACKWARD_LEFT.rowDelta);
        assertEquals(1, DiagonalDirection.BACKWARD_RIGHT.rowDelta);
    }

    // --------------------------------
    // COLUMN DELTA
    // --------------------------------

    @Test
    public void testColumnDelta_Left_neg1() {
        assertEquals(-1, DiagonalDirection.FORWARD_LEFT.columnDelta);
        assertEquals(-1, DiagonalDirection.BACKWARD_LEFT.columnDelta);
    }

    @Test
    public void testColumnDelta_Right_1() {
        assertEquals(1, DiagonalDirection.FORWARD_RIGHT.columnDelta);
        assertEquals(1, DiagonalDirection.BACKWARD_RIGHT.columnDelta);
    }

    // --------------------------------
    // IS FORWARDS FOR
    // --------------------------------

    // Forward left
    @Test
    public void testIsForwardsFor_ForwardLeft_Black_True() {
        assertTrue(DiagonalDirection.FORWARD_LEFT.isForwardsFor(PlayerType.BLACK));
    }

    @Test
    public void testIsForwardsFor_ForwardLeft_Red_False() {
        assertFalse(DiagonalDirection.FORWARD_LEFT.isForwardsFor(PlayerType.RED));
    }

    // Forward right
    @Test
    public void testIsForwardsFor_ForwardRight_Black_True() {
        assertTrue(DiagonalDirection.FORWARD_RIGHT.isForwardsFor(PlayerType.BLACK));
    }

    @Test
    public void testIsForwardsFor_ForwardRight_Red_False() {
        assertFalse(DiagonalDirection.FORWARD_RIGHT.isForwardsFor(PlayerType.RED));
    }

    // Backward right
    @Test
    public void testIsForwardsFor_BackwardRight_Black_False() {
        assertFalse(DiagonalDirection.BACKWARD_RIGHT.isForwardsFor(PlayerType.BLACK));
    }

    @Test
    public void testIsForwardsFor_BackwardRight_Red_True() {
        assertTrue(DiagonalDirection.BACKWARD_RIGHT.isForwardsFor(PlayerType.RED));
    }

    // Backward left
    @Test
    public void testIsForwardsFor_BackwardLeft_Black_False() {
        assertFalse(DiagonalDirection.BACKWARD_LEFT.isForwardsFor(PlayerType.BLACK));
    }

    @Test
    public void testIsForwardsFor_BackwardLeft_Red_True() {
        assertTrue(DiagonalDirection.BACKWARD_LEFT.isForwardsFor(PlayerType.RED));
    }

}

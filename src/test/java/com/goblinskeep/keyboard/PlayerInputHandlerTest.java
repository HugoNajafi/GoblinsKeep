package com.goblinskeep.keyboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PlayerInputHandler} class.
 * This class tests the behavior of key press and release events
 * and their effects on the player's movement and debug mode.
 */
public class PlayerInputHandlerTest {

    private PlayerInputHandler inputHandler;

    /**
     * Sets up the test environment by initializing the {@link PlayerInputHandler}.
     */
    @BeforeEach
    void setUp() {
        inputHandler = new PlayerInputHandler();
    }

    /**
     * Helper method to create a {@link KeyEvent}.
     *
     * @param type the type of key event (e.g., {@link KeyEvent#KEY_PRESSED})
     * @param code the key code (e.g., {@link KeyEvent#VK_W})
     * @return a {@link KeyEvent} instance
     */
    private KeyEvent key(int type, int code) {
        return new KeyEvent(
                new Canvas(),
                type,
                System.currentTimeMillis(),
                0,
                code,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    /**
     * Tests movement upwards using 'W' and the up arrow keys.
     */
    @Test
    void testMoveUpWithWAndArrow() {
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_W));
        assertTrue(inputHandler.up);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_W));
        assertFalse(inputHandler.up);

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_UP));
        assertTrue(inputHandler.up);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_UP));
        assertFalse(inputHandler.up);
    }

    /**
     * Tests movement downwards using 'S' and the down arrow keys.
     */
    @Test
    void testMoveDownWithSAndArrow() {
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_S));
        assertTrue(inputHandler.down);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_S));
        assertFalse(inputHandler.down);

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_DOWN));
        assertTrue(inputHandler.down);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_DOWN));
        assertFalse(inputHandler.down);
    }

    /**
     * Tests movement to the left using 'A' and the left arrow keys.
     */
    @Test
    void testMoveLeftWithAAndArrow() {
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_A));
        assertTrue(inputHandler.left);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_A));
        assertFalse(inputHandler.left);

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT));
        assertTrue(inputHandler.left);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_LEFT));
        assertFalse(inputHandler.left);
    }

    /**
     * Tests movement to the right using 'D' and the right arrow keys.
     */
    @Test
    void testMoveRightWithDAndArrow() {
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_D));
        assertTrue(inputHandler.right);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_D));
        assertFalse(inputHandler.right);

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT));
        assertTrue(inputHandler.right);

        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_RIGHT));
        assertFalse(inputHandler.right);
    }

    /**
     * Tests toggling debug mode using the 'F' key.
     */
    @Test
    void testDebugModeToggle() {
        boolean original = inputHandler.debugMode;

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_F));
        assertNotEquals(original, inputHandler.debugMode, "Debug mode should toggle on first F press");

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_F));
        assertEquals(original, inputHandler.debugMode, "Debug mode should toggle back on second F press");
    }

    /**
     * Tests that the {@link PlayerInputHandler#keyTyped(KeyEvent)} method does nothing.
     */
    @Test
    void keyTypedDoesNothingButIsCovered() {
        KeyEvent k = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED, // REQUIRED for KEY_TYPED
                's'                    // must be a valid character
        );

        inputHandler.keyTyped(k);
    }

    /**
     * Provides coverage for default cases in key press and release handling.
     */
    @Test
    void coverageForDefaultCases() {
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_Z));
        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_Z));
    }
}

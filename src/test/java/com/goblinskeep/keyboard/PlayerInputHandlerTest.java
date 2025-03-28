package com.goblinskeep.keyboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInputHandlerTest {

    private PlayerInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new PlayerInputHandler();
    }

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

    @Test
    void testDebugModeToggle() {
        boolean original = inputHandler.debugMode;

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_F));
        assertNotEquals(original, inputHandler.debugMode, "Debug mode should toggle on first F press");

        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_F));
        assertEquals(original, inputHandler.debugMode, "Debug mode should toggle back on second F press");
    }

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


    @Test
    void coverageForDefaultCases(){
        inputHandler.keyPressed(key(KeyEvent.KEY_PRESSED, KeyEvent.VK_Z));
        inputHandler.keyReleased(key(KeyEvent.KEY_RELEASED, KeyEvent.VK_Z));

    }
}
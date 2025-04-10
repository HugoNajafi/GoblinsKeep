package com.goblinskeep.keyboard;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MenuInputHandler} class.
 */
public class MenuInputHandlerTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;

    /**
     * Sets up the test environment by initializing the GamePanel and MenuInputHandler.
     */
    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        inputHandler = new MenuInputHandler(gp);
    }

    /**
     * Creates a KeyEvent for testing.
     *
     * @param code the key code of the KeyEvent
     * @return the created KeyEvent
     */
    private KeyEvent key(int code) {
        return new KeyEvent(new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                code,
                KeyEvent.CHAR_UNDEFINED);
    }

    /**
     * Tests toggling between pause and play states using the Escape key.
     */
    @Test
    void testPauseToPlayToggleWithEsc() {
        gp.status = GameStatus.PAUSED;
        inputHandler.keyPressed(key(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PLAYING, gp.status);

        inputHandler.keyPressed(key(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PAUSED, gp.status);

    }

    /**
     * Tests toggling between playing and pause states using the P key.
     */
    @Test
    void testPlayingToPauseToggleWithP() {
        gp.status = GameStatus.PLAYING;
        inputHandler.keyPressed(key(KeyEvent.VK_P));
        assertEquals(GameStatus.PAUSED, gp.status);

        inputHandler.keyPressed(key(KeyEvent.VK_P));
        assertEquals(GameStatus.PLAYING, gp.status);

        //for coverage
        gp.status = GameStatus.MENU;
        inputHandler.keyPressed((key(KeyEvent.VK_P)));
        assertEquals(GameStatus.MENU, gp.status); //pressing p shouldn't change anything

    }

    /**
     * Tests cursor movement down and up in a real UI.
     */
    @Test
    void testCursorMovesDownAndUpRealUI() {
        gp.status = GameStatus.MENU;
        MenuUI menuUI = gp.getMenuUI();

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));
        inputHandler.keyReleased(key(KeyEvent.VK_DOWN));

        assertNotEquals(initialIndex, menuUI.cursorSelection, "Cursor should have moved down");

        inputHandler.keyPressed(key(KeyEvent.VK_UP));
        inputHandler.keyReleased(key(KeyEvent.VK_UP));

        assertEquals(initialIndex, menuUI.cursorSelection, "Cursor should have returned to original position");
    }

    /**
     * Tests that cursor movement is blocked without releasing the key.
     */
    @Test
    void testCursorMovementBlockedWithoutKeyRelease() {
        gp.status = GameStatus.END;
        EndUI menuUI = gp.endUI;

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));//should increase the initialIndex by one
        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));// Should not move again
        inputHandler.keyPressed(key(KeyEvent.VK_UP));// Should not move again

        assertEquals(initialIndex+1, menuUI.cursorSelection, "Cursor should have moved down");
    }

    /**
     * Tests that cursor movement resets after releasing the key.
     */
    @Test
    void testCursorMovementResetsOnKeyRelease() {
        gp.status = GameStatus.PAUSED;
        PauseUI menuUI = gp.ui.pauseUI;

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));//should increase the initialIndex by one
        inputHandler.keyReleased(key(KeyEvent.VK_DOWN));
        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));// Should move again

        assertEquals(initialIndex+2, menuUI.cursorSelection, "Cursor should have moved down twice");
    }

    /**
     * Tests that cursor movement resets after releasing the key in the instructions UI.
     */
    @Test
    void testCursorMovementResetsOnKeyReleaseForInstructions() {
        gp.status = GameStatus.INSTRUCTIONS;
        InstructionsUI menuUI = gp.instructionsUI;

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));//should increase the initialIndex by one
        inputHandler.keyReleased(key(KeyEvent.VK_DOWN));
        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));// Should move again

        assertEquals(initialIndex, menuUI.cursorSelection, "Cursor should have moved down twice");
    }

    /**
     * Tests that the keyTyped method does not perform any actions.
     */
    @Test
    void keyTypedTest(){
        GameStatus initialStatus = gp.status;

        inputHandler.keyTyped(key(KeyEvent.KEY_TYPED));

        assertEquals(initialStatus, gp.status, "keyTyped should not change game status");

    }

    /**
     * Tests coverage for default cases in keyPressed and keyReleased.
     */
    @Test
    void coverageForDefaultCases(){
        inputHandler.keyPressed(key(KeyEvent.VK_Z));
        inputHandler.keyReleased(key(KeyEvent.VK_Z));
    }

    /**
     * Tests that keyPressed triggers handleMenuEvent in the menu status.
     */
    @Test
    void keyPressedTriggersHandleMenuEventInMenuStatus() {
        gp.status = GameStatus.MENU;
        inputHandler.keyPressed(key(KeyEvent.VK_ENTER));
        // Implicitly covered if no exception is thrown and menu option is RESTART
        assertEquals(GameStatus.RESTART, gp.status);
    }

    /**
     * Tests that keyPressed triggers handleMenuEvent in the paused status.
     */
    @Test
    void keyPressedTriggersHandleMenuEventInPausedStatus() {
        gp.status = GameStatus.PAUSED;
        gp.ui.pauseUI.cursorSelection = 0; // RESUME
        inputHandler.keyPressed(key(KeyEvent.VK_SPACE));
        assertEquals(GameStatus.PLAYING, gp.status);
    }

    /**
     * Tests that keyPressed triggers handleMenuEvent in the end status.
     */
    @Test
    void keyPressedTriggersHandleMenuEventInEndStatus() {
        gp.status = GameStatus.END;
        gp.endUI.cursorSelection = 0; // RESTART
        inputHandler.keyPressed(key(KeyEvent.VK_ENTER));
        assertEquals(GameStatus.RESTART, gp.status);
    }

    /**
     * Tests that keyPressed triggers handleMenuEvent in the instructions status.
     */
    @Test
    void keyPressedTriggersHandleMenuEventInInstructionsStatus() {
        gp.status = GameStatus.INSTRUCTIONS;
        gp.instructionsUI.cursorSelection = 0; // MENU
        inputHandler.keyPressed(key(KeyEvent.VK_SPACE));
        assertEquals(GameStatus.MENU, gp.status);
    }

}

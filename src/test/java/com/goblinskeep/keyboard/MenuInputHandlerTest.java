package com.goblinskeep.keyboard;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MenuInputHandlerTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        inputHandler = new MenuInputHandler(gp);
    }

    private KeyEvent key(int code) {
        return new KeyEvent(new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                code,
                KeyEvent.CHAR_UNDEFINED);
    }

    @Test
    void testPauseToPlayToggleWithEsc() {
        gp.status = GameStatus.PAUSED;
        inputHandler.keyPressed(key(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PLAYING, gp.status);

        inputHandler.keyPressed(key(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PAUSED, gp.status);

    }

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

    @Test
    void keyTypedTest(){
        GameStatus initialStatus = gp.status;

        inputHandler.keyTyped(key(KeyEvent.KEY_TYPED));

        assertEquals(initialStatus, gp.status, "keyTyped should not change game status");

    }


    @Test
    void coverageForDefaultCases(){
        inputHandler.keyPressed(key(KeyEvent.VK_Z));
        inputHandler.keyReleased(key(KeyEvent.VK_Z));
    }

    //for additional line coverages
    @Test
    void keyPressedTriggersHandleMenuEventInMenuStatus() {
        gp.status = GameStatus.MENU;
        inputHandler.keyPressed(key(KeyEvent.VK_ENTER));
        // Implicitly covered if no exception is thrown and menu option is RESTART
        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void keyPressedTriggersHandleMenuEventInPausedStatus() {
        gp.status = GameStatus.PAUSED;
        gp.ui.pauseUI.cursorSelection = 0; // RESUME
        inputHandler.keyPressed(key(KeyEvent.VK_SPACE));
        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void keyPressedTriggersHandleMenuEventInEndStatus() {
        gp.status = GameStatus.END;
        gp.endUI.cursorSelection = 0; // RESTART
        inputHandler.keyPressed(key(KeyEvent.VK_ENTER));
        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void keyPressedTriggersHandleMenuEventInInstructionsStatus() {
        gp.status = GameStatus.INSTRUCTIONS;
        gp.instructionsUI.cursorSelection = 0; // MENU
        inputHandler.keyPressed(key(KeyEvent.VK_SPACE));
        assertEquals(GameStatus.MENU, gp.status);
    }

}

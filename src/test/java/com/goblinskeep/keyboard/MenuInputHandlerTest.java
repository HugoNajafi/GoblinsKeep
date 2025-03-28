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
        gp.status = GameStatus.MENU;
        MenuUI menuUI = gp.getMenuUI();

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));//should increase the initialIndex by one
        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));// Should not move again

        assertEquals(initialIndex+1, menuUI.cursorSelection, "Cursor should have moved down");
    }

    @Test
    void testCursorMovementResetsOnKeyRelease() {
        gp.status = GameStatus.MENU;
        MenuUI menuUI = gp.getMenuUI();

        int initialIndex = menuUI.cursorSelection;

        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));//should increase the initialIndex by one
        inputHandler.keyReleased(key(KeyEvent.VK_DOWN));
        inputHandler.keyPressed(key(KeyEvent.VK_DOWN));// Should move again

        assertEquals(initialIndex+2, menuUI.cursorSelection, "Cursor should have moved down twice");
    }
}

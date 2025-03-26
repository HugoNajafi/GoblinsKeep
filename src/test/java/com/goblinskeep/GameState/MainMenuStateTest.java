package com.goblinskeep.GameState;

import com.goblinskeep.UI.MenuUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying game state transitions triggered by the Main Menu.
 */
public class MainMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private MenuUI mUI;

    /**
     * Initializes GamePanel, InputHandler and MenuUI with default MENU status.
     */
    private void setup() {
        gp = new GamePanel(); // Default is GameStatus.MENU
        inputHandler = new MenuInputHandler(gp);
        mUI = new MenuUI(gp);
    }

    @Test
    void gameStartsWhenPLAYOptionSelected() {
        setup();
        mUI.cursorSelection = 0; // Options.RESTART

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void instructionsPageShowsWhenINSTRUCTIONSOptionSelected() {
        setup();
        mUI.cursorSelection = 1; // Options.INSTRUCTIONS

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.INSTRUCTIONS, gp.status);
    }
}
package com.goblinskeep.GameState;

import com.goblinskeep.UI.MenuUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
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
     * Sets up the GamePanel, MenuUI, and input handler before each test.
     * The initial game status is assumed to be GameStatus.MENU
     */
    @BeforeEach
    public void setup() {
        gp = new GamePanel(); // Default is GameStatus.MENU
        inputHandler = new MenuInputHandler(gp);
        mUI = new MenuUI(gp);
    }

    /**
     * Simulates selecting the "PLAY" option (cursor index 0) from the main menu.
     * Verifies that the game status changes to GameStatus.RESTART
     */
    @Test
    void gameStartsWhenPLAYOptionSelected() {
        mUI.cursorSelection = 0; // Options.RESTART

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status,
                "Expected game status to be RESTART after selecting PLAY from main menu");
    }

    /**
     * Simulates selecting the "INSTRUCTIONS" option (cursor index 1) from the main menu.
     * Verifies that the game status changes to GameStatus.INSTRUCTIONS
     */
    @Test
    void instructionsPageShowsWhenINSTRUCTIONSOptionSelected() {
        mUI.cursorSelection = 1; // Options.INSTRUCTIONS

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.INSTRUCTIONS, gp.status,
                "Expected game status to be INSTRUCTIONS after selecting INSTRUCTIONS from main menu");
    }
}
package com.goblinskeep.GameState;

import com.goblinskeep.UI.PauseUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying game state transitions triggered by the Pause Menu.
 */
public class PauseMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private PauseUI pUI;

    /**
     /**
     * Initializes the game in a PAUSED state and prepares
     * the input handler and Pause Menu UI before each test.
     */
    @BeforeEach
    public void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.PAUSED;
        inputHandler = new MenuInputHandler(gp);
        pUI = new PauseUI(gp);
    }

    /**
     * Verifies that selecting "Resume" from the Pause Menu
     * transitions the game back to GameStatus.PLAYING
     */
    @Test
    void gameResumesWhenOptionRESUMESelectedAtPauseMenu() {
        pUI.cursorSelection = 0; // Options.RESUME

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.PLAYING, gp.status,
                "Expected game status to be PLAYING after selecting RESUME from pause menu");
    }

    /**
     * Verifies that selecting "Restart" from the Pause Menu
     * sets the game to GameStatus.RESTART
     */
    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtPauseMenu() {
        pUI.cursorSelection = 1; // Options.RESTART

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status,
                "Expected game status to be RESTART after selecting RESTART from pause menu");
    }

    /**
     * Verifies that selecting "Back to Menu" from the Pause Menu
     * sets the game to GameStatus.MENU
     */
    @Test
    void backToMainMenuWhenBACKTOMENUSelectedAtPauseMenu() {
        pUI.cursorSelection = 2; // Options.MENU

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status,
                "Expected game status to be MENU after selecting BACK TO MENU from pause menu");
    }
}
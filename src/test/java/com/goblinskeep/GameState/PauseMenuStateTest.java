package com.goblinskeep.GameState;

import com.goblinskeep.UI.PauseUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

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
     * Initializes GamePanel, InputHandler and PauseUI with PAUSED game status.
     */
    private void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.PAUSED;
        inputHandler = new MenuInputHandler(gp);
        pUI = new PauseUI(gp);
    }

    @Test
    void gameResumesWhenOptionRESUMESelectedAtPauseMenu() {
        setup();
        pUI.cursorSelection = 0; // Options.RESUME

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtPauseMenu() {
        setup();
        pUI.cursorSelection = 1; // Options.RESTART

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void backToMainMenuWhenBACKTOMENUSelectedAtPauseMenu() {
        setup();
        pUI.cursorSelection = 2; // Options.MENU

        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status);
    }
}
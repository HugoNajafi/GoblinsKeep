package com.goblinskeep.GameState;

import com.goblinskeep.UI.EndUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying game state transitions triggered by the End Menu.
 */
public class EndMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private EndUI eUI;

    /**
     * Initializes GamePanel, InputHandler and EndUI with END game status.
     */
    private void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.END;
        inputHandler = new MenuInputHandler(gp);
        eUI = new EndUI(gp);
    }

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtEndMenu() {
        setup();
        eUI.cursorSelection = 0; // Options.RESTART

        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void backToMainMenuWhenBACKTOMENUOptionSelectedAtEndMenu() {
        setup();
        eUI.cursorSelection = 1; // Options.MENU

        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status);
    }
}

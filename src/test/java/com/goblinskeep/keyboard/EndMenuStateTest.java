package com.goblinskeep.GameState;

import com.goblinskeep.UI.EndUI;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
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
     * Sets the game state to END and initializes the End Menu and Input Handler.
     */
    @BeforeEach
    public void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.END;
        inputHandler = new MenuInputHandler(gp);
        eUI = new EndUI(gp);
    }

    /**
     * Verifies that selecting "Restart" from the End Menu
     * sets the game state to GameStatus.RESTART
     */
    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtEndMenu() {
        eUI.cursorSelection = 0; // Options.RESTART

        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status,
                "Expected game status to be RESTART after selecting RESTART from end menu");
    }

    /**
     * Verifies that selecting "Back to Menu" from the End Menu
     * sets the game state to GameStatus.MENU
     */
    @Test
    void backToMainMenuWhenBACKTOMENUOptionSelectedAtEndMenu() {
        eUI.cursorSelection = 1; // Options.MENU

        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status,
                "Expected game status to be MENU after selecting BACK TO MENU from end menu");
    }
}

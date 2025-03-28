package com.goblinskeep.keyboard;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for verifying game state transitions triggered by the End Menu.
 */
public class EndMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private EndUI eUI;

    @BeforeEach
    public void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.END;
        inputHandler = new MenuInputHandler(gp);

        eUI = mock(EndUI.class);
    }

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtEndMenu() {
        when(eUI.getCurrentOption()).thenReturn(Options.RESTART);
        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void backToMainMenuWhenBACKTOMENUOptionSelectedAtEndMenu() {
        when(eUI.getCurrentOption()).thenReturn(Options.MENU);
        inputHandler.handleMenuKeyEvent(eUI, GameStatus.END, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.MENU, gp.status);
    }
}
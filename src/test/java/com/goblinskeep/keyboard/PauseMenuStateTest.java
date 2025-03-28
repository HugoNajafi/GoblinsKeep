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
 * Unit tests for verifying game state transitions triggered by the Pause Menu.
 */
public class PauseMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private PauseUI pUI;

    @BeforeEach
    public void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.PAUSED;
        inputHandler = new MenuInputHandler(gp);

        pUI = mock(PauseUI.class);
    }

    @Test
    void gameResumesWhenOptionRESUMESelectedAtPauseMenu() {
        when(pUI.getCurrentOption()).thenReturn(Options.RESUME);
        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtPauseMenu() {
        when(pUI.getCurrentOption()).thenReturn(Options.RESTART);
        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void backToMainMenuWhenBACKTOMENUSelectedAtPauseMenu() {
        when(pUI.getCurrentOption()).thenReturn(Options.MENU);
        inputHandler.handleMenuKeyEvent(pUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.MENU, gp.status);
    }
}
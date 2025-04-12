package com.goblinskeep.keyboard;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.UI.Options;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for menu UI state transitions handled by {@link MenuInputHandler}.
 */
public class MenuUIStateTransitionTest {

    /** initialize GamePanel */
    private GamePanel gp;
    /** initialize MenuInputHandler */
    private MenuInputHandler inputHandler;

    /**
     * Sets up the test environment by initializing the GamePanel and MenuInputHandler.
     */
    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        inputHandler = new MenuInputHandler(gp);
    }

    /**
     * Tests state transitions from the main menu.
     */
    @Test
    void mainMenuTransitions() {
        DefaultUI menuUI = mock(DefaultUI.class);
        gp.status = GameStatus.MENU;

        when(menuUI.getCurrentOption()).thenReturn(Options.RESTART);
        inputHandler.handleMenuKeyEvent(menuUI, GameStatus.MENU, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.RESTART, gp.status);

        gp.status = GameStatus.MENU;
        when(menuUI.getCurrentOption()).thenReturn(Options.INSTRUCTIONS);
        inputHandler.handleMenuKeyEvent(menuUI, GameStatus.MENU, KeyEvent.VK_SPACE);
        assertEquals(GameStatus.INSTRUCTIONS, gp.status);
    }

    /**
     * Tests state transitions from the pause menu.
     */
    @Test
    void pauseMenuTransitions() {
        DefaultUI pauseUI = mock(DefaultUI.class);
        gp.status = GameStatus.PAUSED;

        when(pauseUI.getCurrentOption()).thenReturn(Options.RESUME);
        inputHandler.handleMenuKeyEvent(pauseUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.PLAYING, gp.status);

        gp.status = GameStatus.PAUSED;
        when(pauseUI.getCurrentOption()).thenReturn(Options.RESTART);
        inputHandler.handleMenuKeyEvent(pauseUI, GameStatus.PAUSED, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.RESTART, gp.status);

        gp.status = GameStatus.PAUSED;
        when(pauseUI.getCurrentOption()).thenReturn(Options.MENU);
        inputHandler.handleMenuKeyEvent(pauseUI, GameStatus.PAUSED, KeyEvent.VK_SPACE);
        assertEquals(GameStatus.MENU, gp.status);
    }

    /**
     * Tests state transitions from the end menu.
     */
    @Test
    void endMenuTransitions() {
        DefaultUI endUI = mock(DefaultUI.class);
        gp.status = GameStatus.END;

        when(endUI.getCurrentOption()).thenReturn(Options.RESTART);
        inputHandler.handleMenuKeyEvent(endUI, GameStatus.END, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.RESTART, gp.status);

        gp.status = GameStatus.END;
        when(endUI.getCurrentOption()).thenReturn(Options.MENU);
        inputHandler.handleMenuKeyEvent(endUI, GameStatus.END, KeyEvent.VK_ENTER);
        assertEquals(GameStatus.MENU, gp.status);
    }
}

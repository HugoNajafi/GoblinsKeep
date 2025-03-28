package com.goblinskeep.keyboard;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainMenuStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;
    private MenuUI mUI;

    @BeforeEach
    public void setup() {
        gp = new GamePanel();
        gp.status = GameStatus.MENU;
        inputHandler = new MenuInputHandler(gp);

        mUI = mock(MenuUI.class);
    }

    @Test
    void gameStartsWhenPLAYOptionSelected() {
        when(mUI.getCurrentOption()).thenReturn(Options.RESTART);

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void instructionsPageShowsWhenINSTRUCTIONSOptionSelected() {
        when(mUI.getCurrentOption()).thenReturn(Options.INSTRUCTIONS);

        inputHandler.handleMenuKeyEvent(mUI, GameStatus.MENU, KeyEvent.VK_ENTER);

        assertEquals(GameStatus.INSTRUCTIONS, gp.status);
    }
}

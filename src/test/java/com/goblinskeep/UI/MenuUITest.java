package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuUITest {

    private GamePanel mockGP;
    private MenuUI menuUI;

    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        menuUI = new MenuUI(mockGP);
    }

    @Test
    void getCurrentOptionReturnsRestartWhenCursorAt0() {
        menuUI.cursorSelection = 0;
        assertEquals(Options.RESTART, menuUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsInstructionsWhenCursorAt1() {
        menuUI.cursorSelection = 1;
        assertEquals(Options.INSTRUCTIONS, menuUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsQuitWhenCursorAt2() {
        menuUI.cursorSelection = 2;
        assertEquals(Options.QUIT, menuUI.getCurrentOption());
    }
}

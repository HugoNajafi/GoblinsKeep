package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndUITest {

    private GamePanel mockGP;
    private EndUI endUI;

    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        endUI = new EndUI(mockGP);
    }

    @Test
    void getCurrentOptionReturnsRestartWhenCursorAt0() {
        endUI.cursorSelection = 0;
        assertEquals(Options.RESTART, endUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsMenuWhenCursorAt1() {
        endUI.cursorSelection = 1;
        assertEquals(Options.MENU, endUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsQuitWhenCursorAt2() {
        endUI.cursorSelection = 2;
        assertEquals(Options.QUIT, endUI.getCurrentOption());
    }
}

package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PauseUITest {

    private GamePanel mockGP;
    private PauseUI pauseUI;

    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        pauseUI = new PauseUI(mockGP);
    }

    @Test
    void getCurrentOptionReturnsResumeWhenCursorAt0() {
        pauseUI.cursorSelection = 0;
        assertEquals(Options.RESUME, pauseUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsRestartWhenCursorAt1() {
        pauseUI.cursorSelection = 1;
        assertEquals(Options.RESTART, pauseUI.getCurrentOption());
    }

    @Test
    void getCurrentOptionReturnsMenuWhenCursorAt2() {
        pauseUI.cursorSelection = 2;
        assertEquals(Options.MENU, pauseUI.getCurrentOption());
    }
}

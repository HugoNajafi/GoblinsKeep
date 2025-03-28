package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionsUITest {

    private GamePanel mockGP;
    private InstructionsUI instructionsUI;

    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        instructionsUI = new InstructionsUI(mockGP);
    }

    @Test
    void getCurrentOptionReturnsMenuWhenCursorAt0() {
        instructionsUI.cursorSelection = 0;
        assertEquals(Options.MENU, instructionsUI.getCurrentOption());
    }
}

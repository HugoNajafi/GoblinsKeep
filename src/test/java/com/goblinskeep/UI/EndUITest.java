package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EndUI class.
 */
public class EndUITest {

    private GamePanel mockGP;
    private EndUI endUI;

    /**
     * Sets up the test environment by initializing the GamePanel and EndUI instances.
     */
    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        endUI = new EndUI(mockGP);
    }

    /**
     * Tests that getCurrentOption() returns RESTART when the cursor is at position 0.
     */
    @Test
    void getCurrentOptionReturnsRestartWhenCursorAt0() {
        endUI.cursorSelection = 0;
        assertEquals(Options.RESTART, endUI.getCurrentOption());
    }

    /**
     * Tests that getCurrentOption() returns MENU when the cursor is at position 1.
     */
    @Test
    void getCurrentOptionReturnsMenuWhenCursorAt1() {
        endUI.cursorSelection = 1;
        assertEquals(Options.MENU, endUI.getCurrentOption());
    }

    /**
     * Tests that getCurrentOption() returns QUIT when the cursor is at position 2.
     */
    @Test
    void getCurrentOptionReturnsQuitWhenCursorAt2() {
        endUI.cursorSelection = 2;
        assertEquals(Options.QUIT, endUI.getCurrentOption());
    }

    /**
     * Tests the draw method and cursor movement functionality of EndUI.
     */
    @Test
    void drawTest(){
        GamePanel realGP = new GamePanel();

        EndUI ui = new EndUI(realGP);
        BufferedImage testImage = new BufferedImage(realGP.screenWidth, realGP.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = testImage.createGraphics();

        // Test draw
        ui.draw(g2);
        realGP.map.setGameWin();

        for (int i = 0; i < ui.totalSelections; i++) {
            int originalOption = ui.cursorSelection;
            ui.moveCursorDown();
            ui.draw(g2);
            //assert option changed correctly
            assertNotEquals(originalOption, ui.getCurrentOption(),
                    "Option should change when cursor moves");
        }
        g2.dispose();
    }
}

package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MenuUI class.
 */
public class MenuUITest {

    private GamePanel mockGP;
    private MenuUI menuUI;

    /**
     * Sets up the test environment by initializing the GamePanel and MenuUI instances.
     */
    @BeforeEach
    void setup() {
        mockGP = new GamePanel();
        menuUI = new MenuUI(mockGP);
    }

    /**
     * Tests that getCurrentOption() returns RESTART when the cursor is at position 0.
     */
    @Test
    void getCurrentOptionReturnsRestartWhenCursorAt0() {
        menuUI.cursorSelection = 0;
        assertEquals(Options.RESTART, menuUI.getCurrentOption());
    }

    /**
     * Tests that getCurrentOption() returns INSTRUCTIONS when the cursor is at position 1.
     */
    @Test
    void getCurrentOptionReturnsInstructionsWhenCursorAt1() {
        menuUI.cursorSelection = 1;
        assertEquals(Options.INSTRUCTIONS, menuUI.getCurrentOption());
    }

    /**
     * Tests that getCurrentOption() returns QUIT when the cursor is at position 2.
     */
    @Test
    void getCurrentOptionReturnsQuitWhenCursorAt2() {
        menuUI.cursorSelection = 2;
        assertEquals(Options.QUIT, menuUI.getCurrentOption());
    }

    /**
     * Tests the draw method and cursor movement functionality of MenuUI.
     */
    @Test
    void drawTest(){
        GamePanel realGP = new GamePanel();
        MenuUI ui = new MenuUI(realGP);
        BufferedImage testImage = new BufferedImage(
                realGP.screenWidth,
                realGP.screenHeight,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = testImage.createGraphics();

        //test draw
        ui.draw(g2);

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

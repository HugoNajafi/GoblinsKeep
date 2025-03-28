package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

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

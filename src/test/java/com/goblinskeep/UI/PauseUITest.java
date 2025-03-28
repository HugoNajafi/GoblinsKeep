package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    @Test
    void drawTest(){

        GamePanel realGP = new GamePanel();
        PauseUI ui = new PauseUI(realGP);

        BufferedImage testImage = new BufferedImage(
                realGP.screenWidth,
                realGP.screenHeight,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = testImage.createGraphics();

        //testing draw method
        ui.draw(g2);

        //test cursor movements
        for (int i = 0; i < ui.totalSelections; i++) {
            int originalOption = ui.cursorSelection;
            ui.moveCursorDown();
            ui.draw(g2);
            // Assert option changed correctly
            assertNotEquals(originalOption, ui.getCurrentOption(),
                    "Option should change when cursor moves");
        }
        g2.dispose();
    }
}

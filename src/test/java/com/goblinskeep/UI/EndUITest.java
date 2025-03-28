package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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

    @Test
    void drawTest(){
        GamePanel realGP = new GamePanel();

        EndUI ui = new EndUI(realGP);
        BufferedImage testImage = new BufferedImage(realGP.screenWidth, realGP.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = testImage.createGraphics();

        // Test draw
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

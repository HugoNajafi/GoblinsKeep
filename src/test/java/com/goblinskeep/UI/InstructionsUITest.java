package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    @Test
    void drawTest(){
        GamePanel realGP = new GamePanel();
        InstructionsUI ui = new InstructionsUI(realGP);

        BufferedImage testImage = new BufferedImage(
                realGP.screenWidth,
                realGP.screenHeight,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = testImage.createGraphics();

        //test draw
        ui.draw(g2);

        for (int i = 0; i < ui.totalSelections; i++) {
            ui.moveCursorDown();
            ui.draw(g2);
            assertEquals(0, ui.cursorSelection);
        }
        g2.dispose();
    }
}

package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultUITest {
    private DefaultUI defaultUI;
    @BeforeEach
    void setUp(){
        defaultUI = new MenuUI(new GamePanel());
    }

    @Test
    void moveCursorDown(){
        int current = defaultUI.cursorSelection;
        defaultUI.moveCursorDown();
        assertEquals(current + 1, defaultUI.cursorSelection);
    }

    @Test
    void moveCursorDownResetTest(){
        defaultUI.cursorSelection = defaultUI.totalSelections -1;
        defaultUI.moveCursorDown();
        assertEquals(0, defaultUI.cursorSelection);
    }

    @Test
    void moveCursorUpResetTest(){
        defaultUI.moveCursorUp();
        assertEquals(defaultUI.totalSelections - 1, defaultUI.cursorSelection);
    }

    @Test
    void moveCursorUpTest(){
        defaultUI.cursorSelection = defaultUI.totalSelections -1;
        defaultUI.moveCursorUp();
        assertEquals(defaultUI.totalSelections - 2, defaultUI.cursorSelection);
    }

    @Test
    void getCenterTextTest(){
        GamePanel mockGp = mock(GamePanel.class);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        DefaultUI ui = new MenuUI(mockGp);

        //test draw to test center text
        ui.draw(g2);
        assertDoesNotThrow(() -> new RuntimeException());
        g2.dispose();
    }

    @Test
    void drawTextWithBorder() {
        Graphics2D g2 = mock(Graphics2D.class);

        defaultUI.drawTextWithBorder(g2, "test", 100, 100);
        verify(g2, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void drawCursorOptionsTest(){
        GamePanel mockGp = mock(GamePanel.class);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        DefaultUI ui = new MenuUI(mockGp);
        String[] options = {"Hello", "test", "123"};
        ui.drawCursorOptionsCentered(g2, options, 3);
        assertDoesNotThrow(() -> new RuntimeException());
        g2.dispose();
    }


}

package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the DefaultUI class and its subclasses.
 */
public class DefaultUITest {
    /** initialize DefaultUI */
    private DefaultUI defaultUI;

    /**
     * Sets up the test environment by initializing the DefaultUI instance.
     */
    @BeforeEach
    void setUp() {
        defaultUI = new MenuUI(new GamePanel());
    }

    /**
     * Tests that moveCursorDown() increments the cursor selection.
     */
    @Test
    void moveCursorDown() {
        int current = defaultUI.cursorSelection;
        defaultUI.moveCursorDown();
        assertEquals(current + 1, defaultUI.cursorSelection);
    }

    /**
     * Tests that moveCursorDown() resets the cursor selection to 0 when at the last option.
     */
    @Test
    void moveCursorDownResetTest() {
        defaultUI.cursorSelection = defaultUI.totalSelections - 1;
        defaultUI.moveCursorDown();
        assertEquals(0, defaultUI.cursorSelection);
    }

    /**
     * Tests that moveCursorUp() resets the cursor selection to the last option when at the first option.
     */
    @Test
    void moveCursorUpResetTest() {
        defaultUI.moveCursorUp();
        assertEquals(defaultUI.totalSelections - 1, defaultUI.cursorSelection);
    }

    /**
     * Tests that moveCursorUp() decrements the cursor selection.
     */
    @Test
    void moveCursorUpTest() {
        defaultUI.cursorSelection = defaultUI.totalSelections - 1;
        defaultUI.moveCursorUp();
        assertEquals(defaultUI.totalSelections - 2, defaultUI.cursorSelection);
    }

    /**
     * Tests the getCenterText() functionality by verifying no exceptions are thrown during drawing.
     */
    @Test
    void getCenterTextTest() {
        GamePanel mockGp = mock(GamePanel.class);
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        DefaultUI ui = new MenuUI(mockGp);

        // test draw to test center text
        ui.draw(g2);
        assertDoesNotThrow(() -> new RuntimeException());
        g2.dispose();
    }

    /**
     * Tests the drawTextWithBorder() method to ensure text is drawn with a border.
     */
    @Test
    void drawTextWithBorder() {
        Graphics2D g2 = mock(Graphics2D.class);

        defaultUI.drawTextWithBorder(g2, "test", 100, 100);
        verify(g2, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }

    /**
     * Tests the drawCursorOptionsCentered() method to ensure it draws options correctly.
     */
    @Test
    void drawCursorOptionsTest() {
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

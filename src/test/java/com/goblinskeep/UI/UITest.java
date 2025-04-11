package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UI class.
 */
public class UITest {

    /** initialize GamePanel */
    private GamePanel gp;
    /** initialize UI */
    private UI ui;
    /** initialize BufferedImage */
    private BufferedImage testImage;
    /** initialize Graphics2D */
    private Graphics2D g2;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        ui = new UI(gp);
        testImage = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = testImage.createGraphics();
    }

    /**
     * Tests the restart method of the UI class.
     */
    @Test
    void testRestartMethod() {
        ui.messageOn = true;
        ui.messageCounter = 50;
        ui.playTime = 120.5;

        // Call method to test
        ui.restart();

        // Verify all values are reset
        assertFalse(ui.messageOn);
        assertEquals(0, ui.messageCounter);
        assertEquals(0, ui.playTime);
    }

    /**
     * Tests the showMessage method of the UI class.
     */
    @Test
    void testShowMessage() {
        String testMessage = "Test Message";
        ui.showMessage(testMessage);

        assertTrue(ui.messageOn);
        assertEquals(testMessage, ui.message);
    }

    /**
     * Tests the draw method of the UI class when the game is in the playing state.
     */
    @Test
    void testDrawMethodWhenPlaying() {
        // Set game to playing state
        gp.status = GameStatus.PLAYING;

        ui.draw(g2);

        assertEquals(GameStatus.PLAYING, gp.status);
    }

    /**
     * Tests the draw method of the UI class when the game is paused.
     */
    @Test
    void testDrawMethodWhenPaused() {
        gp.status = GameStatus.PAUSED;

        // Should not throw exceptions
        ui.draw(g2);

        // Verify we're in expected state after draw
        assertEquals(GameStatus.PAUSED, gp.status);
    }

    /**
     * Tests the message timer expiration in the UI class.
     */
    @Test
    void testMessageTimerExpires() {
        ui.showMessage("Test Message");
        ui.messageCounter = 119;

        gp.status = GameStatus.PLAYING;
        ui.draw(g2);

        //message display
        assertTrue(ui.messageOn);
        ui.draw(g2);
        //message should not be displayed
        assertFalse(ui.messageOn);
        assertEquals(0, ui.messageCounter);
    }

    /**
     * Tests that play time increases when the game is in the playing state.
     */
    @Test
    void testPlayTimeIncreasesWhenPlaying() {
        gp.status = GameStatus.PLAYING;
        ui.playTime = 10.0;

        ui.draw(g2);

        assertTrue(ui.playTime > 10.0);
    }

    /**
     * Tests the draw method of the UI class when the game is neither paused nor playing.
     */
    @Test
    void drawWhenNeitherPausedOrPlay() {
        // Set initial values
        gp.status = GameStatus.END;
        ui.playTime = 10.0;

        ui.draw(g2);
        assertEquals(10.0, ui.playTime);
    }

    /**
     * Tests that getCurrentOption method returns null.
     */
    @Test
    void testGetCurrentOptionReturnsNull() {
        assertNull(ui.getCurrentOption());
    }

    /**
     * Tests that the pause UI is initialized.
     */
    @Test
    void testPauseUiIsInitialized() {
        assertNotNull(ui.pauseUI);
    }

    /**
     * Tests the draw method of the UI class when a message is being displayed.
     */
    @Test
    void testDrawingWithMessage() {
        ui.showMessage("Test Message");
        gp.status = GameStatus.PLAYING;

        ui.draw(g2);

        // message counter should be incremented
        assertEquals(1, ui.messageCounter);
    }
}

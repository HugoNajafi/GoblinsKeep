package com.goblinskeep.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GamePanel class.
 */
public class GamePanelTest {

    /** initialize GamePanel */
    private GamePanel gamePanel;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
    }

    /**
     * Tests the initialization of the GamePanel.
     */
    @Test
    void testGamePanelInitialization() {
        assertEquals(GameStatus.MENU, gamePanel.status);
        assertNotNull(gamePanel.Player);
        assertNotNull(gamePanel.tileM);
        assertNotNull(gamePanel.obj);
        assertNotNull(gamePanel.pathFinder);
    }

    /**
     * Tests the startGameThread method.
     */
    @Test
    void testStartGameThread() {
        gamePanel.startGameThread();
        assertNotNull(gamePanel.gameThread);
        assertTrue(gamePanel.gameThread.isAlive());
    }

    /**
     * Tests the update method when the game status is PLAYING.
     */
    @Test
    void testUpdatePlayingStatus() {
        gamePanel.status = GameStatus.PLAYING;
        gamePanel.update();

        assertFalse(gamePanel.map.gameEnded());
        assertEquals(GameStatus.PLAYING, gamePanel.status);
    }

    /**
     * Tests the update method when the game ends.
     */
    @Test
    void testUpdateGameEndCondition() {
        gamePanel.status = GameStatus.PLAYING;
        gamePanel.map.setGameEnded(true); // Assume you can set this in your MapHandler
        gamePanel.update();

        assertEquals(GameStatus.END, gamePanel.status);
    }

    /**
     * Tests the update method when the game is paused.
     */
    @Test
    void testUpdatePausedStatus() {
        gamePanel.status = GameStatus.PAUSED;
        gamePanel.update();

        assertEquals(GameStatus.PAUSED, gamePanel.status);
    }

    /**
     * Tests the update method when the game is restarted.
     */
    @Test
    void testUpdateRestartStatus() {
        gamePanel.status = GameStatus.RESTART;
        gamePanel.update();

        assertEquals(GameStatus.PLAYING, gamePanel.status);
        assertNotNull(gamePanel.Player);
    }

    /**
     * Tests the paintComponent method in different game states.
     */
    @Test
    void testPaintComponent() {
        BufferedImage testImage = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight,
                BufferedImage.TYPE_INT_ARGB);
        //create a mock graphics objects with similar attributes
        Graphics2D g2 = testImage.createGraphics();

        // Test the paintComponent method in different game states
        gamePanel.status = GameStatus.PLAYING;
        assertDoesNotThrow(() -> gamePanel.paintComponent(g2));

        gamePanel.status = GameStatus.PAUSED;
        assertDoesNotThrow(() -> gamePanel.paintComponent(g2));

        gamePanel.status = GameStatus.MENU;
        assertDoesNotThrow(() -> gamePanel.paintComponent(g2));

        gamePanel.status = GameStatus.END;
        assertDoesNotThrow(() -> gamePanel.paintComponent(g2));

        gamePanel.status = GameStatus.INSTRUCTIONS;
        assertDoesNotThrow(() -> gamePanel.paintComponent(g2));
    }

    /**
     * Tests the restartGame method.
     */
    @Test
    void testRestartGame() {
        gamePanel.restartGame();
        assertNotNull(gamePanel.Player);
        assertEquals(5, gamePanel.Player.speed);
    }

    /**
     * Tests the getGoblinIterator method.
     */
    @Test
    void testGetGoblinIterator() {
        assertNotNull(gamePanel.getGoblinIterator());
        assertTrue(gamePanel.getGoblinIterator().hasNext());
    }

    /**
     * Tests the getMenuUI method.
     */
    @Test
    void testGetMenuUI() {
        assertNotNull(gamePanel.getMenuUI());
    }

}

package com.goblinskeep.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanelTest {

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
    }

    @Test
    void testGamePanelInitialization() {
        assertEquals(GameStatus.MENU, gamePanel.status);
        assertNotNull(gamePanel.Player);
        assertNotNull(gamePanel.tileM);
        assertNotNull(gamePanel.obj);
        assertNotNull(gamePanel.pathFinder);
    }

    @Test
    void testStartGameThread() {
        gamePanel.startGameThread();
        assertNotNull(gamePanel.gameThread);
        assertTrue(gamePanel.gameThread.isAlive());
    }

    @Test
    void testUpdatePlayingStatus() {
        gamePanel.status = GameStatus.PLAYING;
        gamePanel.update();

        assertFalse(gamePanel.map.gameEnded());
        assertEquals(GameStatus.PLAYING, gamePanel.status);
    }

    @Test
    void testUpdateGameEndCondition() {
        gamePanel.status = GameStatus.PLAYING;
        gamePanel.map.setGameEnded(true); // Assume you can set this in your MapGenerator
        gamePanel.update();

        assertEquals(GameStatus.END, gamePanel.status);
    }

    @Test
    void testUpdatePausedStatus() {
        gamePanel.status = GameStatus.PAUSED;
        gamePanel.update();

        assertEquals(GameStatus.PAUSED, gamePanel.status);
    }

    @Test
    void testUpdateRestartStatus() {
        gamePanel.status = GameStatus.RESTART;
        gamePanel.update();

        assertEquals(GameStatus.PLAYING, gamePanel.status);
        assertNotNull(gamePanel.Player);
    }

//    @Test
//    void testPaintComponentMenuStatus() {
//        gamePanel.status = GameStatus.MENU;
//        assertDoesNotThrow(() -> gamePanel.paintComponent(gamePanel.getGraphics()));
//    }
//
//    @Test
//    void testPaintComponentPlayingStatus() {
//        gamePanel.status = GameStatus.PLAYING;
//        assertDoesNotThrow(() -> gamePanel.paintComponent(gamePanel.getGraphics()));
//    }
//
//    @Test
//    void testPaintComponentEndStatus() {
//        gamePanel.status = GameStatus.END;
//        assertDoesNotThrow(() -> gamePanel.paintComponent(gamePanel.getGraphics()));
//    }
//
//    @Test
//    void testPaintComponentInstructionsStatus() {
//        gamePanel.status = GameStatus.INSTRUCTIONS;
//        assertDoesNotThrow(() -> gamePanel.paintComponent(gamePanel.getGraphics()));
//    }

    @Test
    void testRestartGame() {
        gamePanel.restartGame();
        assertNotNull(gamePanel.Player);
        assertEquals(5, gamePanel.Player.speed);
    }

    @Test
    void testGetGoblinIterator() {
        assertNotNull(gamePanel.getGoblinIterator());
        assertTrue(gamePanel.getGoblinIterator().hasNext());
    }

    @Test
    void testGetMenuUI() {
        assertNotNull(gamePanel.getMenuUI());
    }

}

package com.goblinskeep.GameState;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying key-based toggling between PLAYING and PAUSED states.
 */
public class PlayingStateTest {

    private GamePanel gp;
    private MenuInputHandler inputHandler;

    /**
     * Initializes GamePanel and InputHandler with the given status.
     */
    private void setup(GameStatus initialStatus) {
        gp = new GamePanel();
        gp.status = initialStatus;
        inputHandler = new MenuInputHandler(gp);
    }

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    @Test
    void statusPausedWhenEscPressed() {
        setup(GameStatus.PLAYING);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PAUSED, gp.status);
    }

    @Test
    void statusPausedWhenPPressed() {
        setup(GameStatus.PLAYING);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_P));
        assertEquals(GameStatus.PAUSED, gp.status);
    }

    @Test
    void statusPlayingWhenEscPressedAfterPause() {
        setup(GameStatus.PAUSED);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void statusPlayingWhenPPressedAfterPause() {
        setup(GameStatus.PAUSED);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_P));
        assertEquals(GameStatus.PLAYING, gp.status);
    }
}
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
     * Sets the initial game state and initializes the input handler.
     *
     * @param initialStatus The starting {@link GameStatus} (PLAYING or PAUSED)
     */
    private void setup(GameStatus initialStatus) {
        gp = new GamePanel();
        gp.status = initialStatus;
        inputHandler = new MenuInputHandler(gp);
    }

    /**
     * Creates a synthetic KeyEvent for simulation purposes.
     */
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

    /**
     * Verifies that pressing Esc during PLAYING pauses the game.
     */
    @Test
    void statusPausedWhenEscPressed() {
        setup(GameStatus.PLAYING);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PAUSED, gp.status,
                "Expected game status to be PAUSED when Esc is pressed during PLAYING");
    }

    /**
     * Verifies that pressing P during PLAYING pauses the game.
     */
    @Test
    void statusPausedWhenPPressed() {
        setup(GameStatus.PLAYING);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_P));
        assertEquals(GameStatus.PAUSED, gp.status,
                "Expected game status to be PAUSED when P is pressed during PLAYING");
    }

    /**
     * Verifies that pressing Esc during PAUSED resumes gameplay.
     */
    @Test
    void statusPlayingWhenEscPressedAfterPause() {
        setup(GameStatus.PAUSED);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_ESCAPE));
        assertEquals(GameStatus.PLAYING, gp.status,
                "Expected game status to return to PLAYING when Esc is pressed during PAUSED");
    }

    /**
     * Verifies that pressing P during PAUSED resumes gameplay.
     */
    @Test
    void statusPlayingWhenPPressedAfterPause() {
        setup(GameStatus.PAUSED);
        inputHandler.keyPressed(createKeyEvent(KeyEvent.VK_P));
        assertEquals(GameStatus.PLAYING, gp.status,
                "Expected game status to return to PLAYING when P is pressed during PAUSED");
    }
}
package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Bonus class.
 */
class BonusTest {
    private Bonus bonus;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        bonus = new Bonus(1, 1);
    }

    /**
     * Tests the constructor of the Bonus class.
     */
    @Test
    void testConstructor() {
        Bonus bonus = new Bonus(1, 1);
        // Check that the initial fields are correct
        assertEquals("bonus", bonus.name);
        assertNotNull(bonus.image);
        assertFalse(bonus.collision);
    }

    /**
     * Tests the updateState(int) method when the bonus is alive.
     */
    @Test
    void testUpdateStateWhenAlive() {
        bonus.updateState(25);
        assertTrue(bonus.isAlive(25));
    }

    /**
     * Tests the updateState(int) method when the bonus is not alive.
     */
    @Test
    void testUpdateStateWhenNotAlive() {
        bonus.updateState(35);
        assertFalse(bonus.isAlive(35));
    }

    /**
     * Tests the isAlive(int) method when the bonus is not alive.
     */
    @Test
    void testIsAliveWhenNotAlive() {
        assertFalse(bonus.isAlive(45));
        assertFalse(bonus.isAlive(0));
    }

    /**
     * Tests the isAlive(int) method when the bonus is alive.
     */
    @Test
    void testIsAliveWhenAlive() {
        assertTrue(bonus.isAlive(25));
    }

    /**
     * Tests the draw(Graphics2D, GamePanel) method when the bonus is active.
     */
    @Test
    void testDrawWhenActive() {
        // Set the bonus to be alive
        bonus.updateState(25);
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();

        // Call draw method and verify draw is called
        bonus.worldX = gp.Player.WorldX;
        bonus.worldY = gp.Player.WorldY;
        bonus.draw(g2, gp);
        verify(g2, atLeastOnce()).drawImage(any(BufferedImage.class), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }

    /**
     * Tests the draw(Graphics2D, GamePanel) method when the bonus is not active.
     */
    @Test
    void testDrawWhenNotActive() {
        // Set the bonus to be alive
        bonus.updateState(35);
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();

        // Call draw method and verify it does not draw on the screen
        bonus.draw(g2, gp);
        verifyNoInteractions(g2);
    }
}

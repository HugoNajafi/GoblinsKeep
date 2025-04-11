package com.goblinskeep.tile;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TileManager class.
 */
class TileManagerTest {
    /** initialize TileManager */
    private TileManager tm;
    /** initialize GamePanel */
    private GamePanel gp;

    /**
     * Sets up the test instances before each test.
     */
    @BeforeEach
    void setUp(){
        gp = new GamePanel();
        tm = new TileManager(gp);
    }

    /**
     * Tests the checkCollisionOfTile method.
     */
    @Test
    void checkCollisionOfTileTest(){
        assertFalse(tm.checkCollisionOfTile(0));
        assertTrue(tm.checkCollisionOfTile(1));
    }

    /**
     * Tests the draw method.
     */
    @Test
    void drawTest(){
        Graphics2D g2 = mock(Graphics2D.class);
        tm.draw(g2);
        verify(g2, atLeastOnce()).drawImage(any(BufferedImage.class), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }
}

package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

/**
 * Test class for MainObject.
 */
class MainObjectTest {
    private MainObject object;
    private GamePanel gp;
    private Player player;
    private Graphics2D g2;

    @BeforeEach
    void setUp() {
        object = new Key();
        gp = new GamePanel();
        player = gp.Player;
        g2 = mock(Graphics2D.class);
    }

    /**
     * Test drawing the object when it is near the player.
     */
    @Test
    void testDrawForObject() {
        player.WorldX = 100;
        player.WorldY = 100;
        object.worldX = 120;
        object.worldY = 120;

        object.draw(g2, gp);
    }

    /**
     * Test drawing the object when it is not near the player.
     */
    @Test
    void testDrawForObjectNotNearPlayer() {
        object.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Test drawing the object when it is too far to the left of the player.
     */
    @Test
    public void testDrawObjectTooFarLeft() {
        player.WorldX = 440;
        player.WorldY = 440;
        object.worldX = 10;
        object.worldY = 410;

        object.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Test drawing the object when it is too far to the right of the player.
     */
    @Test
    public void testDrawObjectTooFarRight() {
        player.WorldX = 400;
        player.WorldY = 400;
        object.worldX = 900;
        object.worldY = 410;

        object.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Test drawing the object when it is too far above the player.
     */
    @Test
    public void testDrawObjectTooFarUp() {
        player.WorldX = 400;
        player.WorldY = 400;
        object.worldX = 400;
        object.worldY = 10;

        object.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Test drawing the object when it is too far below the player.
     */
    @Test
    public void testDrawObjectTooFarDown() {
        player.WorldX = 400;
        player.WorldY = 400;
        object.worldX = 400;
        object.worldY = 800;

        object.draw(g2, gp);
        verifyNoInteractions(g2);
    }
}

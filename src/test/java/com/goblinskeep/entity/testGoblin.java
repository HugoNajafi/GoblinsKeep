package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;

public class testGoblin {
    // Mocked dependencies
    private GamePanel gp;
    private Goblin goblin;

    @BeforeEach
    void setUp(){
        gp = new GamePanel();
        goblin = gp.getGoblinIterator().next();

    }

    @Test
    public void testGetSpriteForDirection() {
        // Mock the goblin's sprites
        BufferedImage mockUp1 = mock(BufferedImage.class);
        BufferedImage mockUp2 = mock(BufferedImage.class);
        BufferedImage mockDown1 = mock(BufferedImage.class);
        BufferedImage mockDown2 = mock(BufferedImage.class);
        BufferedImage mockLeft1 = mock(BufferedImage.class);
        BufferedImage mockLeft2 = mock(BufferedImage.class);
        BufferedImage mockRight1 = mock(BufferedImage.class);
        BufferedImage mockRight2 = mock(BufferedImage.class);

        // Assign the mocked sprites to the goblin
        assignMockSprites(mockUp1, mockUp2, mockDown1, mockDown2, mockLeft1, mockLeft2, mockRight1, mockRight2);

        // Test all directions
        testDirectionSprites(Direction.UP, mockUp1, mockUp2);
        testDirectionSprites(Direction.DOWN, mockDown1, mockDown2);
        testDirectionSprites(Direction.LEFT, mockLeft1, mockLeft2);
        testDirectionSprites(Direction.RIGHT, mockRight1, mockRight2);
    }

    private void assignMockSprites(BufferedImage up1, BufferedImage up2, BufferedImage down1, BufferedImage down2,
                                   BufferedImage left1, BufferedImage left2, BufferedImage right1, BufferedImage right2) {
        goblin.up1 = up1;
        goblin.up2 = up2;
        goblin.down1 = down1;
        goblin.down2 = down2;
        goblin.left1 = left1;
        goblin.left2 = left2;
        goblin.right1 = right1;
        goblin.right2 = right2;
    }

    private void testDirectionSprites(Direction direction, BufferedImage sprite1, BufferedImage sprite2) {
        goblin.drawDirection = direction;

        // Test SpriteNum = 1
        goblin.SpriteNum = 1;
        assertEquals(sprite1, goblin.getSpriteForDirection(),
                "Expected sprite1 for direction " + direction + " and SpriteNum = 1");

        // Test SpriteNum = 2
        goblin.SpriteNum = 2;
        assertEquals(sprite2, goblin.getSpriteForDirection(),
                "Expected sprite2 for direction " + direction + " and SpriteNum = 2");
    }

    @Test
    public void testupdate(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        goblin.WorldX = 12;
        goblin.WorldY = 0;
        goblin.update();
        assertTrue(goblin.onPath);
        assertTrue(goblin.inSight);
    }

    @Test
    void testDrawForGoblin(){
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.draw(mock(Graphics2D.class));
        Player player = gp.Player;
        player.WorldX = 100;
        player.WorldY = 100;
        goblin.WorldX = 120;
        goblin.WorldY = 120;
        Graphics2D g2 = mock(Graphics2D.class);
        goblin.draw(g2);
        verify(g2, atLeastOnce()).drawImage(any(BufferedImage.class), anyInt(), anyInt(), anyInt(), anyInt(), any());
    }

    /**
     * Tests the draw method of goblin when the goblin is not near the player.
     */
    @Test
    void testDrawForGoblinNotNearPlayer() {
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        Graphics2D g2 = mock(Graphics2D.class);
        goblin.draw(g2);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of goblin when the goblin is too far to the left of the player.
     */
    @Test
    public void testDrawTooFarLeft() {
        Graphics2D g2 = mock(Graphics2D.class);

        gp.Player.WorldX = 440;
        gp.Player.WorldY = 440;

        // goblin object too far left
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.WorldX = 10;  // Left of visible area
        goblin.WorldY = 410;
        goblin.draw(g2);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of goblin when the goblin is too far to the right of the player.
     */
    @Test
    public void testDrawTooFarRight() {
        Graphics2D g2 = mock(Graphics2D.class);

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // goblin object too far right
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.WorldX = 900;  // Right of visible area
        goblin.WorldY = 410;
        goblin.draw(g2);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of goblin when the goblin is too far up from the player.
     */
    @Test
    public void testDrawTooFarUp() {
        Graphics2D g2 = mock(Graphics2D.class);

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // goblin object too far up
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.WorldX = 400;  // Above visible area
        goblin.WorldY = 10;
        goblin.draw(g2);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of goblin when the goblin is too far down from the player.
     */
    @Test
    public void testDrawTooFarDown() {
        Graphics2D g2 = mock(Graphics2D.class);

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // goblin object too far down
        Goblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.WorldX = 400;  // Below visible area
        goblin.WorldY = 800;
        goblin.draw(g2);
        verifyNoInteractions(g2);
    }

    
}

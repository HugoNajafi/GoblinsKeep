package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Unit tests for the Tree class.
 */
class TreeTest {

    /**
     * Tests the constructor of Tree with type 1.
     */
    @Test
    void testConstructorForTree1() {
        Tree tree = new Tree(1);
        // Check that the initial fields are correct
        assertEquals("tree", tree.name);
        assertNotNull(tree.image);
        assertFalse(tree.collision);
    }

    /**
     * Tests the constructor of Tree with type 2.
     */
    @Test
    void testConstructorForTree2() {
        Tree tree = new Tree(2);
        // Check that the initial fields are correct
        assertEquals("tree", tree.name);
        assertNotNull(tree.image);
        assertFalse(tree.collision);
    }

    /**
     * Tests the draw method of Tree when the tree is near the player.
     */
    @Test
    void testDrawForTree(){
        Tree tree = new Tree(1);
        GamePanel gp = new GamePanel();
        tree.draw(mock(Graphics2D.class), gp);
        Player player = gp.Player;
        player.WorldX = 100;
        player.WorldY = 100;
        tree.worldX = 120;
        tree.worldY = 120;

        tree.draw(mock(Graphics2D.class), gp);
    }

    /**
     * Tests the draw method of Tree when the tree is not near the player.
     */
    @Test
    void testDrawForTreeNotNearPlayer() {
        Tree tree = new Tree(1);
        GamePanel gp = new GamePanel();
        Graphics2D g2 = mock(Graphics2D.class);
        tree.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of Tree when the tree is too far to the left of the player.
     */
    @Test
    public void testDrawTooFarLeft() {
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();
        
        gp.Player.WorldX = 440;
        gp.Player.WorldY = 440;

        // Tree object too far left
        Tree tree = new Tree(1);
        tree.worldX = 10;  // Left of visible area
        tree.worldY = 410;
        tree.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of Tree when the tree is too far to the right of the player.
     */
    @Test
    public void testDrawTooFarRight() {
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // Tree object too far right
        Tree tree = new Tree(1);
        tree.worldX = 900;  // Right of visible area
        tree.worldY = 410;
        tree.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of Tree when the tree is too far up from the player.
     */
    @Test
    public void testDrawTooFarUp() {
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // Tree object too far up
        Tree tree = new Tree(1);
        tree.worldX = 400;  // Above visible area
        tree.worldY = 10;
        tree.draw(g2, gp);
        verifyNoInteractions(g2);
    }

    /**
     * Tests the draw method of Tree when the tree is too far down from the player.
     */
    @Test
    public void testDrawTooFarDown() {
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = new GamePanel();

        gp.Player.WorldX = 400;
        gp.Player.WorldY = 400;

        // Tree object too far down
        Tree tree = new Tree(1);
        tree.worldX = 400;  // Below visible area
        tree.worldY = 800;
        tree.draw(g2, gp);
        verifyNoInteractions(g2);
    }
}

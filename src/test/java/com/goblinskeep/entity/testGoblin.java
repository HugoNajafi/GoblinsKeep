package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapGenerator;
import com.goblinskeep.keyboard.PlayerInputHandler;
import com.goblinskeep.objects.ObjectManager;

public class testGoblin {
    // Mocked dependencies
    private GamePanel gp;
    private Goblin regularGoblin;

    @BeforeEach
    void setUp(){
        gp = new GamePanel();
        regularGoblin =  new RegularGoblin(gp, gp.Player);

    }

    //AI moves left since it is running with pathfinding
    private void moveleft(){

    }
    
    public void checkSpritechange(){

    }

  

    //Goblin SpriteChange

    //Goblin Collision with Player

    //Goblin Collision with Objects

    //Goblin Collision with Tiles

    //Goblin Collision with other Goblins


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

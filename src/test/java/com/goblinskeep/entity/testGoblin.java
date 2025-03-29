package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.awt.Image;
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
    private Player player;

    @BeforeEach
    void setUp(){
        gp = new GamePanel();
        regularGoblin =  new RegularGoblin(gp, gp.Player);

    }

    //AI moves left since it is running with pathfinding. It goes straight to the player, and stops 1 tile away from the player.
    // The player is at (0,0) and the goblin is at (12,0). The goblin moves left to (1,0) and stops there.
    private void moveleft(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        regularGoblin.WorldX = 12;
        regularGoblin.WorldY = 0;
        Goblin goblin = gp.getGoblinIterator().next();
        for (int i = 0; i < 11; i++){
            goblin.update();
        }
    }
    private void moveright(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        regularGoblin.WorldX = 12;
        regularGoblin.WorldY = 0;
        Goblin goblin = gp.getGoblinIterator().next();
        for (int i = 0; i < 11; i++){
            goblin.update();
        }
    }
    private void moveup(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        regularGoblin.WorldX = 12;
        regularGoblin.WorldY = 0;
        Goblin goblin = gp.getGoblinIterator().next();
        for (int i = 0; i < 11; i++){
            goblin.update();
        }
    }
    private void movedown(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        regularGoblin.WorldX = 0;
        regularGoblin.WorldY = 0;
        Goblin goblin = gp.getGoblinIterator().next();
        for (int i = 0; i < 11; i++){
            goblin.update();
        }
    }

    public void checkSpritechange(){

    }

    public void testUpdate(){
        gp.Player.WorldX = 0;
        gp.Player.WorldY = 0;
        regularGoblin.WorldX = 12;
        regularGoblin.WorldY = 0;
        regularGoblin.update();
        assertTrue(regularGoblin.onPath);
        assertTrue(regularGoblin.inSight);
    }

    @Test
    private void testDraw(){

    }

    //Goblin SpriteChange

    //Goblin Collision with Player

    //Goblin Collision with Objects

    //Goblin Collision with Tiles

    //Goblin Collision with other Goblins




    
}

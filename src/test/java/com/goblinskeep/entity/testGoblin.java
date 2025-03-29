package com.goblinskeep.entity;

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

    @Test
    private void testDraw(){

    }

    //Goblin SpriteChange

    //Goblin Collision with Player

    //Goblin Collision with Objects

    //Goblin Collision with Tiles

    //Goblin Collision with other Goblins




    
}

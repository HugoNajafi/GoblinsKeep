package com.goblinskeep.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.goblinskeep.app.GamePanel;

public class RegularGoblinTest {
    private GamePanel gp;
    private RegularGoblin regularGoblin;

    @BeforeEach
    void setUp(){
        gp = new GamePanel();
        regularGoblin =  new RegularGoblin(gp, gp.Player,0,0);


    }

    @Test
    void testConstructor() {

        RegularGoblin goblin = new RegularGoblin(gp, gp.Player,0,0);
        assertEquals(8, goblin.hitboxDefaultX);
        assertEquals(16, goblin.hitboxDefaultY);
        assertNotNull(goblin);
    }

    @Test
    void getActionTest(){
        regularGoblin.drawDirection = null;
        regularGoblin.getAction();
        assertNotNull(regularGoblin.drawDirection);
    }

    @Test
    void getPathTest(){
        regularGoblin.getAction();
        assertNotNull(regularGoblin.getPath());
    }

    @Test
    void interactPlayerTest(){
        regularGoblin.WorldX = gp.Player.WorldX;
        regularGoblin.WorldY = gp.Player.WorldY;
        regularGoblin.interactPlayer(45);
        assertTrue(gp.map.gameEnded());
    }

    @Test
    void simulateMovementLeft(){
         Goblin goblin = gp.getGoblinIterator().next();
         for (int i = 0; i < 2000; i++){
             goblin.update();
         }
        assertTrue(gp.map.gameEnded());
    }

    @Test
    void simulateMovementRight(){
        gp.Player.WorldX = 35 * gp.tileSize;
        gp.Player.WorldY = 10 * gp.tileSize;
        Goblin goblin = gp.getGoblinIterator().next();
        for (int i = 0; i < 2000; i++){
            goblin.update();
        }
        assertTrue(gp.map.gameEnded());
    }


}
package com.goblinskeep.entity;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CollisionCheckerTest {
    private GamePanel gp;
    private Player player;
    private CollisionChecker collisionChecker;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        player = gp.Player;
        collisionChecker = gp.collisionChecker;
    }
    @Test
    void checkTileCollisionUp(){
        //move player next to the above wall and check whether it detects if a wall is above him
        player.WorldY -= 30;
        player.direction = Direction.UP;
        collisionChecker.checkTile(player);
        assertTrue(player.collisionOn);
    }

    @Test
    void checkTileCollisionDown(){
        //move player next to the above wall and check whether it detects if a wall is above him
        player.WorldX -= gp.tileSize;
        player.direction = Direction.DOWN;
        collisionChecker.checkTile(player);
        assertTrue(player.collisionOn);
    }

    @Test
    void checkTileCollisionRight(){
        //move player next to the above wall and check whether it detects if a wall is above him
        player.WorldX += gp.tileSize + 30;
        player.direction = Direction.RIGHT;
        collisionChecker.checkTile(player);
        assertTrue(player.collisionOn);
    }

    @Test
    void checkTileCollisionLeft(){
        //move player next to the above wall and check whether it detects if a wall is above him
        player.WorldX -= 30;
        player.direction = Direction.LEFT;
        collisionChecker.checkTile(player);
        assertTrue(player.collisionOn);
    }

    @Test
    void checkTileNoCollisions(){
        Direction[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        //if any of the directions detects a tile then collision on will become true
        for (Direction d : dirs){
            player.direction = d;
            collisionChecker.checkTile(player);
        }
        assertFalse(player.collisionOn);
    }

    @Test
    void checkValidPlayerCollisionWithEnemy(){
        List<RegularGoblin> goblins = new ArrayList<>();
        RegularGoblin goblin = new RegularGoblin(gp, player);
        goblin.WorldX = player.WorldX + 10;
        goblin.WorldY = player.WorldY;
        goblins.add(goblin);
        player.direction = Direction.RIGHT;
        assertInstanceOf(RegularGoblin.class,
                collisionChecker.playerCollisionWithEnemy(player, goblins.iterator()));
    }

    @Test
    void checkInvalidPlayerCollisionWithEnemy(){
        List<RegularGoblin> goblins = new ArrayList<>();
        RegularGoblin goblin = new RegularGoblin(gp, player);
        goblin.WorldX = player.WorldX;
        goblin.WorldY = player.WorldY + gp.tileSize;
        goblins.add(goblin);
        player.direction = Direction.DOWN;
        assertNull(collisionChecker.playerCollisionWithEnemy(player, goblins.iterator()));
    }

}
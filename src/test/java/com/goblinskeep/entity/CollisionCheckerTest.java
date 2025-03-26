package com.goblinskeep.entity;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.Direction;
import com.goblinskeep.objects.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        player.WorldY += gp.tileSize * 2;
        player.WorldY -= 30;
        player.WorldX += gp.tileSize + gp.tileSize/2;
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
        goblins.add(null);
        player.direction = Direction.DOWN;
        assertNull(collisionChecker.playerCollisionWithEnemy(player, goblins.iterator()));
    }

    @Test
    void checkValidEnemyCollisionWithPlayer(){
        RegularGoblin goblin = new RegularGoblin(gp, player);
        goblin.WorldX = player.WorldX + 10;
        goblin.WorldY = player.WorldY;
        goblin.direction = Direction.LEFT;
        assertTrue(collisionChecker.checkPlayer(goblin));
    }

    @Test
    void checkInvalidEnemyCollisionWithPlayer(){
        RegularGoblin goblin = new RegularGoblin(gp, player);
        goblin.WorldX = player.WorldX + gp.tileSize;
        goblin.WorldY = player.WorldY;
        goblin.direction = Direction.LEFT;
        assertFalse(collisionChecker.checkPlayer(goblin));
    }

    @Test
    void checkValidEnemyToEnemyUpCollision(){
        RegularGoblin goblin1 = new RegularGoblin(gp, player);
        RegularGoblin goblin2 = new RegularGoblin(gp, player);
        goblin1.WorldX = 50;
        goblin1.WorldY = 50;
        goblin2.WorldX = goblin1.WorldX;
        goblin2.WorldY = goblin1.WorldY - 10;
        goblin1.direction = Direction.UP;
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        goblins.add(goblin1);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertInstanceOf(Direction.DOWN.getClass(), goblin1.direction);
    }

    @Test
    void checkValidEnemyToEnemyDownCollision(){
        RegularGoblin goblin1 = new RegularGoblin(gp, player);
        RegularGoblin goblin2 = new RegularGoblin(gp, player);
        goblin1.WorldX = 50;
        goblin1.WorldY = 50;
        goblin2.WorldX = goblin1.WorldX;
        goblin2.WorldY = goblin1.WorldY + 10;
        goblin1.direction = Direction.DOWN;
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        goblins.add(goblin1);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertInstanceOf(Direction.UP.getClass(), goblin1.direction);
    }

    @Test
    void checkValidEnemyToEnemyRightCollision(){
        RegularGoblin goblin1 = new RegularGoblin(gp, player);
        RegularGoblin goblin2 = new RegularGoblin(gp, player);
        goblin1.WorldX = 50;
        goblin1.WorldY = 50;
        goblin2.WorldX = goblin1.WorldX + 10;
        goblin2.WorldY = goblin1.WorldY;
        goblin1.direction = Direction.RIGHT;
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(null);
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertInstanceOf(Direction.LEFT.getClass(), goblin1.direction);
    }

    @Test
    void checkValidEnemyToEnemyLeftCollision(){
        RegularGoblin goblin1 = new RegularGoblin(gp, player);
        RegularGoblin goblin2 = new RegularGoblin(gp, player);
        goblin1.WorldX = 50;
        goblin1.WorldY = 50;
        goblin2.WorldX = goblin1.WorldX - 10;
        goblin2.WorldY = goblin1.WorldY;
        goblin1.direction = Direction.LEFT;
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(null);
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertInstanceOf(Direction.RIGHT.getClass(), goblin1.direction);
    }

    @Test
    void checkInvalidEnemyToEnemyVerticalCollision(){
        RegularGoblin goblin1 = new RegularGoblin(gp, player);
        RegularGoblin goblin2 = new RegularGoblin(gp, player);
        goblin1.WorldX = 50;
        goblin1.WorldY = 50;
        goblin2.WorldX = goblin1.WorldX + gp.tileSize + 10;
        goblin2.WorldY = goblin1.WorldY;
        goblin1.direction = Direction.RIGHT;
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertInstanceOf(Direction.RIGHT.getClass(), goblin1.direction);
    }


    @Test
    void checkValidPlayerCollisionWithObject(){
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize, new Key());
        player.direction = Direction.UP;
        assertInstanceOf(Key.class, gp.collisionChecker.checkObjectCollision(player, true));
    }

    @Test
    void checkInvalidPlayerCollisionWithObject(){
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize + 2, new Key());
        assertNull(gp.collisionChecker.checkObjectCollision(player, true));
    }

    @Test
    void enemyCollisionWithObject(){
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize, new Key());
        player.direction = Direction.UP;
        assertNull(gp.collisionChecker.checkObjectCollision(player, false));
    }

}
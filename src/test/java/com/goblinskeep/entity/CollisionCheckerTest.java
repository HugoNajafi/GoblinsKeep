package com.goblinskeep.entity;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.Direction;
import com.goblinskeep.objects.Exit;
import com.goblinskeep.objects.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for the CollisionChecker class.
 */
class CollisionCheckerTest {
    private GamePanel gp;
    private Player player;
    private CollisionChecker collisionChecker;

    /**
     * Sets up the test instances needed before each test.
     */
    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        player = gp.Player;
        collisionChecker = gp.collisionChecker;
    }

    /**
     * Moves the specified entity to the given position and direction.
     *
     * @param entity    the entity to move
     * @param x         the x-coordinate to move to
     * @param y         the y-coordinate to move to
     * @param direction the direction to face
     */
    private void moveEntityToPosition(Entity entity, int x, int y, Direction direction) {
        entity.WorldX = x;
        entity.WorldY = y;
        entity.direction = direction;
    }

    /**
     * Tests if the player detects a collision when moving up.
     */
    @Test
    void testTileCollisionUp() {
        //move player next to the above wall and check whether it detects if a wall is above him
        moveEntityToPosition(player,
                player.WorldX + gp.tileSize + gp.tileSize / 2,
                player.WorldY + gp.tileSize * 2 - 30, Direction.UP);
        collisionChecker.checkTileCollision(player);
        assertTrue(player.collisionOn);
    }

    /**
     * Tests if the player detects a collision when moving down.
     */
    @Test
    void testTileCollisionDown() {
        //move player next to the above wall and check whether it detects if a wall is above him
        moveEntityToPosition(player,player.WorldX - gp.tileSize, player.WorldY, Direction.DOWN);
        collisionChecker.checkTileCollision(player);
        assertTrue(player.collisionOn);
    }

    /**
     * Tests if the player detects a collision when moving right.
     */
    @Test
    void testTileCollisionRight() {
        //move player next to the above wall and check whether it detects if a wall is above him
        moveEntityToPosition(player, player.WorldX + gp.tileSize + 30, player.WorldY, Direction.RIGHT);
        collisionChecker.checkTileCollision(player);
        assertTrue(player.collisionOn);
    }

    /**
     * Tests if the player detects a collision when moving left.
     */
    @Test
    void testTileCollisionLeft() {
        //move player next to the above wall and check whether it detects if a wall is above him
        moveEntityToPosition(player, player.WorldX - 30, player.WorldY, Direction.LEFT);
        collisionChecker.checkTileCollision(player);
        assertTrue(player.collisionOn);
    }

    /**
     * Tests if the player does not detect any collisions in any direction.
     */
    @Test
    void testTileNoCollisions() {
        //if any of the directions detects a tile then collision on will become true
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Direction direction : directions) {
            player.direction = direction;
            collisionChecker.checkTileCollision(player);
            assertFalse(player.collisionOn);
        }
    }

    /**
     * Tests if correctly detects a collision with player and enemy.
     */
    @Test
    void testValidPlayerCollisionWithEnemy() {
        List<RegularGoblin> goblins = new ArrayList<>();
        RegularGoblin goblin = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin, player.WorldX + 10, player.WorldY, Direction.RIGHT);
        goblins.add(goblin);
        collisionChecker.playerCollisionWithEnemy(player, goblins.iterator());
        assertTrue(gp.map.gameEnded());
    }

    /**
     * Tests if the  correctly handles no collision with player and enemy.
     */
    @Test
    void testInvalidPlayerCollisionWithEnemy() {
        List<RegularGoblin> goblins = new ArrayList<>();
        RegularGoblin goblin = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin, player.WorldX, player.WorldY + gp.tileSize, Direction.DOWN);
        goblins.add(goblin);
        goblins.add(null);
        collisionChecker.playerCollisionWithEnemy(player, goblins.iterator());
        assertFalse(gp.map.gameEnded());
    }

    /**
     * Tests if an enemy correctly detects a collision with the player.
     */
    @Test
    void testValidEnemyCollisionWithPlayer() {
        RegularGoblin goblin = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin, player.WorldX + 10, player.WorldY, Direction.LEFT);
        assertTrue(collisionChecker.checkPlayer(goblin));
    }

    /**
     * Tests if an enemy correctly handles no collision with the player.
     */
    @Test
    void testInvalidEnemyCollisionWithPlayer() {
        RegularGoblin goblin = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin, player.WorldX + gp.tileSize, player.WorldY, Direction.LEFT);
        assertFalse(collisionChecker.checkPlayer(goblin));
    }

    /**
     * Tests if an enemy correctly detects a collision with another enemy when moving up.
     */
    @Test
    void testValidEnemyToEnemyUpCollision() {
        RegularGoblin goblin1 = new RegularGoblin(gp, player,0,0);
        RegularGoblin goblin2 = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin1, 50, 50, Direction.UP);
        moveEntityToPosition(goblin2, goblin1.WorldX, goblin1.WorldY - 10, Direction.UP);
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        goblins.add(goblin1);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertEquals(Direction.DOWN, goblin1.direction);
    }

    /**
     * Tests if an enemy correctly detects a collision with another enemy when moving down.
     */
    @Test
    void testValidEnemyToEnemyDownCollision() {
        RegularGoblin goblin1 = new RegularGoblin(gp, player,0,0);
        RegularGoblin goblin2 = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin1, 50, 50, Direction.DOWN);
        moveEntityToPosition(goblin2, goblin1.WorldX, goblin1.WorldY + 10, Direction.DOWN);
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        goblins.add(goblin1);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertEquals(Direction.UP, goblin1.direction);
    }

    /**
     * Tests if an enemy correctly detects a collision with another enemy when moving right.
     */
    @Test
    void testValidEnemyToEnemyRightCollision() {
        RegularGoblin goblin1 = new RegularGoblin(gp, player,0,0);
        RegularGoblin goblin2 = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin1, 50, 50, Direction.RIGHT);
        moveEntityToPosition(goblin2, goblin1.WorldX + 10, goblin1.WorldY, Direction.RIGHT);
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(null);
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertEquals(Direction.LEFT, goblin1.direction);
    }

    /**
     * Tests if an enemy correctly detects a collision with another enemy when moving left.
     */
    @Test
    void testValidEnemyToEnemyLeftCollision() {
        RegularGoblin goblin1 = new RegularGoblin(gp, player,0,0);
        RegularGoblin goblin2 = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin1, 50, 50, Direction.LEFT);
        moveEntityToPosition(goblin2, goblin1.WorldX - 10, goblin1.WorldY, Direction.LEFT);
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(null);
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertEquals(Direction.RIGHT, goblin1.direction);
    }

    /**
     * Tests if an enemy correctly handles no collision with another enemy in the vertical direction.
     */
    @Test
    void testInvalidEnemyToEnemyVerticalCollision() {
        RegularGoblin goblin1 = new RegularGoblin(gp, player,0,0);
        RegularGoblin goblin2 = new RegularGoblin(gp, player,0,0);
        moveEntityToPosition(goblin1, 50, 50, Direction.RIGHT);
        moveEntityToPosition(goblin2, goblin1.WorldX + gp.tileSize + 10, goblin1.WorldY, Direction.RIGHT);
        List<RegularGoblin> goblins = new ArrayList<>();
        goblins.add(goblin2);
        collisionChecker.checkEnemyCollision(goblin1, goblins.iterator());
        assertEquals(Direction.RIGHT, goblin1.direction);
    }

    /**
     * Tests if the player correctly detects a collision with an object.
     */
    @Test
    void testValidPlayerCollisionWithObject() {
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize, new Key());
        int before = gp.map.getKeysCollected();
        player.direction = Direction.UP;
        gp.collisionChecker.checkObjectCollision(player, true);
        assertEquals(before + 1, gp.map.getKeysCollected());
    }

    /**
     * Tests if the player correctly handles no collision with an object.
     */
    @Test
    void testInvalidPlayerCollisionWithObject() {
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize + 2, new Key());
        int before = gp.map.getKeysCollected();
        player.direction = Direction.UP;
        gp.collisionChecker.checkObjectCollision(player, true);
        assertEquals(before, gp.map.getKeysCollected());
    }

    /**
     * Tests if an enemy correctly handles no collision with an object.
     */
    @Test
    void testEnemyCollisionWithObject() {
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize, new Key());
        int before = gp.map.getKeysCollected();
        player.direction = Direction.UP;
        gp.collisionChecker.checkObjectCollision(player, false);
        assertEquals(before, gp.map.getKeysCollected());
    }

    /**
     * Tests if an entity correctly handles collision with an object and is solid.
     */
    @Test
    void testCollisionWithSolidObject() {
        gp.obj.addObject(player.WorldX / gp.tileSize, player.WorldY / gp.tileSize, new Exit());
        player.direction = Direction.UP;
        gp.collisionChecker.checkObjectCollision(player, true);
        assertTrue(player.collisionOn);
    }

    /**
     * Tests if an entity correctly handles no collisions.
     */
    @Test
    void testCheckPlayerCollisions(){
        player.WorldX = 0;
        player.WorldY = 0;
        collisionChecker.checkPlayerCollisions(player);
        assertFalse(player.collisionOn);
    }
}

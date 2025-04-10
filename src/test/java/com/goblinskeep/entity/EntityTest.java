package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Entity class.
 */
public class EntityTest {

    private Entity entity;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        GamePanel gp = new GamePanel();
        entity = gp.Player;
        entity.setX(100);
        entity.setY(200);
        entity.speed = 1;
    }

    /**
     * Tests the getX method.
     */
    @Test
    public void testGetX() {
        assertEquals(100, entity.getX());
    }

    /**
     * Tests the getY method.
     */
    @Test
    public void testGetY() {
        assertEquals(200, entity.getY());
    }

    /**
     * Tests the getSpeed method.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(1, entity.getSpeed());
    }

    /**
     * Tests the setX method.
     */
    @Test
    public void testSetX() {
        entity.setX(150);
        assertEquals(150, entity.getX());
    }

    /**
     * Tests the setY method.
     */
    @Test
    public void testSetY() {
        entity.setY(250);
        assertEquals(250, entity.getY());
    }

    /**
     * Tests the moveEntityTowardDirection method.
     */
    @Test
    public void testMoveEntityTowardDirection() {
        GamePanel gp = new GamePanel();
        entity = new RegularGoblin(gp, gp.Player, 100, 200);
        entity.direction = Direction.UP;
        entity.moveEntityTowardDirection();
        assertEquals(200 - entity.getSpeed(), entity.getY());

        entity.setY(200);
        entity.direction = Direction.DOWN;
        entity.moveEntityTowardDirection();
        assertEquals(200 + entity.getSpeed(), entity.getY());

        entity.direction = Direction.LEFT;
        entity.moveEntityTowardDirection();
        assertEquals(100 - entity.getSpeed(), entity.getX());

        entity.setX(100);
        entity.direction = Direction.RIGHT;
        entity.moveEntityTowardDirection();
        assertEquals(100 + entity.getSpeed(), entity.getX());
    }
}

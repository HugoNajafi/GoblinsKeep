package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import com.goblinskeep.app.MapHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.keyboard.PlayerInputHandler;
import com.goblinskeep.objects.MainObject;
import com.goblinskeep.objects.ObjectManager;

/**
 * Unit tests for the {@link Player} class.
 * This class tests player movement, drawing, and other behaviors.
 */
public class testPlayer {

    // Mocked dependencies
    public GamePanel mockGamePanel;
    public PlayerInputHandler mockPlayerInput;
    public CollisionChecker mockCollisionChecker;
    public Player player;
    public MapHandler mockMap;
    public ObjectManager mockObj;

    /**
     * Sets up the test environment by initializing mocks and the {@link Player} instance.
     */
    @BeforeEach
    public void setup() {
        // Initialize mocks
        mockGamePanel = mock(GamePanel.class);
        mockPlayerInput = mock(PlayerInputHandler.class);
        mockCollisionChecker = mock(CollisionChecker.class);
        mockMap = mock(MapHandler.class);
        mockObj = mock(ObjectManager.class);

        mockGamePanel.collisionChecker = mockCollisionChecker;
        mockGamePanel.map = mockMap;
        mockGamePanel.obj = mockObj;

        player = new Player(0, 0, mockGamePanel, mockPlayerInput);

        try {
            player = spy(player);
            doNothing().when(player).getPlayerImage(); // Disable image loading for tests
        } catch (Exception e) {
            fail("Failed to create player spy: " + e.getMessage());
        }
    }

    /**
     * Helper method to set the player's movement directions.
     *
     * @param up    whether the player is moving up
     * @param down  whether the player is moving down
     * @param left  whether the player is moving left
     * @param right whether the player is moving right
     */
    private void setPlayerMovement(boolean up, boolean down, boolean left, boolean right) {
        mockPlayerInput.up = up;
        mockPlayerInput.down = down;
        mockPlayerInput.left = left;
        mockPlayerInput.right = right;
    }

    /**
     * Helper method to mock collision behavior.
     *
     * @param collisionOn    whether a collision is detected
     * @param objectCollision the object involved in the collision
     * @param enemyCollision  the enemy involved in the collision
     */
    private void mockCollisions(boolean collisionOn, MainObject objectCollision, Entity enemyCollision) {
        doAnswer(invocation -> {
            Player p = invocation.getArgument(0);
            p.collisionOn = collisionOn;
            return null;
        }).when(mockCollisionChecker).checkTileCollision(player);

//        when(mockCollisionChecker.checkObjectCollision(player, true)).thenReturn(objectCollision);
    }

    /**
     * Helper method to assert the player's position and direction.
     *
     * @param expectedX        the expected X-coordinate
     * @param expectedY        the expected Y-coordinate
     * @param expectedDirection the expected direction
     */
    private void assertPlayerState(int expectedX, int expectedY, Direction expectedDirection) {
        assertEquals(expectedX, player.WorldX);
        assertEquals(expectedY, player.WorldY);
        assertEquals(expectedDirection, player.direction);
    }

    /**
     * Tests player movement in all directions.
     */
    @Test
    public void testMovement() {
        // Test moving RIGHT
        int initialX = player.WorldX;
        int initialY = player.WorldY;
        setPlayerMovement(false, false, false, true); // Move right
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX + player.getSpeed(), initialY, Direction.RIGHT);

        // Test moving LEFT
        setPlayerMovement(false, false, true, false); // Move left
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX, initialY, Direction.LEFT);

        // Test moving UP
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX, initialY - player.getSpeed(), Direction.UP);

        // Test moving DOWN
        setPlayerMovement(false, true, false, false); // Move down
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX, initialY, Direction.DOWN);
    }

    /**
     * Tests that the player does not move when no movement keys are pressed.
     */
    @Test
    public void testNoMovement() {
        int initialX = player.WorldX;
        int initialY = player.WorldY;
        setPlayerMovement(false, false, false, false); // No movement
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX, initialY, Direction.DOWN); // Default direction
    }

    /**
     * Tests the player's drawing behavior and sprite updates.
     */
    @Test
    public void testDraw() {
        Graphics2D g2 = mock(Graphics2D.class);
        Image mockImage = mock(Image.class);
        ImageObserver mockObserver = mock(ImageObserver.class);

        when(g2.drawImage(eq(mockImage), anyInt(), anyInt(), anyInt(), anyInt(), eq(mockObserver)))
            .thenReturn(false);

        // Test UP movement
        setPlayerMovement(true, false, false, false); // Move up
        player.update();
        assertEquals(1, player.SpriteNum); // Assert SpriteNum is 1
        assertEquals(player.up1, player.getSpriteForDirection()); // Assert image is up2
        for (int i = 0; i < 10; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(2, player.SpriteNum); // Assert SpriteNum is 2
        assertEquals(player.up2, player.getSpriteForDirection()); // Assert image is up2

        // Test DOWN movement
        setPlayerMovement(false, true, false, false); // Move down
        for (int i = 0; i < 11; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(1, player.SpriteNum); // Assert SpriteNum is 1
        assertEquals(player.down1, player.getSpriteForDirection()); // Assert image is down1
        
        for (int i = 0; i < 11; i++) {
            player.update();
        }
        assertEquals(2, player.SpriteNum); // Assert SpriteNum is 2
        assertEquals(player.down2, player.getSpriteForDirection()); // Assert image is down2

        // Test LEFT movement
        setPlayerMovement(false, false, true, false); // Move left
        for (int i = 0; i < 11; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(1, player.SpriteNum); // Assert SpriteNum is 1
        assertEquals(player.left1, player.getSpriteForDirection()); // Assert image is left1

        for (int i = 0; i < 11; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(2, player.SpriteNum); // Assert SpriteNum is 2
        assertEquals(player.left2, player.getSpriteForDirection()); // Assert image is left2

        // Test RIGHT movement
        setPlayerMovement(false, false, false, true); // Move right
        for (int i = 0; i < 11; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(1, player.SpriteNum); // Assert SpriteNum is 1
        assertEquals(player.right1, player.getSpriteForDirection()); // Assert image is right1

        for (int i = 0; i < 11; i++) {
            player.update();
        }
        player.draw(g2);
        assertEquals(2, player.SpriteNum); // Assert SpriteNum is 2
        assertEquals(player.right2, player.getSpriteForDirection()); // Assert image is right2

        System.out.println("Player Draw and SpriteCounter passed. Success.");
    }

    /**
     * Tests the {@link Player#getCenterTileCoordinates()} method.
     */
    @Test
    void getCenterTileCoordinatesTest(){
        GamePanel gp = new GamePanel();
        Player player = gp.Player;
        int xTile = 20;
        int yTile = 30;
        player.WorldX = xTile * gp.tileSize;
        player.WorldY = yTile * gp.tileSize;
        assertEquals(xTile, player.getCenterTileCoordinates().x);
        assertEquals(yTile, player.getCenterTileCoordinates().y);
    }
}

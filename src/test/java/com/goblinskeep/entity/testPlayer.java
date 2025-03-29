package com.goblinskeep.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapGenerator;
import com.goblinskeep.entity.CollisionChecker;
import com.goblinskeep.entity.Player;
import com.goblinskeep.keyboard.PlayerInputHandler;
import com.goblinskeep.objects.Bonus;
import com.goblinskeep.objects.InvisibleBarrier;
import com.goblinskeep.objects.Key;
import com.goblinskeep.objects.Lever;
import com.goblinskeep.objects.MainObject;
import com.goblinskeep.objects.ObjectManager;
import com.goblinskeep.objects.Trap;


public class testPlayer {

    // Mocked dependencies
    public GamePanel mockGamePanel;
    public PlayerInputHandler mockPlayerInput;
    public CollisionChecker mockCollisionChecker;
    public Player player;
    public MapGenerator mockMap;
    public ObjectManager mockObj;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        mockGamePanel = mock(GamePanel.class);
        mockPlayerInput = mock(PlayerInputHandler.class);
        mockCollisionChecker = mock(CollisionChecker.class);
        mockMap = mock(MapGenerator.class);
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

    // Helper method to set up player movement
    private void setPlayerMovement(boolean up, boolean down, boolean left, boolean right) {
        mockPlayerInput.up = up;
        mockPlayerInput.down = down;
        mockPlayerInput.left = left;
        mockPlayerInput.right = right;
    }

    // Helper method to mock collision behavior
    private void mockCollisions(boolean collisionOn, MainObject objectCollision, Entity enemyCollision) {
        doAnswer(invocation -> {
            Player p = invocation.getArgument(0);
            p.collisionOn = collisionOn;
            return null;
        }).when(mockCollisionChecker).checkTile(player);

        when(mockCollisionChecker.checkObjectCollision(player, true)).thenReturn(objectCollision);
        when(mockCollisionChecker.playerCollisionWithEnemy(eq(player), any())).thenReturn(enemyCollision);
    }

    // Helper method to assert player position and direction
    private void assertPlayerState(int expectedX, int expectedY, Direction expectedDirection) {
        assertEquals(expectedX, player.WorldX);
        assertEquals(expectedY, player.WorldY);
        assertEquals(expectedDirection, player.direction);
    }

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

    @Test
    public void testNoMovement() {
        int initialX = player.WorldX;
        int initialY = player.WorldY;
        setPlayerMovement(false, false, false, false); // No movement
        mockCollisions(false, null, null);
        player.update();
        assertPlayerState(initialX, initialY, Direction.DOWN); // Default direction
    }

    @Test
    public void testCollisionWithTile() {
        int initialX = player.WorldX;
        int initialY = player.WorldY;

        // Test collision while moving UP
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(true, null, null); // Collision detected
        player.update();
        assertPlayerState(initialX, initialY, Direction.UP);

        // Test collision while moving DOWN
        setPlayerMovement(false, true, false, false); // Move down
        mockCollisions(true, null, null); // Collision detected
        player.update();
        assertPlayerState(initialX, initialY, Direction.DOWN);
    }

    @Test
    public void testObjectCollisions() {
        int initialX = player.WorldX;
        int initialY = player.WorldY;
        // Test collision with Bonus
        Bonus bonus = new Bonus(0, 20);
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, bonus, null);
        doNothing().when(mockMap).collectedBonus(bonus);
        player.update();
        assertPlayerState(initialX, initialY - player.getSpeed(), Direction.UP);

        initialX = player.WorldX;
        initialY = player.WorldY;
        // Test collision with Key
        Key key = new Key();
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, key, null);
        doNothing().when(mockMap).keyCollected();
        doNothing().when(mockObj).removeObject(player.WorldX, player.WorldY);
        player.update();
        assertPlayerState(initialX, initialY - player.getSpeed(), Direction.UP);

        initialX = player.WorldX;
        initialY = player.WorldY;
        // Test collision with Trap
        Trap trap = new Trap();
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, trap, null);
        doNothing().when(mockMap).trapHit();
        player.update();
        assertPlayerState(initialX, initialY - player.getSpeed(), Direction.UP);

        initialX = player.WorldX;
        initialY = player.WorldY;
        // Test collision with lever
        Lever lever = new Lever();
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, lever, null);
        doNothing().when(mockMap).leverTouched();
        player.update();
        assertPlayerState(initialX, initialY - player.getSpeed(), Direction.UP);
        
        initialX = player.WorldX;
        initialY = player.WorldY;
        // Test collision with invisible barrier
        InvisibleBarrier barrier = new InvisibleBarrier();
        setPlayerMovement(true, false, false, false); // Move up
        mockCollisions(false, barrier, null);
        doNothing().when(mockMap).exitTouched();
        player.update();
        assertPlayerState(initialX, initialY, Direction.UP);
        }

    @Test
    public void testGoblinCollision() {
        MapGenerator map = new MapGenerator(mockGamePanel);
        Goblin mockGoblin = mock(Goblin.class);

        // Mock collision detection
        mockCollisions(true, null, mockGoblin);


        // Act
        player.update();

        // Assert
        assertTrue(player.collisionOn, "Player collisionOn should be true after colliding with a goblin.");
    }

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
}

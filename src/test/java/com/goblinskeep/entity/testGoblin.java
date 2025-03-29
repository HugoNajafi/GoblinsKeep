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

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapGenerator;
import com.goblinskeep.keyboard.PlayerInputHandler;
import com.goblinskeep.objects.ObjectManager;

public class testGoblin {
    // Mocked dependencies
    public GamePanel mockGamePanel;
    public PlayerInputHandler mockPlayerInput;
    public CollisionChecker mockCollisionChecker;
    public Player player;
    public Goblin goblin;
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
        Goblin goblin = new RegularGoblin(mockGamePanel, player);

        try {
            player = spy(player);
            goblin = spy(goblin);
            doNothing().when(player).getPlayerImage(); // Disable image loading for tests
            doNothing().when(goblin).getGoblinImage(); // Disable image loading for tests
        } catch (Exception e) {
            fail("Failed to create player spy: " + e.getMessage());
        }
    }

    public void tes

    @Test
    private void testDraw(){

    }

    //Goblin SpriteChange

    //Goblin Collision with Player

    //Goblin Collision with Objects

    //Goblin Collision with Tiles

    //Goblin Collision with other Goblins




    
}

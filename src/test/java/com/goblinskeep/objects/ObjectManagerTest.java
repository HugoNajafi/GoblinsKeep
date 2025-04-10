package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ObjectManager} class.
 */
public class ObjectManagerTest {

    private ObjectManager objectManager;
    private GamePanel mockGamePanel;

    /**
     * Sets up the test environment by initializing the ObjectManager and a mocked GamePanel.
     */
    @BeforeEach
    public void setUp() {
        mockGamePanel = mock(GamePanel.class);
        objectManager = new ObjectManager(mockGamePanel);
    }

    /**
     * Tests adding an object to the ObjectManager.
     */
    @Test
    public void testAddObject() {
        MainObject mockObject = mock(MainObject.class);
        objectManager.addObject(1, 2, mockObject);

        String key = "1,2";
        assertTrue(objectManager.anObject.containsKey(key));
        assertEquals(mockObject, objectManager.anObject.get(key));
        assertEquals(48, mockObject.worldX);
        assertEquals(96, mockObject.worldY);
    }

    /**
     * Tests removing an object from the ObjectManager.
     */
    @Test
    public void testRemoveObject() {
        MainObject mockObject = mock(MainObject.class);
        objectManager.addObject(2, 3, mockObject);
        assertEquals(1, objectManager.anObject.size());

        objectManager.removeObject(96, 144); // 2 * 32, 3 * 32
        assertEquals(0, objectManager.anObject.size());
    }

    /**
     * Tests removing an object that does not exist in the ObjectManager.
     */
    @Test
    public void testRemoveObject_DoesNotExist() {
        objectManager.removeObject(0, 0);
        assertEquals(0, objectManager.anObject.size());
    }

    /**
     * Tests finding a door object in the ObjectManager.
     */
    @Test
    public void testFindDoor() {
        Exit mockExit = mock(Exit.class);
        objectManager.addObject(0, 0, mockExit);

        Exit found = objectManager.findDoor();
        assertNotNull(found);
        assertEquals(mockExit, found);
    }

    /**
     * Tests the behavior when no door object is found in the ObjectManager.
     */
    @Test
    public void testFindDoor_NotFound() {
        MainObject mockObject = mock(MainObject.class);
        objectManager.addObject(0, 0, mockObject);

        assertNull(objectManager.findDoor());
    }

    /**
     * Tests finding a lever object in the ObjectManager.
     */
    @Test
    public void testFindLever() {
        Lever mockLever = mock(Lever.class);
        objectManager.addObject(0, 1, mockLever);

        Lever found = objectManager.findLever();
        assertNotNull(found);
        assertEquals(mockLever, found);
    }

    /**
     * Tests the behavior when no lever object is found in the ObjectManager.
     */
    @Test
    public void testFindLever_NotFound() {
        MainObject mockObject = mock(MainObject.class);
        objectManager.addObject(0, 0, mockObject);

        assertNull(objectManager.findLever());
    }

    /**
     * Tests drawing objects managed by the ObjectManager.
     */
    @Test
    public void testDraw() {
        Graphics2D g2 = mock(Graphics2D.class);
        MainObject mockObject = mock(MainObject.class);
        objectManager.addObject(0, 0, mockObject);

        objectManager.draw(g2, mockGamePanel);
        verify(mockObject, times(1)).draw(g2, mockGamePanel);
    }
}

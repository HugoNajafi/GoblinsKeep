package com.goblinskeep.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for the Exit class.
 */
class ExitTest {
    private Exit exit;

    /**
     * Sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp(){
         exit = new Exit();
    }

    /**
     * Tests the constructor of the Exit class.
     */
    @Test
    void testConstructor() {
        // Check that the initial fields are correct
        assertEquals("exit", exit.name);
        assertNotNull(exit.image);
        assertTrue(exit.collision);
    }

    /**
     * Tests the open method of the Exit class.
     */
    @Test
    void testOpen() {
        Exit exit = new Exit();

        //get the initial image to test later
        BufferedImage initialImage = exit.image;

        //method to test
        exit.open();

        //check expected outputs
        assertFalse(exit.collision);
        assertNotEquals(initialImage, exit.image);
    }
}

package com.goblinskeep.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for the Lever class.
 */
class LeverTest {
    private Lever lever;

    /**
     * Sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp(){
        lever = new Lever();
    }

    /**
     * Tests the constructor of the lever class.
     */
    @Test
    void testConstructor() {
        // Check that the initial fields are correct
        assertEquals("lever", lever.name);
        assertNotNull(lever.image);
        assertFalse(lever.collision);
    }

    /**
     * Tests the activate method of the lever class.
     */
    @Test
    void testActivate() {
        Lever lever = new Lever();

        //get the initial image to test later
        BufferedImage initialImage = lever.image;

        //method to test
        lever.activate();

        //check if initial image has changed
        assertNotEquals(initialImage, lever.image);
    }

}
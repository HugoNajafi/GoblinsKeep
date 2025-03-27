package com.goblinskeep.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for the Key class.
 */
class KeyTest {

    /**
     * Tests the constructor of the Key class.
     */
    @Test
    void testConstructor() {
        Key key = new Key();
        // Check that the initial fields are correct
        assertEquals("key", key.name);
        assertNotNull(key.image);
        assertFalse(key.collision);
    }
}
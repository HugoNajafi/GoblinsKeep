package com.goblinskeep.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test class for the Trap class.
 */
class TrapTest {

    /**
     * Tests the constructor of the Trap class.
     */
    @Test
    void testConstructor() {
        Trap trap = new Trap();
        // Check that the initial fields are correct
        assertEquals("trap", trap.name);
        assertNotNull(trap.image);
        assertFalse(trap.collision);
    }

}
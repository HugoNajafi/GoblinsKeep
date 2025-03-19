package com.goblinskeep.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeverTest {

    @Test
    void activate() {
        Lever l1 = new Lever();
        assertFalse(l1.collision);
    }
}
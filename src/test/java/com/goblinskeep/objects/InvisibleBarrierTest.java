package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for the InvisibleBarrier class.
 */
class InvisibleBarrierTest {

    /**
     * Tests the constructor of the InvisibleBarrier class.
     */
    @Test
    void testConstructor() {
        InvisibleBarrier invisibleBarrier = new InvisibleBarrier();
        // Check that the initial fields are correct
        assertEquals("invisible", invisibleBarrier.name);
        assertNull(invisibleBarrier.image);
        assertTrue(invisibleBarrier.collision);

    }

    /**
     * Tests the draw method of the InvisibleBarrier class.
     */
    @Test
    void testDraw(){
        InvisibleBarrier invisibleBarrier = new InvisibleBarrier();
        Graphics2D g2 = mock(Graphics2D.class);
        GamePanel gp = mock(GamePanel.class);

        //call draw method and verify it does not draw on the screen
        invisibleBarrier.draw(g2, gp);
        verifyNoInteractions(g2);
    }

}
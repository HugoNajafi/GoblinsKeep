package com.goblinskeep.objects;

import java.awt.*;

import com.goblinskeep.app.GamePanel;


/**
 * Represents an invisible barrier that the player can touch to trigger the win screen.
 *
 * - The barrier is **invisible** and does not render on the screen.
 * - It becomes active after the player **opens the exit door and walks outside**.
 * - When touched, it triggers the **win condition** in the game.
 */
public class InvisibleBarrier extends MainObject{

    /**
     * Constructs an invisible barrier.
     * The barrier has **collision enabled** to detect player interaction.
     */
    public InvisibleBarrier(){
        collision = true;
        name = "invisible";
    }


    /**
     * Overrides the draw method to prevent rendering.
     * This ensures the barrier remains completely invisible in the game.
     *
     * @param g2 The graphics context (unused).
     * @param gp The game panel (unused).
     */
    @Override
    public void draw(Graphics2D g2, GamePanel gp){
        //draw nothing
    }
}

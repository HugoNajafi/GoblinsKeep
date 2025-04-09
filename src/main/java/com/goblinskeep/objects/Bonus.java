package com.goblinskeep.objects;

import java.awt.*;
import com.goblinskeep.app.GamePanel;

/**
 * Represents a Bonus item that appears temporarily in the game.
 *
 * - It can become active at a random time between **0 and 30 seconds**.
 * - Once active, it remains for a duration between **30 to 60 seconds** before disappearing permanently.
 */
public class Bonus extends MainObject {

    /** The time (in seconds) when the bonus item becomes active. */
    private int startTime;

    /** The duration (in seconds) for which the bonus remains before disappearing. */
    private int survivalTime;

    /** Indicates whether the bonus is currently active (visible and collectible). */
    private boolean alive = false;


    /**
     * Constructs a Bonus item with a random activation and survival duration.
     *
     * @param startTime The time at which the bonus becomes active (between 0 and 30 seconds).
     * @param survivalTime The duration for which the bonus remains active (between 30 and 60 seconds).
     */
    public Bonus(int startTime, int survivalTime) {
        name = "bonus";

        image = loadImage("/objects/bonus1.png");

        collision = false;

        // Ensure startTime is within the range 0-30 seconds
        this.startTime = startTime % 30;

        // Ensure survivalTime is within the range 30-60 seconds
        this.survivalTime = 30 + survivalTime % 30;
    }


    /**
     * Updates the bonus item's state based on the current game time.
     * If the bonus is within its activation window, it becomes active.
     *
     * @param currentTime The current time in the game (in seconds).
     */
    public void updateState(int currentTime){
        if (isAlive(currentTime)){
            alive = true;
        } else {
            alive = false;
        }
    }


    /**
     * Determines whether the bonus item is currently active.
     *
     * @param currentTime The current time in the game (in seconds).
     * @return {@code true} if the bonus is active, otherwise {@code false}.
     */

    public boolean isAlive(int currentTime){
        return startTime <= currentTime && currentTime < startTime + survivalTime;
    }

    /**
     * Draws the bonus item on the screen if it is currently active.
     *
     * @param g2 The graphics object used for rendering.
     * @param gp The game panel where the bonus is displayed.
     */
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (alive){
            super.draw(g2, gp);
        }
    }
}

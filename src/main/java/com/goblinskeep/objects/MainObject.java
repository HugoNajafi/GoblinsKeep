package com.goblinskeep.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.goblinskeep.app.GamePanel;

/**
 * Represents the base class for all interactive objects in the game.
 * This includes items such as keys, bonuses, levers, traps, and barriers.
 *
 * Objects derived from this class can have unique properties such as collision behavior
 * and visibility within the game world.
 */
public abstract class MainObject {

    /** The image representing the object. */
    public BufferedImage image;

    /** The name of the object (e.g., "key", "bonus", "trap"). */
    public String name;

    /** Indicates whether the object has collision enabled (true = cannot pass through). */
    public boolean collision = false;

    /** The object's position in the game world. */
    public int worldX, worldY;

    /** The hitbox area for collision detection. */
    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);

    /** Default X and Y offset for collision area, useful for adjusting object hitboxes. */
    public int defaultCollisionAreaX, defaultCollisionAreaY;


    /**
     * Draws the object on the screen if it is within the visible area (camera view).
     *
     * @param g2 The graphics object used for rendering.
     * @param gp The game panel where the object is displayed.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = worldY - gp.Player.WorldY + gp.Player.screenY;

        //camera logic, draw only if the object is within the player's visible range
        if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                worldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}

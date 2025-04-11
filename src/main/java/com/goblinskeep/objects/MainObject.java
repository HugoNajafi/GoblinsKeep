package com.goblinskeep.objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.goblinskeep.app.GamePanel;

import javax.imageio.ImageIO;

/**
 * Represents the base class for all interactive objects in the game.
 * This includes items such as keys, bonuses, levers, traps, and barriers.
 * <p>
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

    /**
     * Loads an image from the given resource path.
     * This method attempts to read an image file located in the classpath using
     *
     * @param path the path to the image resource, starting from the root of the classpath (e.g. {@code "/goblin/orc_up_1.png"})
     * @return a {@link BufferedImage} representing the loaded image
     * @throws RuntimeException if the image cannot be found or read
     */
    public static BufferedImage loadImage(String path) {
        try (InputStream is = MainObject.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Image not found: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }
}

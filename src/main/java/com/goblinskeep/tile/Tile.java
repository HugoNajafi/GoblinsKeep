package com.goblinskeep.tile;

import java.awt.image.BufferedImage;

/**
 * Represents a tile in the game, which can have an image and a collision property.
 */
public class Tile {
    /**
     * The image representing the tile.
     */
    public BufferedImage image;

    /**
     * Indicates whether the tile has collision enabled.
     */
    public boolean collision = false;
}

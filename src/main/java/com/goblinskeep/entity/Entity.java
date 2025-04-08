package com.goblinskeep.entity;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.goblinskeep.app.Direction;

import javax.imageio.ImageIO;


/**
 * Represents a general game entity, serving as the base class for player and enemy characters.
 * This class defines movement properties, collision areas, and sprite animations.
 */
public abstract class Entity {

    /** The entity's x-coordinate and y-coordinate in the world. */
    public int WorldX, WorldY;

    /** The movement speed of the entity. */
    public int speed;

    /** Sprites for movement animations in different directions. */
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    /** The current movement direction of the entity. */
    public Direction direction;

    /** Counter for controlling sprite animation updates. */
    protected int SpriteCounter = 0;

    /** The current sprite number for animation switching. */
    protected int SpriteNum = 1;

    /** The hitbox area of the entity for collision detection. */
    public Rectangle collisionArea;
    public int hitboxDefaultX = 48;
    public int hitboxDefaultY = 48;

    /** Flag to determine if the entity is currently colliding with something. */
    public boolean collisionOn;

    public int actionLockCounter = 0;


    /**
     * Default constructor that initializes the entity with default speed and collision settings.
     */
    public Entity(){
        this.speed = 1;
        this.collisionArea = new Rectangle(0, 0, hitboxDefaultX, hitboxDefaultY); // Adjust size as needed
        this.hitboxDefaultX = 0;
        this.hitboxDefaultY = 0;
        this.collisionOn = false;
    }


    /**
     * Constructor to initialize the entity at a specific position in the world.
     *
     * @param WorldX The x-coordinate of the entity in the game world.
     * @param WorldY The y-coordinate of the entity in the game world.
     */
    public Entity(int WorldX, int WorldY){
        this.WorldX = WorldX;
        this.WorldY = WorldY;
        this.speed = 1;
        this.collisionArea = new Rectangle(0, 0, hitboxDefaultX, hitboxDefaultY); // Adjust size as needed
        this.hitboxDefaultX = 0;
        this.hitboxDefaultY = 0;
        this.collisionOn = false;
    }


    /**
     * Gets the entity's current x-coordinate.
     *
     * @return The x-coordinate of the entity in the game world.
     */
    public int getX(){
        return this.WorldX;
    }


    /**
     * Gets the entity's current y-coordinate.
     *
     * @return The y-coordinate of the entity in the game world.
     */
    public int getY(){
        return this.WorldY;
    }


    /**
     * Gets the movement speed of the entity.
     *
     * @return The speed value.
     */
    public int getSpeed(){
        return this.speed;
    }


    /**
     * Sets a new x-coordinate for the entity.
     *
     * @param NewX The new x-coordinate in the game world.
     */
    public void setX(int NewX){
        this.WorldX = NewX;
    }


    /**
     * Sets a new y-coordinate for the entity.
     *
     * @param NewY The new x-coordinate in the game world.
     */
    public void setY(int NewY){
        this.WorldY = NewY;
    }

    /**
     * Moves the entity in the direction it is currently facing.
     * The movement is based on the entity's speed and the direction
     */
    public void moveEntityTowardDirection(){
        if (direction == Direction.UP) {
            this.WorldY -= Direction.UP.getDy() * this.getSpeed();
        } else if (direction == Direction.DOWN) {
            this.WorldY -= Direction.DOWN.getDy() * this.getSpeed();
        } else if (direction == Direction.LEFT) {
            this.WorldX += Direction.LEFT.getDx() * this.getSpeed();
        } else  { //Direction.RIGHT
            this.WorldX += Direction.RIGHT.getDx() * this.getSpeed();
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
    public static BufferedImage loadImage(String path){
        try (InputStream inputStream = Entity.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }
    
}

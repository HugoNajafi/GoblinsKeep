package com.goblinskeep.entity;

import com.goblinskeep.app.Direction;
import com.goblinskeep.keyboard.PlayerInputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.goblinskeep.app.GamePanel;

/**
 * Represents the player character in the game.
 * The player can move, interact with objects, and collide with enemies.
 */
public class Player extends Entity{

    /** Handles user input for player movement. */
    PlayerInputHandler PlayerInput;

    /** The player's screen position in the x-axis (centered). */
    public final int screenX;

    /** The player's screen position in the y-axis (centered). */
    public final int screenY;


    /**
     * Constructs a Player entity with a starting position.
     *
     * @param startX   The player's initial x-coordinate.
     * @param startY   The player's initial y-coordinate.
     * @param gp       The game panel.
     * @param PlayerInput Handles player input.
     */
    public Player(int startX, int startY, GamePanel gp, PlayerInputHandler PlayerInput) {
        super(gp, startX, startY);  // Pass values up to GameObject constructor
        gp.Player = this;
        this.PlayerInput = PlayerInput;

        // Set a default direction
        this.direction = Direction.DOWN;

        // Set up the collision area
        this.collisionArea = new Rectangle(8, 16, 32, 32); // Adjust these values to fit your sprite
        this.hitboxDefaultX = 8;
        this.hitboxDefaultY = 16;

        // Set player's screen position (centered)
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        getPlayerImage();
    }


    /** Loads the player's sprite images for different movement directions. */
    public void getPlayerImage() {
        up1    = Entity.loadImage("/player/up1.png");
        up2    = Entity.loadImage("/player/up2.png");
        down1  = Entity.loadImage("/player/down1.png");
        down2  = Entity.loadImage("/player/down2.png");
        right1 = Entity.loadImage("/player/right1.png");
        right2 = Entity.loadImage("/player/right2.png");
        left1  = Entity.loadImage("/player/left1.png");
        left2  = Entity.loadImage("/player/left2.png");
    }

    /** Updates the player's movement and collision logic. */
    public void update() {
        // Reset collision flag
        collisionOn = false;
        updateDirection();
        gp.debugMode = PlayerInput.debugMode;
        // Check collision with tiles, objects and goblin before moving
        gp.collisionChecker.checkPlayerCollisions(this);
        // Move player if no collision detected
        moveEntityTowardDirection();


    }

    /**
     * Determine direction based on player input.
     * This method checks the current state of the PlayerInput and updates
     * the direction accordingly.
    */
    private void updateDirection() {
        if (PlayerInput.up) {
            direction = Direction.UP;
        } else if (PlayerInput.down) {
            direction = Direction.DOWN;
        } else if (PlayerInput.left) {
            direction = Direction.LEFT;
        } else if (PlayerInput.right) {
            direction = Direction.RIGHT;
        }
    }

    private boolean playerInput(){
        boolean PlayerIn = false;
        if (PlayerInput.up || PlayerInput.down || PlayerInput.left || PlayerInput.right) {
            PlayerIn = true;
        }
        return PlayerIn;
    }

    @Override
    protected boolean canMove(){
        return !collisionOn && playerInput();
    }

    /**
     * Draws the player's sprite on the screen based on movement direction.
     *
     * @param g2 The graphics object used to draw the player.
     */
    public void draw(Graphics2D g2){

        BufferedImage image = getSpriteForDirection();
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }


    /**
     * Gets the tile coordinates of the player's center hit-box position.
     * This converts the player's world coordinates to tile-based coordinates.
     *
     * @return A Point containing the column (x) and row (y) of the tile where the player's hit-box center is located
     */
    public Point getCenterTileCoordinates(){
        int col = (WorldX + hitboxDefaultX + (collisionArea.width / 2)) / gp.tileSize;
        int row = (WorldY + hitboxDefaultY + (collisionArea.height / 2)) / gp.tileSize;
        return new Point(col , row);
    }

}

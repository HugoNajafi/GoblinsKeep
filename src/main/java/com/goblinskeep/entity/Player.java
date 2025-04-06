package com.goblinskeep.entity;

import com.goblinskeep.app.Direction;
import com.goblinskeep.keyboard.PlayerInputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.goblinskeep.app.GamePanel;

/**
 * Represents the player character in the game.
 * The player can move, interact with objects, and collide with enemies.
 */
public class Player extends Entity{

    /** Reference to the main game panel. */
    public GamePanel gp;

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
        super(startX, startY);  // Pass values up to GameObject constructor
        this.gp = gp;
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

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /** Updates the player's movement and collision logic. */
    public void update() {
        // Reset collision flag
        collisionOn = false;

        // Determine direction based on player input
        if (PlayerInput.up) {
            direction = Direction.UP;
        } else if (PlayerInput.down) {
            direction = Direction.DOWN;
        } else if (PlayerInput.left) {
            direction = Direction.LEFT;
        } else if (PlayerInput.right) {
            direction = Direction.RIGHT;
        }

        gp.debugMode = PlayerInput.debugMode;

        // Check collision with tiles, objects and goblin before moving
        gp.collisionChecker.checkPlayerCollisions(this);

        // Move player if no collision detected
        if (!collisionOn) {
            if (PlayerInput.up) {
                this.WorldY -= Direction.UP.getDy() * this.getSpeed();
            } else if (PlayerInput.down) {
                this.WorldY -= Direction.DOWN.getDy() * this.getSpeed();
            } else if (PlayerInput.left) {
                this.WorldX += Direction.LEFT.getDx() * this.getSpeed();
            } else if (PlayerInput.right) {
                this.WorldX += Direction.RIGHT.getDx() * this.getSpeed();
            }
        }

        // Update animation frames when moving
        if (PlayerInput.up || PlayerInput.down || PlayerInput.left ||PlayerInput.right ){
            SpriteCounter++;
            if(SpriteCounter> 10){
                SpriteNum = (SpriteNum == 1) ? 2: 1;
                SpriteCounter = 0;
            }
        }
    }


    public BufferedImage getSpriteForDirection(){
        BufferedImage image = null;
        switch (direction){
            case Direction.UP:
                if (SpriteNum == 1) {
                    image = up1;
                }
                if (SpriteNum == 2) {
                    image = up2;
                }
                break;
            case Direction.DOWN:
                if (SpriteNum == 1) {
                    image = down1;
                }
                if (SpriteNum == 2) {
                    image = down2;
                }
                break;
            case Direction.LEFT:
                if (SpriteNum == 1) {
                    image = left1;
                }
                if (SpriteNum == 2) {
                    image = left2;
                }
                break;
            case Direction.RIGHT:
                if (SpriteNum == 1) {
                    image = right1;
                }
                if (SpriteNum == 2) {
                    image = right2;
                }
                break;
        }
        return image;
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

}

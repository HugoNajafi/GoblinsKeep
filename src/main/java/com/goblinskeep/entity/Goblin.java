package com.goblinskeep.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;

/**
 * Represents a goblin enemy in the game.
 * This is an abstract class that defines common goblin behavior and appearance.
 * Goblins are hostile NPCs that interact with the player and game environment.
 */
public abstract class Goblin extends Entity{

    /** Reference to the main game panel. */
    protected GamePanel gp;

    /** Reference to the player, used for AI movement and interaction. */
    protected Player player;

    /** The direction in which the goblin is currently facing/drawn. */
    protected Direction drawDirection = Direction.UP;

    public boolean onPath;

    public boolean inSight;

    /**
     * Constructs a goblin with references to the game panel and player.
     *
     * @param gp     The game panel instance.
     * @param player The player instance.
     */
    public Goblin(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        this.speed = 2;
        this.collisionArea = new Rectangle(11, 17, 23, 23); // Set collision area
        this.hitboxDefaultX = collisionArea.x;
        this.hitboxDefaultY = collisionArea.y;
        direction = Direction.DOWN;
        getGoblinImage();
    }


    /**
     * Defines the goblin's behavior (to be implemented by subclasses).
     * This method should contain movement and AI logic.
     */
    public abstract void getAction();


    /**
     * Loads (using loadImage) and assigns sprite images for goblin animations in different directions.
     * Called when initializing the goblin's appearance.
     */
    public void getGoblinImage(){
        up1    = Entity.loadImage("/goblin/orc_up_1.png");
        up2    = Entity.loadImage("/goblin/orc_up_2.png");
        down1  = Entity.loadImage("/goblin/orc_down_1.png");
        down2  = Entity.loadImage("/goblin/orc_down_2.png");
        right1 = Entity.loadImage("/goblin/orc_right_1.png");
        right2 = Entity.loadImage("/goblin/orc_right_2.png");
        left1  = Entity.loadImage("/goblin/orc_left_1.png");
        left2  = Entity.loadImage("/goblin/orc_left_2.png");
    }

    public void update(){
        onPath = true;
        inSight = true;

        getAction();
    }

    public BufferedImage getSpriteForDirection(){
        BufferedImage image = null;
        switch (drawDirection){
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
     * Draws the goblin on the screen based on its movement direction.
     *
     * @param g2 The graphics context used for rendering.
     */
    public void draw(Graphics2D g2){

        BufferedImage image = getSpriteForDirection();
        // Calculate screen position relative to the player's position
        int screenX = WorldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = WorldY - gp.Player.WorldY + gp.Player.screenY;

        // Only draw the goblin if it's within the player's visible area
        if (WorldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                WorldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                WorldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                WorldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }



    }
}

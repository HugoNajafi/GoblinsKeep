package com.goblinskeep.entity;

import java.awt.*;
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
    public Goblin(GamePanel gp, Player player, int WorldX, int WorldY) {
        super(gp, WorldX, WorldY);  // Pass values up to Entity constructor
        // this.gp = gp;
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
<<<<<<< HEAD
    
=======

>>>>>>> 2f5668deb91f01c239a4740167a2d9934926ac8c
    public void update(){
        onPath = true;
        inSight = true;

        getAction();
    }

    @Override
    protected Direction getEffectiveDirection() {
        return drawDirection; // Default behavior for Player
    }
}

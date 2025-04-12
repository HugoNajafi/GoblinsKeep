package com.goblinskeep.entity;

import java.awt.*;
import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.pathFinder.Circle;

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

    private int LOSradius = 120; // Line of sight radius

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
    
    public void update(){
        int xDistance = Math.abs(WorldX - gp.Player.WorldX);
        int yDistance = Math.abs(WorldY - gp.Player.WorldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        checkSight();

        if(!onPath && inSight){
            onPath = true;
        }
        //tile Distance is Distance for how long Goblins will follow you
        if(onPath && !inSight && tileDistance > 15){
            onPath = false;
        }
        getAction();
    }

    
    private void checkSight(){

        int screenX = WorldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = WorldY - gp.Player.WorldY + gp.Player.screenY;

        int PlayerX = gp.Player.screenX + gp.Player.hitboxDefaultX + (gp.Player.collisionArea.width/2);
        int PlayerY = gp.Player.screenY + gp.Player.hitboxDefaultY + (gp.Player.collisionArea.height/2);

        int goblinCenterX = (screenX + hitboxDefaultX + (collisionArea.width/2));
        int goblinCenterY = (screenY + hitboxDefaultY + (collisionArea.height/2));
        Circle LOSrange = new Circle(LOSradius, goblinCenterX, goblinCenterY);

        if(LOSrange.intersects(PlayerX, PlayerY)){
            inSight = true;
        }else{
            inSight = false;
        }
        
    }
    
    @Override
    protected Direction getEffectiveDirection() {
        if(onPath) {
            return drawDirection; // Goblin is on a path, use the current direction
        }else{
            return direction; // Default behavior for Player
        }
    }
}

package com.goblinskeep.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.pathFinder.Circle;

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
    public boolean viewBlocked;

    public int LOSradius = 120;


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
     * Loads and assigns sprite images for goblin animations in different directions.
     * Called when initializing the goblin's appearance.
     */
    public void getGoblinImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/goblin/orc_left_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update(){
        int xDistance = Math.abs(WorldX - gp.Player.WorldX);
        int yDistance = Math.abs(WorldY - gp.Player.WorldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
    
        // if(onPath == false && tileDistance < 5){
        //     onPath = true;
        // }
        // if(onPath == true && tileDistance > 20){
        //     onPath = false;
        // }
        checkLOS();

        if(!onPath && inSight){
            onPath = true;
        }
        if(onPath && !inSight && tileDistance > 10){
            onPath = false;
        }
        getAction();
    }

    /**
     * Checks, line of sight of Goblin, and if Player is within the lineofSight
     * Then the Goblin chases the player, if player goes behind an obstacle then 
     * The LOS (line of sight) is broken and the goblin will go to the last seen position
     */
    public void checkLOS(){
        // int PlayerLeftWorldX = gp.Player.WorldX + gp.Player.hitboxDefaultX;
        // int PlayerRightWorldX = gp.Player.WorldX + gp.Player.hitboxDefaultX + gp.Player.collisionArea.width;
        // int PlayerTopWorldY = gp.Player.WorldY + gp.Player.hitboxDefaultY;
        // int PlayerBottomWorldY = gp.Player.WorldY + gp.Player.hitboxDefaultY + gp.Player.collisionArea.height;

        // int PlayerLeftCol = PlayerLeftWorldX / gp.tileSize;
        // int PlayerRightCol = PlayerRightWorldX / gp.tileSize;
        // int PlayerTopRow = PlayerTopWorldY / gp.tileSize;
        // int PlayerBottomRow = PlayerBottomWorldY / gp.tileSize;
        int screenX = WorldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = WorldY - gp.Player.WorldY + gp.Player.screenY;

        int PlayerX = gp.Player.screenX + gp.Player.hitboxDefaultX + (gp.Player.collisionArea.width/2);
        int PlayerY = gp.Player.screenY + gp.Player.hitboxDefaultY + (gp.Player.collisionArea.height/2);

        int goblinCenterX = (screenX + hitboxDefaultX + (collisionArea.width/2));
        int goblinCenterY = (screenY + hitboxDefaultY + (collisionArea.height/2));
        Circle LOSrange = new Circle(LOSradius, goblinCenterX, goblinCenterY);
        // if(LOSrange.checkView(PlayerX, PlayerY)){
        //     viewBlocked = true;
        // }else{
        //     viewBlocked = false;
        // }
        LOSrange.checkView(PlayerX, PlayerY);
        if(LOSrange.intersects(PlayerX, PlayerY)){
            inSight = true;
        }else{
            inSight = false;
        }
        // for(int i=0; i< gp.Player.collisionArea.width; i++){
        //     //Scan the top row and check for collision
        //     if(LOSrange.intersects(PlayerLeftCol + i, PlayerTopRow)){
        //         inSight = true;
        //     }
        //     //Scan the bottom row and check for collision 
        //     if(LOSrange.intersects(PlayerLeftCol + i, PlayerBottomRow)){
        //         inSight = true;
        //     } 
        // }
        // for(int i=0; i< gp.Player.collisionArea.height; i++){
        //     //Scan the LeftSide and check for collision
        //     if(LOSrange.intersects(PlayerLeftCol, PlayerBottomRow-i)){
        //         inSight = true;
        //     }
        //     //Scan the Rightside and check for collision 
        //     if(LOSrange.intersects(PlayerRightCol, PlayerBottomRow - i)){
        //         inSight = true;
        //     } 
        // }
    }


    /**
     * Draws the goblin on the screen based on its movement direction.
     *
     * @param g2 The graphics context used for rendering.
     */
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        // Select the appropriate sprite based on movement direction
        switch (drawDirection){
            case Direction.UP:
                if(SpriteNum == 1){
                    image = up1;
                }
                if(SpriteNum == 2){
                    image = up2;
                }
                break;
            case Direction.DOWN:
                if(SpriteNum == 1){
                    image = down1;
                }
                if(SpriteNum == 2){
                    image = down2;
                }
                break;
            case Direction.LEFT:
                if(SpriteNum == 1){
                    image = left1;
                }
                if(SpriteNum == 2){
                    image = left2;
                }
                break;
            case Direction.RIGHT:
                if(SpriteNum == 1){
                    image = right1;
                }
                if(SpriteNum == 2){
                    image = right2;
                }
                break;
        }

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
        //Draw CollisionArea
        if(gp.debugMode){
            g2.setColor(Color.RED);
            g2.drawRect(screenX + hitboxDefaultX, screenY + hitboxDefaultY, 
            collisionArea.width, collisionArea.height);
        }
        //Draw Path
        if(gp.debugMode && onPath && !viewBlocked){
            g2.setColor(Color.GREEN);
            if(this instanceof RegularGoblin){
                RegularGoblin rg = (RegularGoblin)this;
                for(int i=0; i<rg.getPath().size(); i++){
                    int WorldX1 = rg.getPath().get(i).col * gp.tileSize;
                    int WorldY1 = rg.getPath().get(i).row * gp.tileSize;
                    int screenX1 = WorldX1 - gp.Player.WorldX + gp.Player.screenX;
                    int screenY1 = WorldY1 - gp.Player.WorldY + gp.Player.screenY;
    
                    g2.drawRect(screenX1, screenY1, gp.tileSize, gp.tileSize);
                }
            }
        }
        //Draw Line of Sight Radius
        if(gp.debugMode){
            // int x = gp.tileSize-(LOSradius/2);
            // int y = gp.tileSize-(LOSradius/2);
            // double Ax =Math.pow((screenX + hitboxDefaultX + (collisionArea.width/2)) - (gp.Player.screenX + gp.Player.hitboxDefaultX + (gp.Player.collisionArea.width/2)),2); 
            // double Bx = Math.pow((screenY + hitboxDefaultY + (collisionArea.height/2)) - gp.Player.screenY + gp.Player.hitboxDefaultY + (gp.Player.collisionArea.height/2),2);
            // double Distance =  Math.sqrt(Ax + Bx);
            // String result = String.format("%.2f", Distance);

            // int x = 10;
            // int y = 380;
            // g2.setFont(new Font("Arial", Font.BOLD, 20));
            // g2.setColor(Color.BLACK);
            // g2.drawString("GobDistance :" + result, x, y);
             
            
            
            g2.setColor(Color.BLUE);
            g2.drawOval(screenX + hitboxDefaultX + (collisionArea.width/2) - LOSradius, screenY + hitboxDefaultY + (collisionArea.height/2) - LOSradius, LOSradius*2, LOSradius*2);
            if(inSight){
                g2.drawLine(screenX + hitboxDefaultX + (collisionArea.width/2), screenY + hitboxDefaultY + (collisionArea.height/2), gp.Player.screenX + gp.Player.hitboxDefaultX + (gp.Player.collisionArea.width/2), gp.Player.screenY + gp.Player.hitboxDefaultY + (gp.Player.collisionArea.height/2));
            }

        }


    }
}

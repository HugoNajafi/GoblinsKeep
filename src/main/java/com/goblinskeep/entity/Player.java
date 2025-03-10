package com.goblinskeep.entity;
import com.goblinskeep.app.Direction;
import com.goblinskeep.keyboard.PlayerInputHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.objects.*;



public class Player extends Entity{
    public GamePanel gp;
    PlayerInputHandler PlayerInput;

    public final int screenX;
    public final int screenY;

    public Player(int startX, int startY, GamePanel gp, PlayerInputHandler PlayerInput) {
        super(startX, startY);  // Pass values up to GameObject constructor
        this.gp = gp;
        this.PlayerInput = PlayerInput;
        // Set a default direction
        this.direction = Direction.DOWN;

        // Set up the collision area
        this.collisionArea = new Rectangle(8, 16, 32, 32); // Adjust these values to fit your sprite
        this.hitboxDefaultX = 8;
        this.hitboxDefaultY = 16;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        getPlayerImage();
    }

    public void getPlayerImage() {

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/left2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        // Reset collision flag
        collisionOn = false;

        // find direction player intends to move toward
        if (PlayerInput.up) {
            direction = Direction.UP;
        } else if (PlayerInput.down) {
            direction = Direction.DOWN;
        } else if (PlayerInput.left) {
            direction = Direction.LEFT;
        } else if (PlayerInput.right) {
            direction = Direction.RIGHT;
        }
        // Check collision before moving
        gp.collisionChecker.checkTile(this);

        //check if collision with object before moving
        MainObject collisionObj = gp.collisionChecker.checkObjectCollision(this, true);
        handleObject(collisionObj);
        Entity target = gp.collisionChecker.playerCollisionWithEnemy(this, gp.getSmartGoblinIterator());
        if (target != null){
            collisionOn = true;
            gp.map.playerCollisionWithEnemy();
            System.out.println("collision detected between player and goblin");
        }

        // Move if no collision
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

        SpriteCounter++;
        if(SpriteCounter> 10){
            if(SpriteNum == 1){
                SpriteNum = 2;
            }
            else if(SpriteNum == 2){
                SpriteNum = 1;
            }
            SpriteCounter = 0;
        }

    }


    public void handleObject(MainObject collisionObject) {
        //if null then an object was not collected
        if (collisionObject != null){
            String objName = collisionObject.name;
            switch (objName){
                case "key":
                    gp.map.keyCollected();
                    gp.obj.removeObject(collisionObject.worldY,collisionObject.worldX);
                    break;
                case "bonus":
                    gp.map.collectedBonus();
                    gp.obj.removeObject(collisionObject.worldY,collisionObject.worldX);
                    break;
                case "trap":
                    gp.map.trapHit();
                    break;
                case "lever":
                    gp.map.leverTouched(collisionObject);
                    break;
                case "exit":
                    gp.map.exitTouched();
                    break;
            }
        }
    }


    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }

}

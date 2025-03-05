package com.goblinskeep.entity;
import com.goblinskeep.App.Direction;
import com.goblinskeep.Keyboard.PlayerInputHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;

import javax.imageio.ImageIO;

import com.goblinskeep.App.GamePanel;



public class Player extends Entity{
    public GamePanel gp;
    PlayerInputHandler PlayerInput;

    public final int screenX;
    public final int screenY;
    public int keysCollected = 0;

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

    public void getAction() {
        // Reset collision flag
        collisionOn = false;
        
        // // Store original position
        // int originalX = this.WorldX;
        // int originalY = this.WorldY;
        
        // Check collision first based on direction
        if(PlayerInput.up == true || PlayerInput.down == true || PlayerInput.left == true || PlayerInput.right == true){

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
            int objIndex = gp.collisionChecker.checkObject(this, true);
            handleObject(objIndex);
            Entity target = gp.collisionChecker.playerCollisionWithEnemy(this, gp.getSmartGoblinIterator());
            if (target != null){
                collisionOn = true;
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
    }
        public void getPlayerImage() {

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/up.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/up.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/idle.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/left2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);
        // // System.out.println("Player x: " + Player.getX() + "y: " + Player.getY());
        // g2.fillRect(this.WorldX, this.WorldY, gp.tileSize, gp.tileSize);

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
    public void handleObject(int index) {
        //if 999 then an object was not collected
        if (index != 999){
            String objName = gp.obj[index].name;
            System.out.println(objName);
            switch (objName){
                case "key":
                    keysCollected++;
                    gp.obj[index] = null;
                    break;
                case "lever":
                    gp.map.leverTouched();
                    break;
            }
        }
    }
}

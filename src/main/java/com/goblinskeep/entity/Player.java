package com.goblinskeep.entity;
import com.goblinskeep.App.Direction;
import com.goblinskeep.Keyboard.PlayerInputHandler;
import java.awt.Graphics2D;
import com.goblinskeep.App.GamePanel;


import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity{
    GamePanel gp;
    PlayerInputHandler PlayerInput;

    public Player(int startX, int startY, GamePanel gp, PlayerInputHandler PlayerInput) {
        super(startX, startY);  // Pass values up to GameObject constructor
        this.gp = gp;
        this.PlayerInput = PlayerInput;
    }

    public void setDefaultValues(){
        WorldX = 100;
        WorldY = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){

        switch (this.direction){
            case "up":
                direction = "up";
                WorldY -= speed;
            case "down":
                direction = "down";
                WorldY += speed;
            case "left":
                direction = "left";
                WorldX -= speed;
            case "right":
                direction = "right";
                WorldX += speed;
        }
    }



    public void getAction(){
        if(PlayerInput.up){
            // System.out.println("Pressing Up");
            // System.out.println(playerY);
            this.WorldY -= Direction.UP.getDy() * this.getSpeed();
        }
        else if(PlayerInput.down){
            // System.out.println("Pressing Down");
            // System.out.println(playerY);
            this.WorldY -= Direction.DOWN.getDy() * this.getSpeed();
        }
        else if(PlayerInput.left){
            // System.out.println("Pressing Left");
            // System.out.println(playerX);
            this.WorldX += Direction.LEFT.getDx() * this.getSpeed();
        }
        else if(PlayerInput.right){
            // System.out.println("Pressing Right");
            // System.out.println(playerX);
            this.WorldX += Direction.RIGHT.getDx() * this.getSpeed();
        }
        // System.out.println("Get Action!");
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        // System.out.println("Player x: " + Player.getX() + "y: " + Player.getY());
//        g2.fillRect(this.WorldX, this.WorldY, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction){
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }
        g2.drawImage(image, WorldX, WorldY, gp.tileSize, gp.tileSize, null);

    }
}

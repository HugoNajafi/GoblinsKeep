package com.goblinskeep.entity;

// import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
// import java.io.IOException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.goblinskeep.app.Direction;

// import javax.imageio.ImageIO;

import com.goblinskeep.app.GamePanel;

public abstract class Goblin extends Entity{
    GamePanel gp;
    Player player;
    protected Direction drawDirection = Direction.UP;

    public Goblin(){
        super();

    }

    public Goblin(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        // getGoblinImage();
    }
    public abstract void getAction();

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
    
    public void draw(Graphics2D g2){
        BufferedImage image = null;

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
//        g2.drawImage(image, WorldX, WorldY, gp.tileSize, gp.tileSize, null);
        int screenX = WorldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = WorldY - gp.Player.WorldY + gp.Player.screenY;

        if (WorldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                WorldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                WorldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                WorldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }

    }
}

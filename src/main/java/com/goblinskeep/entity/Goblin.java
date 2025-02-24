package com.goblinskeep.entity;

import java.awt.Color;
import java.awt.Graphics2D;
// import java.io.IOException;

// import javax.imageio.ImageIO;

import com.goblinskeep.App.GamePanel;

public abstract class Goblin extends Entity{
    GamePanel gp;
    Player player;

    public Goblin(){
        super();

    }

    public Goblin(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        // getGoblinImage();
    }
    // public void getGoblinImage(){
    //     try{
    //         up1 = ImageIO.read(getClass().getResourceAsStream("."));
    //     }
    //     catch(IOException e){
    //         e.printStackTrace();
    //     }
    // }
    
    public void draw(Graphics2D g2){
        g2.setColor(Color.green);
        // System.out.println("Player x: " + Player.getX() + "y: " + Player.getY());
        g2.fillRect(this.WorldX, this.WorldY, gp.tileSize, gp.tileSize);

    }
}

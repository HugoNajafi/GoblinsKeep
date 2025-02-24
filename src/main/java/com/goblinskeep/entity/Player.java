package com.goblinskeep.entity;
import com.goblinskeep.App.Direction;
import com.goblinskeep.Keyboard.PlayerInputHandler;
import java.awt.Graphics2D;
import com.goblinskeep.App.GamePanel;


import java.awt.Color;


public class Player extends Entity{
    GamePanel gp;
    PlayerInputHandler PlayerInput;

    public Player(int startX, int startY, GamePanel gp, PlayerInputHandler PlayerInput) {
        super(startX, startY);  // Pass values up to GameObject constructor
        this.gp = gp;
        this.PlayerInput = PlayerInput;
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
        g2.setColor(Color.white);
        // System.out.println("Player x: " + Player.getX() + "y: " + Player.getY());
        g2.fillRect(this.WorldX, this.WorldY, gp.tileSize, gp.tileSize);
        
    }
}

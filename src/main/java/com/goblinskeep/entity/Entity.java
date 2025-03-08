package com.goblinskeep.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.goblinskeep.app.Direction;

public abstract class Entity {
    public int WorldX, WorldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand1, stand2;
    public Direction direction;

    protected int SpriteCounter = 0;
    protected int SpriteNum = 1;

    public Rectangle collisionArea;
    public int hitboxDefaultX = 48;
    public int hitboxDefaultY = 48;
    public boolean collisionOn;

    public Entity(){
        this.speed = 1;
        this.collisionArea = new Rectangle(0, 0, hitboxDefaultX, hitboxDefaultY); // Adjust size as needed
        this.hitboxDefaultX = 0;
        this.hitboxDefaultY = 0;
        this.collisionOn = false;
    }

    public Entity(int WorldX, int WorldY){
        this.WorldX = WorldX;
        this.WorldY = WorldY;
        this.speed = 1;
        this.collisionArea = new Rectangle(0, 0, hitboxDefaultX, hitboxDefaultY); // Adjust size as needed
        this.hitboxDefaultX = 0;
        this.hitboxDefaultY = 0;
        this.collisionOn = false;
    }

    public int getX(){
        return this.WorldX;
    }

    public int getY(){
        return this.WorldY;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public void setX(int NewX){
        this.WorldX = NewX;
    }

    public void setY(int NewY){
        this.WorldY = NewY;
    }
    
}

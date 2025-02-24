package com.goblinskeep.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int WorldX, WorldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand1, stand2;
    public String direction;

    protected int SpriteCounter = 0;
    protected int SpriteNum = 1;

    public Rectangle collisionArea;
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean collisionOn;

    public Entity(){
        this.speed = 1;
    }

    public Entity(int WorldX, int WorldY){
        this.WorldX = WorldX;
        this.WorldY = WorldY;
        this.speed = 1;
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

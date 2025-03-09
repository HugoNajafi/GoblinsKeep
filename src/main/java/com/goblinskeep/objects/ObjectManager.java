package com.goblinskeep.objects;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Entity;

import java.awt.*;
import java.util.HashMap;

public class ObjectManager {
    public HashMap<String, MainObject> anObject;
    public GamePanel gp;

    public ObjectManager(GamePanel gp){
        anObject = new HashMap<>();
        this.gp = gp;
    }
    private String generateKey(int x, int y){
        return x + "," + y;
    }

    public void addObject(int x, int y, MainObject newObject){
        anObject.put(generateKey(x,y), newObject);
        newObject.worldY = x * gp.tileSize;
        newObject.worldX = y * gp.tileSize;
    }

    public MainObject findObject(int playerX, int playerY){
        return anObject.get(generateKey(playerX, playerY));
    }

    public void removeObject(int x, int y) {
        String key = generateKey(x/gp.tileSize,y/gp.tileSize);
        if(anObject.containsKey(key)) {
            anObject.remove(key);
        }
    }

    public void draw(Graphics2D g2, GamePanel gp){
        for(MainObject i: anObject.values()){
            i.draw(g2,gp);
        }
    }
}
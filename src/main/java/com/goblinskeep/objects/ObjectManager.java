package com.goblinskeep.objects;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapGenerator;
import com.goblinskeep.entity.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ObjectManager {
    public HashMap<String, MainObject> anObject;
    public GamePanel gp;
    public MapGenerator map;

    public ObjectManager(GamePanel gp){
        anObject = new HashMap<>();
        this.gp = gp;
        this.map = gp.map;
    }
    private String generateKey(int x, int y){
        return x + "," + y;
    }

    public void addObject(int x, int y, MainObject newObject){
        anObject.put(generateKey(x,y), newObject);
        newObject.worldY = y * gp.tileSize;
        newObject.worldX = x * gp.tileSize;
    }

    public MainObject findObject(int playerX, int playerY){
        return anObject.get(generateKey(playerX, playerY));
    }

    public Exit findDoor(){
        for(MainObject i: anObject.values()){
            if (i instanceof Exit)
            {
                return ((Exit) i);
            }
        }
        return null;
    }

    public Lever findLever(){
        for(MainObject i: anObject.values()){
            if (i instanceof Lever)
            {
                return ((Lever) i);
            }
        }
        return null;
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
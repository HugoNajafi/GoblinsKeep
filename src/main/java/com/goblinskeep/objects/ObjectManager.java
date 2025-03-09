package com.goblinskeep.objects;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Entity;

import java.awt.*;
import java.util.HashMap;

public class ObjectManager {
    public HashMap<String, MainObject> obj;

    public ObjectManager(){
        obj = new HashMap<>();
    }
    private String generateKey(int x, int y){
        return x + "," + y;
    }

    public void addObject(int x, int y, MainObject newObject){
        obj.put(generateKey(x,y), newObject);
        newObject.worldY = x;
        newObject.worldX = y;
    }

    public MainObject findObject(int playerX, int playerY){
        return obj.get(generateKey(playerX, playerY));
    }

    public void removeObject(int x, int y) {
        String key = generateKey(x,y);
        if(obj.containsKey(key)) {
            obj.remove(key);
        }
    }

    public void draw(Graphics2D g2, GamePanel gp){
        int worldRow = 0;
        int worldCol = 0;

        //loop terminates after the boundary is reached
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
            int screenY = worldY - gp.Player.WorldY + gp.Player.screenY;
            String key =generateKey(worldCol, worldRow);

            //camera logic, draw only around the player
            if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                    worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                    worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                    worldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY &&
                    obj.containsKey(key)){
                g2.drawImage(findObject(worldCol,worldRow).image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol += 1;

            if (worldCol >= gp.maxWorldCol){
                worldCol = 0;
                worldRow++;//once you finish constructing one row, you'll go the next one
            }
        }

    }
}
package com.goblinskeep.entity;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.Direction;

public class CollisionChecker {
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.WorldX + entity.collisionArea.x;
        int entityRightWorldX = entity.WorldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.WorldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.WorldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        
        // Calculate the position after potential movement
        int speed = entity.getSpeed();
        Direction dir = entity.direction;
        
        switch (dir) {
            case UP:
                // Calculate the new row after moving up
                entityTopRow = (entityTopWorldY - dir.getDy() * speed) / gp.tileSize;
                // Check two points: top-left and top-right corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case DOWN:
                // Calculate the new row after moving down (note: dy is negative for DOWN)
                entityBottomRow = (entityBottomWorldY - dir.getDy() * speed) / gp.tileSize;
                // Check two points: bottom-left and bottom-right corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case LEFT:
                // Calculate the new column after moving left
                entityLeftCol = (entityLeftWorldX + dir.getDx() * speed) / gp.tileSize;
                // Check two points: left-top and left-bottom corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case RIGHT:
                // Calculate the new column after moving right
                entityRightCol = (entityRightWorldX + dir.getDx() * speed) / gp.tileSize;
                // Check two points: right-top and right-bottom corners
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
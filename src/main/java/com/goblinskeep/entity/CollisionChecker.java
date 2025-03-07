package com.goblinskeep.entity;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.Direction;

import java.util.Iterator;

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

    public int checkObject(Entity entity, boolean player){
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++){
            if (gp.obj[i] == null) {
                continue;
            }
            //need to test if this works the same
            entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
            entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

            //get world position of the collision area for the Objects
            gp.obj[i].collisionArea.x = gp.obj[i].worldX + gp.obj[i].defaultCollisionAreaX;
            gp.obj[i].collisionArea.y = gp.obj[i].worldY + gp.obj[i].defaultCollisionAreaY;

            //check if there is a collision based on the way the Entity is facing
            switch (entity.direction){
                case Direction.UP:
                    entity.collisionArea.y -= entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case Direction.LEFT:
                    entity.collisionArea.x -= entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case Direction.DOWN:
                    entity.collisionArea.x += entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case Direction.RIGHT:
                    entity.collisionArea.y += entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
            }

            //reset the collision area positions for future use
            //uncomment if broken
            entity.collisionArea.x = entity.hitboxDefaultX;
            entity.collisionArea.y = entity.hitboxDefaultY;
            gp.obj[i].collisionArea.x = gp.obj[i].defaultCollisionAreaX;
            gp.obj[i].collisionArea.y = gp.obj[i].defaultCollisionAreaY;
        }
        return index;
    }

    public Entity playerCollisionWithEnemy(Entity entity, Iterator<? extends Entity> targets){
        while (targets.hasNext()){
            Entity target = targets.next();
            if (target == null){
                continue;
            }
            entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
            entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

            //get world position of the collision area for the Objects
            target.collisionArea.x = target.WorldX + target.hitboxDefaultX;
            target.collisionArea.y = target.WorldY + target.hitboxDefaultY;


            switch (entity.direction){
                case Direction.UP:
                    entity.collisionArea.y -= entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.LEFT:
                    entity.collisionArea.x -= entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.DOWN:
                    entity.collisionArea.x += entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.RIGHT:
                    entity.collisionArea.y += entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
            }
            //reset the collision area positions for future use
            //uncomment if broken
            entity.collisionArea.x = entity.hitboxDefaultX;
            entity.collisionArea.y = entity.hitboxDefaultY;
            target.collisionArea.x = target.hitboxDefaultX;
            target.collisionArea.y = target.hitboxDefaultY;
        }
        return null;

    }
    public boolean checkPlayer(Entity entity){
        entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
        entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

        //get world position of the collision area for the Objects
        gp.Player.collisionArea.x = gp.Player.WorldX + gp.Player.hitboxDefaultX;
        gp.Player.collisionArea.y = gp.Player.WorldY + gp.Player.hitboxDefaultY;


        switch (entity.direction){
            case Direction.UP:
                entity.collisionArea.y -= entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.LEFT:
                entity.collisionArea.x -= entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.DOWN:
                entity.collisionArea.x += entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.RIGHT:
                entity.collisionArea.y += entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea)){
                    return true;
                }
                break;
        }
        //reset the collision area positions for future use
        //uncomment if broken
        entity.collisionArea.x = entity.hitboxDefaultX;
        entity.collisionArea.y = entity.hitboxDefaultY;
        gp.Player.collisionArea.x = gp.Player.hitboxDefaultX;
        gp.Player.collisionArea.y = gp.Player.hitboxDefaultY;
        return false;
    }


    private int handleEntityCollision(Entity entity, boolean player, int index){
        if (gp.obj[index].collision){
            entity.collisionOn = true;
        }
        if (player){
            return index;
        }
        return 999;
    }
}
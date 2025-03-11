package com.goblinskeep.entity;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.Direction;
import com.goblinskeep.objects.*;

import java.awt.*;
import java.util.Iterator;

public class CollisionChecker {
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.WorldX + entity.hitboxDefaultX;
        int entityRightWorldX = entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width;
        int entityTopWorldY = entity.WorldY + entity.hitboxDefaultY;
        int entityBottomWorldY = entity.WorldY + entity.hitboxDefaultY + entity.collisionArea.height;

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
                    if (entity.collisionArea.intersects(target.collisionArea) || isTouchingEdges(entity.collisionArea, target.collisionArea)){
                        if (target.collisionOn){
                            System.out.println("yo");
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.LEFT:
                    entity.collisionArea.x -= entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea) || isTouchingEdges(entity.collisionArea, target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.DOWN:
                    entity.collisionArea.y += entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea) || isTouchingEdges(entity.collisionArea, target.collisionArea)){
                        if (target.collisionOn){
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
                case Direction.RIGHT:
                    entity.collisionArea.x += entity.speed;
                    if (entity.collisionArea.intersects(target.collisionArea) || isTouchingEdges(entity.collisionArea, target.collisionArea)){
                        if (target.collisionOn){
//                            System.out.print("Player: " + (entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width));
//                            System.out.println("  Goblin: " + (target.WorldX + target.hitboxDefaultX));
                            entity.collisionOn = true;
                            return target;
                        }
                    }
                    break;
            }
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
                if (entity.collisionArea.intersects(gp.Player.collisionArea) || isTouchingEdges(entity.collisionArea, gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.LEFT:
                entity.collisionArea.x -= entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea) || isTouchingEdges(entity.collisionArea, gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.DOWN:
                entity.collisionArea.y += entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea) || isTouchingEdges(entity.collisionArea, gp.Player.collisionArea)){
                    return true;
                }
                break;
            case Direction.RIGHT:
                entity.collisionArea.x += entity.speed;
                if (entity.collisionArea.intersects(gp.Player.collisionArea) || isTouchingEdges(entity.collisionArea, gp.Player.collisionArea)){
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isTouchingEdges(Rectangle rect1, Rectangle rect2) {
        return (rect1.x + rect1.width == rect2.x && rect1.y < rect2.y + rect2.height && rect1.y + rect1.height > rect2.y) ||  // Right touch
                (rect2.x + rect2.width == rect1.x && rect1.y < rect2.y + rect2.height && rect1.y + rect1.height > rect2.y) ||  // Left touch
                (rect1.y + rect1.height == rect2.y && rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x) ||  // Bottom touch
                (rect2.y + rect2.height == rect1.y && rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x);   // Top touch
//        return false;

    }

    public void checkEnemyCollision(Entity entity, Iterator<? extends Entity> otherEntities) {
        entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
        entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

        // Calculate potential new position based on direction
        Rectangle potentialPosition = new Rectangle(entity.collisionArea);
        switch (entity.direction) {
            case Direction.UP:
                potentialPosition.y -= entity.speed;
                break;
            case Direction.DOWN:
                potentialPosition.y += entity.speed;
                break;
            case Direction.LEFT:
                potentialPosition.x -= entity.speed;
                break;
            case Direction.RIGHT:
                potentialPosition.x += entity.speed;
                break;
        }

        // Check collision with all other entities
        while (otherEntities.hasNext()) {
            Entity other = otherEntities.next();
            if (other == null || other == entity) {
                continue;
            }

            // Update other entity's collision area
            other.collisionArea.x = other.WorldX + other.hitboxDefaultX;
            other.collisionArea.y = other.WorldY + other.hitboxDefaultY;

            // Check if potential movement would cause collision
            if (potentialPosition.intersects(other.collisionArea) ||
                    isTouchingEdges(potentialPosition, other.collisionArea)) {
//                entity.collisionOn = true;

                // Instead of just random direction, use a smarter approach:
                // Calculate direction away from the other entity
                int dx = entity.WorldX - other.WorldX;
                int dy = entity.WorldY - other.WorldY;

                // Determine best escape direction based on relative positions
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Horizontal separation is larger, move horizontally
                    entity.direction = dx > 0 ? Direction.RIGHT : Direction.LEFT;
                } else {
                    // Vertical separation is larger, move vertically
                    entity.direction = dy > 0 ? Direction.DOWN : Direction.UP;
                }
                break;
            }
        }
    }


    public MainObject checkObjectCollision(Entity entity, boolean player){

        MainObject returnObject = null;

        for(MainObject object: gp.obj.anObject.values()){

            entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
            entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

            object.collisionArea.x = object.worldX + object.defaultCollisionAreaX;
            object.collisionArea.y = object.worldY + object.defaultCollisionAreaY;

            switch (entity.direction){
                case Direction.UP:
                    entity.collisionArea.y -= entity.speed;
                    if (entity.collisionArea.intersects(object.collisionArea)){
                        returnObject = handleEntityCollision(entity, player,object);
                    }
                    break;
                case Direction.DOWN:
                    entity.collisionArea.x += entity.speed;
                    if (entity.collisionArea.intersects(object.collisionArea)){
                        returnObject = handleEntityCollision(entity, player,object);
                    }
                    break;
                case Direction.LEFT:
                    entity.collisionArea.x -= entity.speed;
                    if (entity.collisionArea.intersects(object.collisionArea)){
                        returnObject = handleEntityCollision(entity, player,object);
                    }
                    break;
                case Direction.RIGHT:
                    entity.collisionArea.y += entity.speed;
                    if (entity.collisionArea.intersects(object.collisionArea)){
                        returnObject = handleEntityCollision(entity, player,object);
                    }
                    break;
            }

            entity.collisionArea.x = entity.hitboxDefaultX;
            entity.collisionArea.y = entity.hitboxDefaultY;
            object.collisionArea.x = object.defaultCollisionAreaX;
            object.collisionArea.y = object.defaultCollisionAreaY;
        }
        return returnObject;
    }

    private MainObject handleEntityCollision(Entity entity, boolean player, MainObject object){
        if (object.collision){
            entity.collisionOn = true;
        }
        if (player){
            return object;
        }
        return null;
    }
}
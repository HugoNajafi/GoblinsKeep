package com.goblinskeep.entity;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.Direction;
import com.goblinskeep.objects.*;

import java.awt.*;
import java.util.Iterator;

/**
 * Handles collision detection for entities, including players, enemies, and objects.
 */
public class CollisionChecker {
    /** Reference to the main game panel. */
    private GamePanel gp;


    /**
     * Constructs a collision checker for handling entity interactions.
     *
     * @param gp The main game panel.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    /**
     * Checks if an entity collides with a solid tile based on its direction.
     *
     * @param entity The entity to check for tile collision.
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.WorldX + entity.hitboxDefaultX;
        int entityRightWorldX = entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width;
        int entityTopWorldY = entity.WorldY + entity.hitboxDefaultY;
        int entityBottomWorldY = entity.WorldY + entity.hitboxDefaultY + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        //default collision tiles
        int tileNum1 = 0;
        int tileNum2 = 0;

        // Get movement direction and speed
        int speed = entity.getSpeed();
        Direction dir = entity.direction;

        //Check collision based on direction
        switch (dir) {
            case UP:
                // Calculate the new row after moving up
                entityTopRow = (entityTopWorldY - dir.getDy() * speed) / gp.tileSize;
                // Check two points: top-left and top-right corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                break;
            case DOWN:
                // Calculate the new row after moving down (note: dy is negative for DOWN)
                entityBottomRow = (entityBottomWorldY - dir.getDy() * speed) / gp.tileSize;
                // Check two points: bottom-left and bottom-right corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            case LEFT:
                // Calculate the new column after moving left
                entityLeftCol = (entityLeftWorldX + dir.getDx() * speed) / gp.tileSize;
                // Check two points: left-top and left-bottom corners
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                break;
            case RIGHT:
                // Calculate the new column after moving right
                entityRightCol = (entityRightWorldX + dir.getDx() * speed) / gp.tileSize;
                // Check two points: right-top and right-bottom corners
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
        }
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }


    /**
     * Checks for a collision between a player and an enemy.
     * notifies map if a collision has occurred for it to handle.
     *
     * @param entity  The player entity.
     * @param targets An iterator over enemy entities.
     */
    public void playerCollisionWithEnemy(Entity entity, Iterator<? extends Entity> targets){

        while (targets.hasNext()){
            Entity target = targets.next();
            if (target == null){
                continue;
            }

            //update collision areas
            entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
            entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;
            //get world position of the collision area for the Objects
            target.collisionArea.x = target.WorldX + target.hitboxDefaultX;
            target.collisionArea.y = target.WorldY + target.hitboxDefaultY;

            // Check collision based on movement direction
            updateCollisionArea(entity);
            if (entity.collisionArea.intersects(target.collisionArea)){
                //handle collision by changing player to immovable and letting map handle the collision
                entity.collisionOn = true;
                gp.map.playerCollisionWithEnemy();;
            }
        }
    }


    /**
     * Checks if an entity is colliding with the player.
     *
     * @param entity The entity to check.
     * @return True if the entity collides with the player, false otherwise.
     */
    public boolean checkPlayer(Entity entity){
        entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
        entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;
        //get world position of the collision area for the Objects
        gp.Player.collisionArea.x = gp.Player.WorldX + gp.Player.hitboxDefaultX;
        gp.Player.collisionArea.y = gp.Player.WorldY + gp.Player.hitboxDefaultY;

        updateCollisionArea(entity);
        return entity.collisionArea.intersects(gp.Player.collisionArea);
    }


    /**
     * Checks for collisions between enemies to prevent them from overlapping.
     *
     * @param entity        The enemy entity to check.
     * @param otherEntities An iterator over other enemy entities.
     */
    public void checkEnemyCollision(Entity entity, Iterator<? extends Entity> otherEntities) {
        entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
        entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

        // Calculate potential new position based on direction
        updateCollisionArea(entity);

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
            if (entity.collisionArea.intersects(other.collisionArea)) {

                // Instead of just random direction, use a smarter approach:
                // Calculate direction away from the other entity
                int dx = entity.WorldX - other.WorldX;
                int dy = entity.WorldY - other.WorldY;

                // Determine best escape direction based on relative positions
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Horizontal separation is larger, move horizontally
                    if (dx > 0) {
                        entity.direction = Direction.RIGHT;
                    } else {
                        entity.direction = Direction.LEFT;
                    }

                } else {
                    // Vertical separation is larger, move vertically
                    if (dy > 0) {
                        entity.direction = Direction.DOWN;
                    } else {
                        entity.direction = Direction.UP;
                    }
                }
                break;
            }
        }
    }


    /**
     * Checks if an entity collides with any interactive object in the game.
     *
     * @param entity The entity to check for collisions.
     * @param player A boolean indicating whether the entity is a player (true) or not (false).
     * @return The object that the entity collides with, or null if no collision occurs.
     */
    public MainObject checkObjectCollision(Entity entity, boolean player){

        MainObject returnObject = null;

        // Iterate through all objects in the game world
        for(MainObject object: gp.obj.anObject.values()){

            // Set entity's collision area position
            entity.collisionArea.x = entity.WorldX + entity.hitboxDefaultX;
            entity.collisionArea.y = entity.WorldY + entity.hitboxDefaultY;

            // Set object's collision area position
            object.collisionArea.x = object.worldX + object.defaultCollisionAreaX;
            object.collisionArea.y = object.worldY + object.defaultCollisionAreaY;

            // Check collision based on entity's movement direction
            updateCollisionArea(entity);

            // Check if the entity collides with the object
            if (entity.collisionArea.intersects(object.collisionArea)) {
                returnObject = handleEntityCollision(entity, player, object);
            }

            // Reset collision areas to their default positions
            entity.collisionArea.x = entity.hitboxDefaultX;
            entity.collisionArea.y = entity.hitboxDefaultY;
            object.collisionArea.x = object.defaultCollisionAreaX;
            object.collisionArea.y = object.defaultCollisionAreaY;
        }
        return returnObject;
    }


    /**
     * Handles collision between an entity and an object.
     *
     * @param entity The entity that collided with the object.
     * @param player A boolean indicating if the entity is the player (true) or another entity (false).
     * @param object The object that was collided with.
     * @return The collided object if the entity is a player; otherwise, returns null.
     */
    private MainObject handleEntityCollision(Entity entity, boolean player, MainObject object){
        // If the object has collision enabled, set the entity's collision flag
        if (object.collision){
            entity.collisionOn = true;
        }

        // If the entity is a player, return the collided object for interaction
        if (player){
            return object;
        }

        return null;
    }


    /**
     * Updates the collision area based on the entity's direction and speed.
     *
     * @param entity The entity whose collision area is to be updated.
     */
    private void updateCollisionArea(Entity entity) {
        switch (entity.direction) {
            case UP:
                entity.collisionArea.y -= entity.speed;
                break;
            case DOWN:
                entity.collisionArea.y += entity.speed;
                break;
            case LEFT:
                entity.collisionArea.x -= entity.speed;
                break;
            case RIGHT:
                entity.collisionArea.x += entity.speed;
                break;
        }
    }
}
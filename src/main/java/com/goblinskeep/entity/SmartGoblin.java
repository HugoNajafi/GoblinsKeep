package com.goblinskeep.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;


public class SmartGoblin extends Goblin {

    public SmartGoblin(GamePanel gp, Player player) {
        super(gp, player);

        this.speed = 2; // Adjust the speed as needed

            // Initialize direction to avoid null pointer exceptions
        this.direction = Direction.DOWN; // Give it a default direction
    
        // Load the goblin images
        getGoblinImage();
    

        // Register this goblin with the gamestate

    }
    
    @Override
    public void getAction() {
        int goalCol = (gp.Player.WorldX + gp.Player.hitboxDefaultX) / gp.tileSize;
        int goalRow = (gp.Player.WorldY + gp.Player.hitboxDefaultY) / gp.tileSize;

        gp.pathFinder.searchPath(goalCol, goalRow, this);
        drawDirection = direction;
        
        // Move along the path
        moveAlongPath();
    }
    
    private void moveAlongPath() {


        collisionOn = false;

        
        // Check collision
        gp.collisionChecker.checkTile(this);

        //ensure that the goblin does not collide with other goblins
        gp.collisionChecker.checkEnemyCollision(this, gp.getSmartGoblinIterator());
        //comment this out for collisions between player to not work properly
        if (gp.collisionChecker.checkPlayer(this)){
            collisionOn = true;
            gp.map.playerCollisionWithEnemy();
        }
        interactPlayer(47);
        // Move if no collision
        if (!collisionOn) {
            if (direction == Direction.UP) {
                this.WorldY -= Direction.UP.getDy() * this.getSpeed();
            } else if (direction == Direction.DOWN) {
                this.WorldY -= Direction.DOWN.getDy() * this.getSpeed();
            } else if (direction == Direction.LEFT) {
                this.WorldX += Direction.LEFT.getDx() * this.getSpeed();
            } else if (direction == Direction.RIGHT) {
                this.WorldX += Direction.RIGHT.getDx() * this.getSpeed();
            }
            SpriteCounter++;
            if(SpriteCounter> 10){
                if(SpriteNum == 1){
                    SpriteNum = 2;
                }
                else if(SpriteNum == 2){
                    SpriteNum = 1;
                }
                SpriteCounter = 0;
            }
        }
    }

    //case where goblins collisions
    public void interactPlayer(int hitDistance) {

        double playerMiddleX = gp.Player.WorldX + gp.Player.hitboxDefaultX + ((double) gp.Player.collisionArea.width / 2);
        double playerMiddleY = gp.Player.WorldY + gp.Player.hitboxDefaultY+ ((double) gp.Player.collisionArea.height / 2);
        double goblinMiddleX = this.WorldX + this.hitboxDefaultX + ((double) this.collisionArea.width / 2);
        double goblinMiddleY = this.WorldY + this.hitboxDefaultY + ((double) this.collisionArea.height / 2);

        double edgeY = Math.abs(playerMiddleY - goblinMiddleY);
        double edgeX = Math.abs(playerMiddleX - goblinMiddleX);

        double distanceApart = Math.hypot(edgeY, edgeX);
        if (distanceApart <= hitDistance)
        {
            gp.map.playerCollisionWithEnemy();
        }
    }


}
package com.goblinskeep.entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.pathFinder.Node;

/**
 * Represents a regular goblin entity that can move randomly or follow a path.
 */
public class RegularGoblin extends Goblin {

    /**
     * The path that the goblin follows.
     */
    public ArrayList<Node> myPath = new ArrayList<>();

    /**
     * Constructs a RegularGoblin with the specified GamePanel and Player.
     *
     * @param gp the GamePanel instance
     * @param player the Player instance
     */
    public RegularGoblin(GamePanel gp, Player player, int WorldX, int WorldY) {
        super(gp, player, WorldX, WorldY);
        collisionArea = new Rectangle(8, 16, 32, 32); // Set collision area
        hitboxDefaultX = 8;
        hitboxDefaultY = 16;

    }

    /**
     * Determines the action of the regular goblin, including random movement or pathfinding.
     */
    @Override
    public void getAction() {
        if(onPath){
            followPlayer();
        }else{
            RandomMovement();
        }
        //Move along Path
        move();
    }

    private void followPlayer(){
        //find player coordinate and get direction to it using pathfinding
        Point goalCoordinates = gp.Player.getCenterTileCoordinates();
        gp.pathFinder.searchPath(goalCoordinates.x, goalCoordinates.y, this);
        drawDirection = direction;
    }
    

    private void RandomMovement() {
        // Randomly change direction
        actionLockCounter++;
    
        //How often the goblin changes direction
        //less means faster direction change, more means slower
        if(actionLockCounter == 60){

            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = Direction.UP;
            }
            else if(i >= 25 && i <= 50){
                direction = Direction.DOWN;
            }
            else if(i >= 50 && i<= 75){
                direction = Direction.LEFT;
            }
            else{
                direction = Direction.RIGHT;
            }
            drawDirection = direction;
            actionLockCounter = 0;
        }
    }
    /**
     * Moves the regular goblin along the path found by the pathfinder.
     */
    private void move(){
        collisionOn = false;
        checkCollisions();
        moveEntityTowardDirection();
    }


    /**
     * Returns the path that the goblin follows.
     *
     * @return the path as an ArrayList of Nodes
     */
    public ArrayList<Node> getPath() {
        return myPath;
    }

    private void checkCollisions(){
        gp.collisionChecker.handleEnemyCollisions(this);
        if(collisionOn && !onPath){
            direction = direction.getOpposite(direction);
        }
        interactPlayer(47);
    }



    /**
     * Interacts with the player if within a specified range.
     *
     * @param hitDistance the range within which the goblin interacts with the player
     */
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

package com.goblinskeep.entity;

import java.awt.*;
import java.util.ArrayList;
// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.List;
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
    public RegularGoblin(GamePanel gp, Player player) {
        super(gp, player);
        collisionArea = new Rectangle(8, 16, 32, 32); // Set collision area
        hitboxDefaultX = 8;
        hitboxDefaultY = 16;
        
        // // Set default direction and collisionArea
        // this.direction = Direction.DOWN;

    }

    /**
     * Determines the action of the regular goblin, including random movement or pathfinding.
     */
    @Override
    public void getAction() {
        int goalCol = (gp.Player.WorldX + gp.Player.hitboxDefaultX +
        (gp.Player.collisionArea.width / 2)) / gp.tileSize;
        int goalRow = (gp.Player.WorldY + gp.Player.hitboxDefaultY +
        (gp.Player.collisionArea.height / 2)) / gp.tileSize;

        gp.pathFinder.searchPath(goalCol, goalRow, this);
        drawDirection = direction;

        //Move along Path
        moveAlongPath();
    }

    /**
     * Moves the regular goblin along the path found by the pathfinder.
     */
    private void moveAlongPath(){
        collisionOn = false;

        gp.collisionChecker.checkTile(this);

        gp.collisionChecker.checkEnemyCollision(this, gp.getGoblinIterator());
        if(gp.collisionChecker.checkPlayer(this)){
            collisionOn = true;
            gp.map.playerCollisionWithEnemy();
        }
        interactPlayer(47);

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


    /**
     * Returns the path that the goblin follows.
     *
     * @return the path as an ArrayList of Nodes
     */
    public ArrayList<Node> getPath() {
        return myPath;
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

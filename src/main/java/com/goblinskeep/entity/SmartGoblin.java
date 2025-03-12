package com.goblinskeep.entity;


import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;


/**
 * Represents a smart goblin entity that uses pathfinding to follow the player.
 */
public class SmartGoblin extends Goblin {

    /**
     * Constructs a SmartGoblin with the specified GamePanel and Player.
     *
     * @param gp the GamePanel instance
     * @param player the Player instance
     */
    public SmartGoblin(GamePanel gp, Player player) {
        super(gp, player);

        this.speed = 2; // Adjust the speed as needed

            // Initialize direction to avoid null pointer exceptions
        this.direction = Direction.DOWN; // Give it a default direction
    
        // Load the goblin images
        getGoblinImage();
    

    }

    /**
     * Determines the action of the smart goblin, including pathfinding to the player.
     */
    @Override
    public void getAction() {
        int goalCol = (gp.Player.WorldX + gp.Player.hitboxDefaultX) / gp.tileSize;
        int goalRow = (gp.Player.WorldY + gp.Player.hitboxDefaultY) / gp.tileSize;

        gp.pathFinder.searchPath(goalCol, goalRow, this);
        //set the initial direction to the draw direction in case direction changes due to collision
        drawDirection = direction;
        
        // Move along the path
        moveAlongPath();
    }

    /**
     * Moves the smart goblin along the path found by the pathfinder.
     */
    private void moveAlongPath() {

        //collision will be set to true if a collision occurs
        collisionOn = false;

        
        // Check collision with tiles
        gp.collisionChecker.checkTile(this);

        //ensure that the goblin does not collide with other goblins
        gp.collisionChecker.checkEnemyCollision(this, gp.getSmartGoblinIterator());
        //check if collided with player
        if (gp.collisionChecker.checkPlayer(this)){
            collisionOn = true;
            gp.map.playerCollisionWithEnemy();
        }
        //check if player very close but pathfinding cannot reach
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

    /**
     * Interacts with the player if within a specified range.
     *
     * @param range the range within which the goblin interacts with the player
     */
    public void interactPlayer(int range) {
        double playerMiddleX = gp.Player.WorldX + gp.Player.hitboxDefaultX + ((double) gp.Player.collisionArea.width / 2);
        double playerMiddleY = gp.Player.WorldY + gp.Player.hitboxDefaultY+ ((double) gp.Player.collisionArea.height / 2);
        double goblinMiddleX = this.WorldX + this.hitboxDefaultX + ((double) this.collisionArea.width / 2);
        double goblinMiddleY = this.WorldY + this.hitboxDefaultY + ((double) this.collisionArea.height / 2);
        double edgeY = Math.abs(playerMiddleY - goblinMiddleY);
        double edgeX = Math.abs(playerMiddleX - goblinMiddleX);
        double distanceApart = Math.hypot(edgeY, edgeX);
        if (distanceApart <= range) {
            gp.map.playerCollisionWithEnemy();
        }
    }


}
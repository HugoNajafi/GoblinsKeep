package com.goblinskeep.entity;

import java.util.ArrayList;
// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Random;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;

public class RegularGoblin extends Goblin {
    public ArrayList<Node> myPath = new ArrayList<>();
    
    public RegularGoblin(GamePanel gp, Player player) {
        super(gp, player);
        
        // // Set default direction and collisionArea
        // this.direction = Direction.DOWN;

    }
    
    @Override
    public void getAction() {
        if(onPath){
            int goalCol = (gp.Player.WorldX + gp.Player.hitboxDefaultX + 
            (gp.Player.collisionArea.width / 2)) / gp.tileSize;
            int goalRow = (gp.Player.WorldY + gp.Player.hitboxDefaultY + 
            (gp.Player.collisionArea.height / 2)) / gp.tileSize;
             
            gp.pathFinder.searchPath(goalCol, goalRow, this);    
            drawDirection = direction;

  
            
        }
        else{
            randomMovement();
        }
        //Move along Path
        moveAlongPath();
    }

    private void moveAlongPath(){
        collisionOn = false;

        gp.collisionChecker.checkTile(this);

        gp.collisionChecker.checkEnemyCollision(this, gp.getGoblinIterator());
        if(gp.collisionChecker.checkPlayer(this)){
            collisionOn = true;
            gp.map.playerCollisionWithEnemy();;
        }
        interactPlayer(47);

        if (collisionOn && !onPath) {
            // If there's a collision during random movement, reverse direction
            switch (direction) {
                case UP:
                    direction = Direction.DOWN;
                    break;
                case DOWN:
                    direction = Direction.UP;
                    break;
                case LEFT:
                    direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    direction = Direction.LEFT;
                    break;
            }
            drawDirection = direction; 
            collisionOn = false; 
        }
        
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


    public ArrayList<Node> getPath() {
        return myPath;
    }

    private void randomMovement(){
        
        actionLockCounter++;
    
        if(actionLockCounter == 75){

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

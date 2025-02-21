package com.goblinskeep;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/ gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction){
            case Direction.UP:
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case Direction.DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case Direction.LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case Direction.RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;


        for (int i = 0; i < gp.obj.length; i++){
            //get the world position of the collision area for the Entity
//            entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
//            entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
//
//            //get world position of the collision area for the Objects
//            gp.obj[i].collisionArea.x = gp.obj[i].worldX + gp.obj[i].collisionArea.x;
//            gp.obj[i].collisionArea.y = gp.obj[i].worldY + gp.obj[i].collisionArea.y;

            //need to test if this works the same
            entity.collisionArea.x = entity.worldX + entity.collisionAreaDefaultX;
            entity.collisionArea.y = entity.worldY + entity.collisionAreaDefaultY;

            //get world position of the collision area for the Objects
            gp.obj[i].collisionArea.x = gp.obj[i].worldX + gp.obj[i].defaultCollisionAreaX;
            gp.obj[i].collisionArea.y = gp.obj[i].worldY + gp.obj[i].defaultCollisionAreaY;

            //use direction to find where the Entity will be after moved
            switch (entity.direction){
                case UP:
                    entity.collisionArea.y -= entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case LEFT:
                    entity.collisionArea.x -= entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case RIGHT:
                    entity.collisionArea.x += entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
                case DOWN:
                    entity.collisionArea.y += entity.speed;
                    if (entity.collisionArea.intersects(gp.obj[i].collisionArea)){
                        index = handleEntityCollision(entity, player, i);
                    }
                    break;
            }
            //reset the collision area positions for future use
            //uncomment if broken
//            entity.collisionArea.x = entity.collisionAreaDefaultX;
//            entity.collisionArea.y = entity.collisionAreaDefaultY;
//            gp.obj[i].collisionArea.x = gp.obj[i].defaultCollisionAreaX;
//            gp.obj[i].collisionArea.y = gp.obj[i].defaultCollisionAreaY;
        }
        return index;
    }

    private int handleEntityCollision(Entity entity, boolean player, int index){
        if (gp.obj[index].collision){
            //need to test why collision should be on
            entity.collisionOn = true;
        }
        if (player){
            return index;
        }
        return 999;
    }
}

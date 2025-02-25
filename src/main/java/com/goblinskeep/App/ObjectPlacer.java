package com.goblinskeep.App;

import com.goblinskeep.objects.Key;

public class ObjectPlacer {
    public GamePanel gp;

    public ObjectPlacer(GamePanel gp){
            this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new Key();
        gp.obj[0].worldX = 10 * gp.tileSize;
        gp.obj[0].worldY = 10* gp.tileSize;
    }
}
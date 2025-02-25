package com.goblinskeep.App;

import com.goblinskeep.objects.Key;
import com.goblinskeep.objects.MainObject;

public class ObjectPlacer {
    public GamePanel gp;

    public ObjectPlacer(GamePanel gp){
            this.gp = gp;
    }

    public void setObject(){
        createObjectOnBoard(new Key(),4 ,2, 0);
        createObjectOnBoard(new Key(),3 ,5, 1);
    }

    private void createObjectOnBoard(MainObject object, int xTile, int yTile, int index){
        gp.obj[index] = object;
        gp.obj[index].worldX = xTile * gp.tileSize;
        gp.obj[index].worldY = yTile * gp.tileSize;
    }
}
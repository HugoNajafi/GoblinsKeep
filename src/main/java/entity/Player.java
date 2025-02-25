package entity;

import com.goblinskeep.GamePanel;

public class Player extends Entity{
    public int screenX, screenY;
    public GamePanel gp;
    public int keysCollected;
    public Player (GamePanel gp){
        this.gp = gp;
    }

    public void update(){
        //call this before applying movement
        int objIndex = gp.collisionChecker.checkObject(this, true);
        pickUpObject(objIndex);
    }

    public void pickUpObject(int index) {
        //if 999 then an object was not collected
        if (index != 999){
            String objName = gp.obj[index].name;
            switch (objName){
                case "key":
                    keysCollected++;
            }
            //can make collision true in here if needed to for uncollectable objects
            gp.obj[index] = null;
        }
    }
}

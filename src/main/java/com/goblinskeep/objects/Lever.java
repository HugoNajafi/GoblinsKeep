package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Lever extends MainObject {
    public Lever() {
        name = "lever";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/lever1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = false;
        defaultCollisionAreaX += 6;
        defaultCollisionAreaY += 24;
        collisionArea.height -= 24;
        collisionArea.width -= 12;
    }

    public void activate(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/lever2.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
    }
}

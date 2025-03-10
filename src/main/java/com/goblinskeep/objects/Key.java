package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends MainObject {
    public Key() {
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Key.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = true;
        defaultCollisionAreaX += 6;
        defaultCollisionAreaY += 6;
        collisionArea.height -= 12;
        collisionArea.width -= 12;
    }
}

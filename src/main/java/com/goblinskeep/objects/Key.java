package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends MainObject {
    public Key() {
        name = "key";
        image = loadImage("/objects/Key.png");
        collision = false;
        defaultCollisionAreaX += 6;
        defaultCollisionAreaY += 6;
        collisionArea.height -= 12;
        collisionArea.width -= 12;
    }
}

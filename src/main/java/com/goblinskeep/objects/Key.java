package com.goblinskeep.objects;


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

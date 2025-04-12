package com.goblinskeep.objects;

/**
 * Represents a key object in the game. 
 * The key can be used to unlock certain areas or objects.
 */
public class Key extends MainObject {

    /**
     * Constructs a Key object with predefined properties such as name, image, 
     * collision settings, and collision area adjustments.
     */
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

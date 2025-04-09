package com.goblinskeep.objects;

/**
 * Represents a lever that controls the game's exit gate.
 *
 * - The lever starts in a **locked state** and requires a certain number of keys to activate.
 * - Once **activated**, the lever **unlocks the exit** and allows the player to leave.
 * - It also **changes its appearance** to indicate its status.
 */
public class Lever extends MainObject {

    /**
     * Constructs a locked lever.
     * The lever requires a specific number of keys to activate.
     */
    public Lever() {
        name = "lever";

        image = loadImage("/objects/lever1.png");

        collision = false;
        defaultCollisionAreaX += 6;
        defaultCollisionAreaY -= 12;
        collisionArea.height -= 6;
        collisionArea.width -= 12;
    }


    /**
     * Activates the lever, unlocking the exit.
     * - Changes the lever's appearance to an "unlocked" state.
     * - Removes collision so the player can interact with it.
     */
    public void activate(){
        image = loadImage("/objects/lever2.png");
    }
}

package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Represents a trap that deducts points when a player steps on it.
 *
 * - The trap is **always active** and deducts points upon collision.
 * - After the **first collision**, it has a **1-second cooldown** before it can deduct points again.
 * - The trap does **not block movement**, meaning the player can walk over it.
 */
public class Trap extends MainObject {

    /**
     * Constructs a trap object.
     * The trap starts in an active state but does not block movement.
     */
    public Trap() {
        name = "trap";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/trap1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = false;//player can walk through
    }
}

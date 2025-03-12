package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * Represents an exit door in the game.
 *
 * - The exit door starts as **closed** and blocks player movement.
 * - Once **opened**, it changes its appearance and allows the player to pass through.
 */
public class Exit extends MainObject {

    /**
     * Constructs an Exit door with a default **closed** state.
     * The door is initially impassable due to collision.
     */
    public Exit() {
        name = "exit";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = true; //door is initially locked door
    }


    /**
     * Opens the exit door, changing its appearance and allowing the player to pass through.
     *
     * - Updates the door's sprite to an **open** version.
     * - Disables collision so the player can walk through it.
     */
    public void open(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door2.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        this.collision = false;
    }
}

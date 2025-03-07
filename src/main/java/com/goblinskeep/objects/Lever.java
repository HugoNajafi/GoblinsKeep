package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Lever extends MainObject {
    public Lever() {
        name = "lever";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Diamond.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = false;
    }

    public void activate(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Life.png"));

        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
    }
}

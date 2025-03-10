package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bonus extends MainObject {
    public Bonus() {
        name = "bonus";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/bonus1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = true;
    }
}

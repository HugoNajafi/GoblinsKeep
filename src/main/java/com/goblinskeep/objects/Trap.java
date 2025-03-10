package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Trap extends MainObject {
    public Trap() {
        name = "trap";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/trap1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = true;
    }
}

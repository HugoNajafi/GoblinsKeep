package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Exit extends MainObject {
    public Exit() {
        name = "exit";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Star.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = false;
    }
}

package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Exit extends MainObject {
    public Exit() {
        name = "exit";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = true;
    }

    public void open(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door2.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        this.collision = false;
    }
}

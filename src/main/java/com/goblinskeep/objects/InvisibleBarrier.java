package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;

import java.awt.*;

public class InvisibleBarrier extends MainObject{

    public InvisibleBarrier(){
        collision = true;
        name = "invisible";
    }
    @Override
    public void draw(Graphics2D g2, GamePanel gp){
        //draw nothing
    }
}

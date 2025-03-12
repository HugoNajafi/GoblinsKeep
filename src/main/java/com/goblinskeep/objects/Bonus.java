package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Represents a Bonus item
 * its becomes alive any time between 0 and 30 seconds
 * after becoming alive, it has a lifetime of 30 to 60 seconds before disappearing forever
 */
public class Bonus extends MainObject {
    private int startTime;
    private int survivalTime;
    private boolean alive = false;
    public Bonus(int startTime, int survivalTime) {
        name = "bonus";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/bonus1.png"));
        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
        collision = false;
        this.startTime = startTime % 30;
        this.survivalTime = 30 + survivalTime % 30;
    }

    public void updateState(int currentTime){
        if (isAlive(currentTime)){
            alive = true;
        } else {
            alive = false;
        }
    }

    public boolean isAlive(int currentTime){
        return startTime < currentTime && currentTime < startTime + survivalTime;
    }


    public void draw(Graphics2D g2, GamePanel gp) {
        if (alive){
            super.draw(g2, gp);
        }
    }
}

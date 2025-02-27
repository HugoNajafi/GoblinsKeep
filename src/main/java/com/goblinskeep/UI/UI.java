package com.goblinskeep.UI;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.GameStatus;
import com.goblinskeep.objects.Key;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gp.status == GameStatus.PLAYING) {
            drawPlaying(g2);
        } else if (gp.status == GameStatus.PAUSED){
            drawPaused(g2);
        }
    }

    public void drawPlaying(Graphics2D g2) {
        //draw key counter on screen
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x = " + 3, 74, 65);

        //draw the timer on the screen
        playTime += (double) 1/60;
        g2.drawString( String.format("Time: %.2f", playTime), gp.tileSize * 11 , 65);

        //draw any messages called
        if (messageOn){
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);
            messageCounter++;
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    public void drawPaused(Graphics2D g2){

    }

}
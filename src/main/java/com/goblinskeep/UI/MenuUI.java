package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.objects.Key;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuUI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;

    double playTime;
    public MenuUI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.image;
    }


    public void draw(Graphics2D g2) {
        g2.setFont(arial_80B);
        String title = "Goblins Keep";
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 3;
        g2.setColor(Color.WHITE);
        g2.drawString(title, x, y);
    }

    private int getCenteredXAxisText(String text, Graphics2D g2){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }



}
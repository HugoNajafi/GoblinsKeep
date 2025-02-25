package com.goblinskeep.objects;

import com.goblinskeep.App.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MainObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);;
    public int defaultCollisionAreaX, defaultCollisionAreaY;

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = worldY - gp.Player.WorldY + gp.Player.screenY;

        if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
            worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
            worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
            worldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//                g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

        }

    }
}

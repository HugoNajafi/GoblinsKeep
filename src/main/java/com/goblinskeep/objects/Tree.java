package com.goblinskeep.objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import com.goblinskeep.app.GamePanel;

/**
 * Represents a decorative tree in the game.
 *
 * - The tree is purely **visual** and does not interact with the player.
 * - It does **not have collision**, meaning the player can walk through it.
 * - Different tree variations can be used to enhance the game's environment.
 */
public class Tree extends MainObject {
    public Tree(int type) {
        name = "tree";
        if (type == 1){
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/goodTree1.png"));
            } catch (IOException e){
                System.out.printf(e.getMessage());
            }
        } else {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/goodTree2.png"));
            } catch (IOException e){
                System.out.printf(e.getMessage());
            }
        }

    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
        int screenY = worldY - gp.Player.WorldY + gp.Player.screenY - gp.tileSize;

        //camera logic, draw only around the player
        if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                worldY - 2 * gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize + gp.tileSize, null);
        }
    }
}

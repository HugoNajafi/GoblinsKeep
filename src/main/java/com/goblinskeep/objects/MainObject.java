package com.goblinskeep.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MainObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);;
    public int defaultCollisionAreaX, defaultCollisionAreaY;

}

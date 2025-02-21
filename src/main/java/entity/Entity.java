package entity;

import com.goblinskeep.Direction;

import java.awt.*;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public Rectangle collisionArea;
    public boolean collisionOn;
    public Direction direction;
}

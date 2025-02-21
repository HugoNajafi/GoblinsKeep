package entity;

import com.goblinskeep.Direction;

import java.awt.*;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public Rectangle collisionArea;
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean collisionOn;
    public Direction direction;
}

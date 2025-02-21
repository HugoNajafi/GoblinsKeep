package com.goblinskeep;

import Tile.TileManager;
import entity.Entity;
import entity.Player;
import objects.SuperObject;

import javax.swing.*;

public class GamePanel extends JFrame {
    public Player player;
    public int tileSize = 16;
    public TileManager tileM;
    public SuperObject[] obj;
    public CollisionChecker collisionChecker;
}

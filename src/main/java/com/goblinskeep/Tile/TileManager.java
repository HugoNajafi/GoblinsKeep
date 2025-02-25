package com.goblinskeep.Tile;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.CellType;
import com.goblinskeep.App.Gamestate;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum; // For collision checking
    
    // Tile index constants for easier reference
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int KEY = 2;
    public static final int BONUS = 3;
    public static final int TRAP = 4;
    public static final int LEVER = 5;
    public static final int ENTRY = 6;
    public static final int EXIT = 7;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        // We need 8 different tile types now
        tile = new Tile[8];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        
        getTileImage();
    }

    public void getTileImage() {
        try {
            // Load all tile images
            tile[EMPTY] = new Tile();
            tile[EMPTY].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/metalplate.png"));
            
            tile[WALL] = new Tile();
            tile[WALL].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/drytree.png"));
            tile[WALL].collision = true; // Important for collision checking
            
            tile[KEY] = new Tile();
            tile[KEY].image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            
            tile[BONUS] = new Tile();
            tile[BONUS].image = ImageIO.read(getClass().getResourceAsStream("/objects/Diamond.png"));
            
            tile[TRAP] = new Tile();
            tile[TRAP].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/destructiblewall.png"));
            
            tile[LEVER] = new Tile();
            tile[LEVER].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/metalplate.png"));
            
            tile[ENTRY] = new Tile();
            tile[ENTRY].image = ImageIO.read(getClass().getResourceAsStream("/objects/Star.png"));
            
            tile[EXIT] = new Tile();
            tile[EXIT].image = ImageIO.read(getClass().getResourceAsStream("/objects/Life.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to get the tile index for a specific cell type
    public int getTileIndex(CellType cellType) {
        switch (cellType) {
            case Wall: return WALL;
            case Key: return KEY;
            case Bonus: return BONUS;
            case Trap: return TRAP;
            case Lever: return LEVER;
            case Entry: return ENTRY;
            case Exit: return EXIT;
            case Empty: default: return EMPTY;
        }
    }
    
    // Update the mapTileNum array based on the gamestate grid
    public void updateMapTileNum(Gamestate gamestate) {
        for (int y = 0; y < gamestate.getHeight(); y++) {
            for (int x = 0; x < gamestate.getWidth(); x++) {
                CellType cellType = gamestate.getCellType(x, y);
                mapTileNum[x][y] = getTileIndex(cellType);
            }
        }
    }

    // Draw the map tiles
    public void draw(Graphics2D g2, Gamestate gamestate) {
        // First update the mapTileNum array
        updateMapTileNum(gamestate);
        
        for (int y = 0; y < gamestate.getHeight(); y++) {
            for (int x = 0; x < gamestate.getWidth(); x++) {
                int tileIndex = mapTileNum[x][y];
                
                // Draw the appropriate tile
                g2.drawImage(tile[tileIndex].image, x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
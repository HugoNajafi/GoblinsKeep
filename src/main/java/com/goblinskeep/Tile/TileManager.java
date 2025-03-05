package com.goblinskeep.Tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
//        getTileImage();
        newGetTileImage();
    }

    private void newGetTileImage() {
        try {
            mapNumToTile("/Tiles/metalplate.png", 0, false);
            mapNumToTile("/Tiles/drytree.png", 1, true);
            mapNumToTile("/Tiles/destructiblewall.png", 8, false);
        } catch (IOException e){
            System.out.println("tile image loading failed: " + e.getMessage());
        }

    }

    private void mapNumToTile(String tileFileName, int tileNum, boolean collision) throws IOException {
        tile[tileNum] = new Tile();
        tile[tileNum].image = ImageIO.read(getClass().getResourceAsStream(tileFileName));
        if (collision){
            tile[tileNum].collision = true;
        }

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

    public void loadMap(String mapFile){
        try {
            //load the map file into a stream and start parsing it
            InputStream inputMap = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputMap));
            int col = 0;
            int row = 0;
            //loop each line of the map file and map the tile number to the mapTileNum array
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = reader.readLine();
                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error in Map loading: " + e.getMessage());
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
    int count = 0;
    // Draw the map tiles
    public void draw(Graphics2D g2, Gamestate gamestate) {
        // First update the mapTileNum array
//        updateMapTileNum(gamestate);
//        old drawing version non-camera
//        for (int y = 0; y < gamestate.getHeight(); y++) {
//            for (int x = 0; x < gamestate.getWidth(); x++) {
//                int tileIndex = mapTileNum[x][y];
//
//                // Draw the appropriate tile
//                g2.drawImage(tile[tileIndex].image, x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize, null);
//            }
//        }
//        count++;
//        if (count == 60){
//            System.out.println("X: " + gp.Player.WorldX + " Y: " + gp.Player.WorldY);
//            count = 0;
//        }
//
        //starting point for iteration
        int worldRow = 0;
        int worldCol = 0;

//        while (worldCol < gamestate.getWidth() && worldRow < gamestate.getHeight()) {
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
            int screenY = worldY - gp.Player.WorldY + gp.Player.screenY;

            if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                    worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                    worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                    worldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol += 1;
            if (worldCol >= gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
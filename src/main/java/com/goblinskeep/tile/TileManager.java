package com.goblinskeep.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.CellType;
import com.goblinskeep.app.Gamestate;
import com.goblinskeep.objects.*;

/**
 * handles all types of tiles and maps the image to an array called tile[] and has the draw method for tiles
 */
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum; // For collision checking

    /**
     * constructor for TileManger, it creates a tile[] array and mapTileNum[][] and initilizes the 
     * @param gp: gamepanel for load map and update function of this class
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        // We need 8 different tile types now
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
    }

    /**
     * method to map the images to their respective position in tile[] array, uses helper function mapNumToTile()
     */
    private void getTileImage() {
        try {
            mapNumToTile("/tiles/ground1.png", 0, false);
            mapNumToTile("/tiles/wall1.png", 1, true);
            mapNumToTile("/tiles/grass_tile.png", 8, false);
        } catch (IOException e){
            System.out.println("tile image loading failed: " + e.getMessage());
        }

    }

    /**
     * helper function for getTileImage()
     * @param tileFileName: filePath
     * @param tileNum: index for the array
     * @param collision: boolean value for checking collision
     * @throws IOException: failed to load the tiles properly
     */
    private void mapNumToTile(String tileFileName, int tileNum, boolean collision) throws IOException {
        tile[tileNum] = new Tile();
        tile[tileNum].image = ImageIO.read(getClass().getResourceAsStream(tileFileName));
        if (collision){
            tile[tileNum].collision = true;
        }

    }

    /**
     * draw function for the tiles and objects
     * @param g2: graphics 2D to draw it
     * @param gamestate
     */
    public void draw(Graphics2D g2, Gamestate gamestate) {

        int worldRow = 0;
        int worldCol = 0;

        //loop terminates after the boundary is reached
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.Player.WorldX + gp.Player.screenX;
            int screenY = worldY - gp.Player.WorldY + gp.Player.screenY;

            //camera logic, draw only around the player
            if (worldX + gp.tileSize > gp.Player.WorldX - gp.Player.screenX &&
                    worldX - gp.tileSize < gp.Player.WorldX + gp.Player.screenX &&
                    worldY + gp.tileSize > gp.Player.WorldY - gp.Player.screenY &&
                    worldY - gp.tileSize < gp.Player.WorldY + gp.Player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol += 1;

            if (worldCol >= gp.maxWorldCol){
                worldCol = 0;
                worldRow++;//once you finish constructing one row, you'll go the next one
            }
        }
    }
}
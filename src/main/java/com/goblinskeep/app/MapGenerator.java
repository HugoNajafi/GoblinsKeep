package com.goblinskeep.app;

import com.goblinskeep.entity.Goblin;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.RegularGoblin;
import com.goblinskeep.objects.*;
import com.goblinskeep.tile.TileManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {

    GamePanel gp;
    private final Random random = new Random();
    public int[][] rawMapData; //for testing
    private final TileManager tileM;
    private final ObjectManager obj;
    private final MapHandler mapH;
    private final ArrayList<Goblin> goblins;
    private final Player player;


    public MapGenerator(GamePanel gp, MapHandler mapH, String filePath) {
        this.gp = gp;
        obj = new ObjectManager(gp);
        tileM = new TileManager(gp);
        rawMapData = new int[gp.maxWorldCol][gp.maxWorldRow];
        player = new Player(0, 0, gp, gp.PlayerInput);
        this.mapH = mapH;
        goblins = new ArrayList<>();
        gp.goblins = this.goblins;
        loadMap(filePath);
    }

    /**
     * Loads the map structure from a text file and initializes game objects.
     *
     * @param filePath The file path of the text-based map.
     */
    public void loadMap(String filePath) {
        try {
            //load the map file into a stream and start parsing it
            InputStream inputMap = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputMap));
            int col = 0;
            int row = 0;
            //loop each line of the map file and map the tile number to the mapTileNum array
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = reader.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    // Assign objects and tiles based on map file values
                    switch (num) {
                        case 2:
                            obj.addObject(col, row, new Key());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 3:
                            int start = random.nextInt(30);
                            int survival = random.nextInt(30);
                            Bonus newBonus = new Bonus(start, survival);
                            obj.addObject(col, row, newBonus);
                            mapH.addBonus(newBonus);
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 4:
                            obj.addObject(col, row, new Trap());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 5:
                            obj.addObject(col, row, new Lever());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 6:
                            setPlayerPosition(col, row);
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 7:
                            obj.addObject(col, row, new Exit());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 11:
                            setGoblinPosition(col, row);
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 12:
                            obj.addObject(col, row, new Tree(1));
                            tileM.mapTileNum[col][row] = 8;
                            break;
                        case 13:
                            obj.addObject(col, row, new Tree(0));
                            tileM.mapTileNum[col][row] = 8;
                            break;
                        case 15:
                            obj.addObject(col, row, new InvisibleBarrier());
                            tileM.mapTileNum[col][row] = 8;
                            break;
                        case 16:
                            obj.addObject(col, row, new InvisibleBarrier());
                            tileM.mapTileNum[col][row] = 9;
                            break;
                        case 22:
                            obj.addObject(col, row, new Key());
                            tileM.mapTileNum[col][row] = 8;
                            break;
                        default:
                            tileM.mapTileNum[col][row] = num;
                            break;
                    }
                    rawMapData[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error in Map loading: " + e.getMessage());
        }
    }


    /**
     * Sets the player's initial position.
     *
     * @param x The x-coordinate in tile units.
     * @param y The y-coordinate in tile units.
     */
    void setPlayerPosition(int x, int y) {
        player.WorldX = x * gp.tileSize;
        player.WorldY = y * gp.tileSize;
    }

    private void setGoblinPosition(int x, int y) {
        Goblin goblin = new RegularGoblin(gp, gp.Player,0,0);
        goblin.setX(x * gp.tileSize);
        goblin.setY(y * gp.tileSize);
        goblins.add(goblin);
    }

}
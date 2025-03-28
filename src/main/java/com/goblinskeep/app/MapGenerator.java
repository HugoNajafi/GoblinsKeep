package com.goblinskeep.app;


import com.goblinskeep.entity.Goblin;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.RegularGoblin;
import com.goblinskeep.objects.*;
import com.goblinskeep.tile.TileManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Generates and controls the game map, including object placement, enemy spawning,
 * and tracking game progression.
 */
public class MapGenerator {
    private GamePanel gp;
    private ArrayList<Goblin> goblins;
    private List<Bonus> bonuses = new ArrayList<>();
    private Player player;
    private ObjectManager obj;
    private TileManager tileM;
    private List<Point> goblinSpawnPositions = new ArrayList<>();
    private int currentTime = 0;
    private int currentTimeCounter = 0;
    private Random random = new Random();
    public int[][] rawMapData; //for testing


    /** Number of keys needed to unlock the lever*/
    public int keysNeeded = 5;

    private boolean exitOpen = false;
    private boolean gameEnded = false;
    private int keysCollected = 0;
    private boolean gameWin = false;
    private int score = 0;
    private int counter = 0;
    private boolean canDeductPoints = true;

    /**
     * Initializes the map generator and sets up the map and goblins.
     *
     * @param gp The main game panel.
     */
    public MapGenerator(GamePanel gp){
        this.gp = gp;
        player = new Player(0, 0, gp, gp.PlayerInput);
        obj = new ObjectManager(gp);
        tileM = new TileManager(gp);
        rawMapData = new int[gp.maxWorldCol][gp.maxWorldRow];
        setMap();
        setGoblins();

    }

    /** Loads the map from a predefined file. */
    public void setMap(){
        loadMap("/maps/world1.txt");
    }

    /**
     * Loads the map structure from a text file and initializes game objects.
     *
     * @param filePath The file path of the text-based map.
     */
    public void loadMap(String filePath){
        try {
            //load the map file into a stream and start parsing it
            InputStream inputMap = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputMap));
            int col = 0;
            int row = 0;
            //loop each line of the map file and map the tile number to the mapTileNum array
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = reader.readLine();
                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    // Assign objects and tiles based on map file values
                    switch (num) {
                        case 2:
                            obj.addObject(col, row,new Key());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 3:
                            int start = random.nextInt(30);
                            int survival = random.nextInt(30);
                            Bonus newBonus = new Bonus(start,survival);
                            obj.addObject(col, row, newBonus);
                            bonuses.add(newBonus);
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 4:
                            obj.addObject(col, row,new Trap());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 5:
                            obj.addObject(col, row,new Lever());
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
                            goblinSpawnPositions.add(new Point(col, row));
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


    /**
     * Sets the player's initial position.
     *
     * @param x The x-coordinate in tile units.
     * @param y The y-coordinate in tile units.
     */
    private void setPlayerPosition(int x, int y){
        player.WorldX = x * gp.tileSize;
        player.WorldY = y * gp.tileSize;
    }


    /** Spawns goblins at predefined locations from the map. */
    private void setGoblins(){
        goblins = new ArrayList<>();
        for (Point position : goblinSpawnPositions){
            position.x *= gp.tileSize;
            position.y *= gp.tileSize;
            Goblin goblin = new RegularGoblin(gp, gp.Player);
            goblin.setX(position.x);
            goblin.setY(position.y);
            goblins.add(goblin);
        }

    }


    /**
     * Handles interaction when a player touches a lever.
     *
     */
    public void leverTouched(){
        if (keysCollected >= keysNeeded){
            exitOpen = true;
            keysNeeded = -1;

            //getting the only lever in the map
            Lever lever = gp.obj.findLever();
            lever.activate();

            //getting the only door in the map
            Exit door = gp.obj.findDoor();
            door.open();
            gp.ui.showMessage("Exit Opened");
        }
        else
        {
            gp.ui.showMessage("Get more Keys!");
        }
    }


    /** Handles interaction when the player reaches the exit. */
    public void exitTouched(){
        if (exitOpen){
            gameEnded = true;
            gameWin = true;
        }
        else
        {
            gp.ui.showMessage("Door locked!");
        }
    }


    /** Updates the number of collected keys. */
    public void keyCollected(){
        keysCollected++;
        if (keysCollected == keysNeeded){
            gp.ui.showMessage("Lever Unlocked");
        }
    }


    /** Handles collision when the player encounters an enemy. */
    public void playerCollisionWithEnemy(){
        gameEnded = true;
        gameWin = false;
    }



    public void collectedBonus(Bonus bonus){
        if (bonus.isAlive(currentTime)){
            score += 100;
            gp.obj.removeObject(bonus.worldX,bonus.worldY);
            bonuses.remove(bonus);
        }
    }


    /** Deducts points when the player steps on a trap. */
    public void trapHit(){
        if(canDeductPoints) {
            score -= 50;
            canDeductPoints = false;
        }
        if (score < 0 ){
            gameEnded =true;
            gameWin = false;
        }
    }


    /** Updates the timers for score deductions. */
    public void update(){
        currentTimeCounter++;
        if (currentTimeCounter >= 60){
            currentTime++;
            currentTimeCounter = 0;
            for (Bonus bonus : bonuses){
                bonus.updateState(currentTime);
            }
        }
        if(!canDeductPoints){
            counter+=1;
            if(counter == 120){
                counter = 0;
                canDeductPoints = true;
            }
        }
    }


    /** @return The list of goblins currently in the game. */
    public ArrayList<Goblin> getGoblins(){
        return goblins;
    }


    /** @return The tile manager handling the game tiles. */
    public TileManager getTileM(){
        return tileM;
    }


    /** @return The object manager handling game objects. */
    public ObjectManager getObj(){
        return obj;
    }


    /** @return The player instance. */
    public Player getPlayer(){
        return player;
    }


    /** @return The number of collected keys. */
    public int getKeysCollected(){
        return keysCollected;
    }

    /** @return The player's score. */
    public int getScore() {
        return score;
    }


    /** @return Whether the game has ended. */
    public boolean gameEnded(){
        return gameEnded;
    }


    /** @return Whether the player won the game. */
    public boolean isGameWin(){
        return gameWin;
    }

}

package com.goblinskeep.app;


import com.goblinskeep.entity.SmartGoblin;
import com.goblinskeep.objects.Exit;
import com.goblinskeep.objects.Key;
import com.goblinskeep.objects.Lever;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    private GamePanel gp;
    private ArrayList<SmartGoblin> goblins;
    private Lever lever = new Lever();
    public int keysNeeded = 2;
    private boolean exitOpen = false;
    private boolean gameEnded = false;
    private int keysCollected = 0;
    private boolean gameWin = false;
    private int score = 0;

    public MapGenerator(GamePanel gp){
        this.gp = gp;
        setMap();
        setPlayerPosition();
        setGoblins();
    }


    public void setMap(){
        loadMap("/maps/world1.txt");
    }

    /**
     * loads up the mapTileNum[][] array with indices for each position
     * @param filePath: the file path where the txt map is
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
                    switch (num) {
                        case 2:
                            gp.obj.addObject(col,row,new Key());
                            gp.tileM.mapTileNum[col][row] = 0;
                            break;
                        case 5:
                            gp.obj.addObject(col,row,new Lever());
                            gp.tileM.mapTileNum[col][row] = 0;
                            break;
                        case 7:
                            gp.obj.addObject(col,row, new Exit());
                            gp.tileM.mapTileNum[col][row] = 0;
                            break;
                        default:
                            gp.tileM.mapTileNum[col][row] = num;
                            break;
                    }
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

    private void setPlayerPosition(){
        gp.Player.WorldX = 6 * gp.tileSize;
        gp.Player.WorldY = 6 * gp.tileSize;
    }


    private void setGoblins(){
        goblins = new ArrayList<>();
        for (Point position : getGoblinPositions()){
            position.x *= gp.tileSize;
            position.y *= gp.tileSize;
            SmartGoblin goblin = new SmartGoblin(gp, gp.Player, gp.gamestate);
            goblin.setX(position.x);
            goblin.setY(position.y);
            goblin.collisionArea = new Rectangle(8, 16, 32, 32); // Set collision area
            goblin.hitboxDefaultX = 8;
            goblin.hitboxDefaultY = 16;
            goblins.add(goblin);
        }

    }

    private List<Point> getGoblinPositions(){
        List<Point> positions = new ArrayList<>();

        //add list of positions below
        positions.add(new Point(20, 20));

        return positions;
    }


    public ArrayList<SmartGoblin> getGoblins(){
        return goblins;
    }


    public void leverTouched(){
        if (keysCollected == keysNeeded){
            exitOpen = true;
            keysNeeded = -1;
            gp.ui.showMessage("Exit Opened");
            lever.activate();
        }
    }

    public void exitTouched(){
        if (exitOpen){
            gameEnded = true;
            gameWin = true;
        }
    }

    public void keyCollected(){
        keysCollected++;
        if (keysCollected == keysNeeded){
            gp.ui.showMessage("Lever Unlocked");
        }
    }
    public int getKeysCollected(){
        return keysCollected;
    }

    public void playerCollisionWithEnemy(){
        gameEnded = true;
        gameWin = false;
    }

    public boolean isGameWin(){
        return gameWin;
    }

    public void collectedBonus(){
        score += 100;
    }

    public int getScore() {
        return score;
    }

    public boolean gameEnded(){
        return gameEnded;
    }
}

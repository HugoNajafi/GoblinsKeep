package com.goblinskeep.app;


import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.SmartGoblin;
import com.goblinskeep.objects.*;
import com.goblinskeep.tile.TileManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    private GamePanel gp;
    private ArrayList<SmartGoblin> goblins;
    private Player player;
    private ObjectManager obj;
    private TileManager tileM;

    public int keysNeeded = 3;
    private boolean exitOpen = false;
    private boolean gameEnded = false;
    private int keysCollected = 0;
    private boolean gameWin = false;
    private int score = 0;
    private int counter = 0;
    private boolean canDeductPoints = true;

    public MapGenerator(GamePanel gp){
        this.gp = gp;
        player = new Player(0, 0, gp, gp.PlayerInput);
        obj = new ObjectManager(gp);
        tileM = new TileManager(gp);
        setMap();
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
                            obj.addObject(row,col,new Key());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 3:
                            obj.addObject(row,col, new Bonus());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 4:
                            obj.addObject(row,col,new Trap());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 5:
                            obj.addObject(row,col,new Lever());
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 6:
                            setPlayerPosition(col, row);
                            tileM.mapTileNum[col][row] = 0;
                            break;
                        case 7:
                            obj.addObject(row,col, new Exit());
                            tileM.mapTileNum[col][row] = 1;
                            break;
                        default:
                            tileM.mapTileNum[col][row] = num;
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

    private void setPlayerPosition(int x, int y){
        player.WorldX = x * gp.tileSize;
        player.WorldY = y * gp.tileSize;
    }


    private void setGoblins(){
        goblins = new ArrayList<>();
        for (Point position : getGoblinPositions()){
            position.x *= gp.tileSize;
            position.y *= gp.tileSize;
            SmartGoblin goblin = new SmartGoblin(gp, gp.Player);
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


    public void leverTouched(MainObject collisionObject){
        if (keysCollected >= keysNeeded){
            exitOpen = true;
            keysNeeded = -1;
            Lever lever = gp.obj.findLever();
            lever.activate();
            Exit door = gp.obj.findDoor();
            door.open();
            gp.ui.showMessage("Exit Opened");
        }
        else
        {
            gp.ui.showMessage("Get more Keys!");
        }
    }
    public TileManager getTileM(){
        return tileM;
    }

    public ObjectManager getObj(){
        return obj;
    }

    public Player getPlayer(){
        return player;
    }
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
    public int getScore() {
        return score;
    }

    public boolean gameEnded(){
        return gameEnded;
    }

    public void update(){
        if(!canDeductPoints){
            counter+=1;
            if(counter == 120){
                counter = 0;
                canDeductPoints = true;
            }
        }
    }

}

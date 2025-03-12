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
import java.util.Random;

public class MapGenerator {
    private GamePanel gp;
    private ArrayList<SmartGoblin> goblins;
    private List<Bonus> bonuses = new ArrayList<>();
    private Player player;
    private ObjectManager obj;
    private TileManager tileM;
    private List<Point> goblinSpawnPositions = new ArrayList<>();
    private int currentTime = 0;
    private int currentTimeCounter = 0;
    private Random random = new Random();

    public int keysNeeded = 5;
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
        for (Point position : goblinSpawnPositions){
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

    public void collectedBonus(Bonus bonus){
        if (bonus.isAlive(currentTime)){
            score += 100;
            gp.obj.removeObject(bonus.worldX,bonus.worldY);
            bonuses.remove(bonus);
        }

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

}

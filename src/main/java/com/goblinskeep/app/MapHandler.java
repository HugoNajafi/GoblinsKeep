package com.goblinskeep.app;


import com.goblinskeep.entity.Goblin;
import com.goblinskeep.entity.Player;
import com.goblinskeep.objects.*;
import com.goblinskeep.tile.TileManager;


import java.util.ArrayList;
import java.util.List;



/**
 * Generates and controls the game map, including object placement, enemy spawning,
 * and tracking game progression.
 */
public class MapHandler {
    private final GamePanel gp;
    private final List<Bonus> bonuses = new ArrayList<>();
    private int currentTime = 0;
    private int currentTimeCounter = 0;
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
    public MapHandler(GamePanel gp){
        this.gp = gp;
        rawMapData = new int[gp.maxWorldCol][gp.maxWorldRow];
        MapGenerator mapGen = new MapGenerator(gp, this, "/maps/world1.txt");
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
            removeBonus(bonus);
        }
    }

    public void removeBonus(Bonus bonus){
        gp.obj.removeObject(bonus.worldX,bonus.worldY);
        bonuses.remove(bonus);
    }

    public void addBonus(Bonus bonus){
        bonuses.add(bonus);
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

    /** sets the game status for testing purposes. */
    public void setGameEnded(boolean gameEnded){
        this.gameEnded = gameEnded;
    }

}

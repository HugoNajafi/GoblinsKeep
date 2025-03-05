package com.goblinskeep.App;


import com.goblinskeep.Tile.TileManager;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.SmartGoblin;
import com.goblinskeep.objects.Exit;
import com.goblinskeep.objects.Key;
import com.goblinskeep.objects.Lever;
import com.goblinskeep.objects.MainObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map1 {
    private GamePanel gp;
    private TileManager tileM;
    private Player player;
    private ArrayList<SmartGoblin> goblins;
    private MainObject[] objects;
    private Lever lever = new Lever();
    public int keysNeeded = 2;
    private boolean exitOpen = false;

    public Map1(GamePanel gp){
        this.gp = gp;
        this.player = gp.Player;
        tileM = new TileManager(gp);
        setMap();
        setPlayerPosition();
        setGoblins();

        //sets keys, lever and exit
        setObject();


    }

    private void setPlayerPosition(){
        player.WorldX = 2 * gp.tileSize;
        player.WorldY = 2 * gp.tileSize;
    }

    private List<Point> getGoblinPositions(){
        List<Point> positions = new ArrayList<>();

        //add list of positions below
        positions.add(new Point(12, 2));

        return positions;
    }

    private void setGoblins(){
        goblins = new ArrayList<>();
        for (Point position : getGoblinPositions()){
            position.x *= gp.tileSize;
            position.y *= gp.tileSize;
            SmartGoblin goblin = new SmartGoblin(gp, player, gp.gamestate);
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

    public void setMap(){
        tileM.loadMap("/Maps/map1_spaced.txt");

    }

    public TileManager getTileManager(){
        return tileM;
    }



    private void setObject(){
        objects = new MainObject[20];
        createObjectOnBoard(new Key(),4 ,0, 0);
        createObjectOnBoard(new Key(),3 ,2, 1);
        createObjectOnBoard(lever, 5, 2, 2);
        createObjectOnBoard(new Exit(), 10, 0, 3);
    }

    public MainObject[] getObjects(){
        return objects;
    }

    private void createObjectOnBoard(MainObject object, int xTile, int yTile, int index){
        objects[index] = object;
        objects[index].worldX = xTile * gp.tileSize;
        objects[index].worldY = yTile * gp.tileSize;
    }

    public void leverTouched(){
        if (player.keysCollected == keysNeeded){
            exitOpen = true;
            keysNeeded = -1;
            lever.activate();
        }
    }

    public void exitTouched(){
        if (exitOpen){

        }
    }
}

package com.goblinskeep.objects;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapHandler;

import java.awt.*;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Manages all interactive objects in the game.
 * <p>
 * Uses a {@link HashMap} to track objects where keys represent **map coordinates** (`x,y`).
 * Stores objects like keys, traps, levers, exits, and bonuses.
 * Provides methods to add, remove, and find objects dynamically.
 */
public class ObjectManager {

    /** A mapping of object locations to their respective objects. */
    public HashMap<String, MainObject> anObject;
    /** initialize GamePanel instance */
    public GamePanel gp;
    /** initialize MapHandler instance */
    public MapHandler map;


    /**
     * Constructs an ObjectManager that manages all game objects.
     *
     * @param gp The game panel instance.
     */
    public ObjectManager(GamePanel gp){
        anObject = new LinkedHashMap<>();
        this.gp = gp;//tilesize is 48
        this.map = gp.map;
        gp.obj = this;
    }

    /**
     * Generates a unique key for an object's location in the game world.
     *
     * @param x The object's x-coordinate in tile units.
     * @param y The object's y-coordinate in tile units.
     * @return A unique string key representing the object's location.
     */
    private String generateKey(int x, int y){
        return x + "," + y;
    }


    /**
     * Adds an object to the game world at the specified coordinates.
     *
     * @param x The object's x-coordinate in tile units.
     * @param y The object's y-coordinate in tile units.
     * @param newObject The object to be placed at this location.
     */
    public void addObject(int x, int y, MainObject newObject){
        anObject.put(generateKey(x,y), newObject);
        newObject.worldY = y * gp.tileSize;
        newObject.worldX = x * gp.tileSize;
    }


    /**
     * Searches the game world for the only {@link Exit}.
     *
     * @return The {@link Exit} object if found, or {@code null} if not found.
     */
    public Exit findDoor(){
        for(MainObject i: anObject.values()){
            if (i instanceof Exit)
            {
                return ((Exit) i);
            }
        }
        return null;
    }


    /**
     * Searches the game world for the only {@link Lever} object.
     *
     * @return The {@link Lever} object if found, otherwise {@code null}.
     */
    public Lever findLever(){
        for(MainObject i: anObject.values()){
            if (i instanceof Lever)
            {
                return ((Lever) i);
            }
        }
        return null;
    }


    /**
     * Removes an object from the game world at the specified coordinates.
     *
     * @param x The x-coordinate (in pixels) of the object to remove.
     * @param y The y-coordinate (in pixels) of the object to remove.
     */
    public void removeObject(int x, int y) {
        String key = generateKey(x/gp.tileSize,y/gp.tileSize);
        if(anObject.containsKey(key)) {
            anObject.remove(key);
        }
    }


    /**
     * Draws all objects currently in the game world by calling their corresponding draw methods
     *
     * @param g2 The graphics context used for rendering.
     * @param gp The game panel where objects are displayed.
     */
    public void draw(Graphics2D g2, GamePanel gp){
        for(MainObject i: anObject.values()){
            i.draw(g2,gp);
        }
    }
}
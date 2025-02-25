package com.goblinskeep.App;

import java.awt.Point;
import java.util.ArrayList;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.Goblin;

public class Gamestate {
    private CellType[][] grid;
    private Player player;
    private ArrayList<Goblin> enemies;
    private ArrayList<Point> initialGoblinPositions;
    private Point exitPosition;
    private int width, height;
    private boolean gameOver;
    private boolean levelCompleted;
    
    // Constructor for a new game state
    public Gamestate(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.grid = new CellType[width][height];
        this.player = player;

        // Initialize an empty grid
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = CellType.Empty;
            }
        }
        
        this.gameOver = false;
        this.levelCompleted = false;
        this.enemies = new ArrayList<>();
        this.initialGoblinPositions = new ArrayList<>();
    }
    
    // Add an enemy to the gamestate
    public void addEnemy(Goblin enemy) {
        if (!enemies.contains(enemy)) {
            enemies.add(enemy);
        }
    }
    
    // Remove an enemy from the gamestate
    public void removeEnemy(Goblin enemy) {
        enemies.remove(enemy);
    }
    
    // Cell type management
    public void setCellType(int x, int y, CellType type) {
        if (isValidPosition(x, y)) {
            grid[x][y] = type;
        }
    }
    
    public CellType getCellType(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return CellType.Wall; // Return Wall for out of bounds
    }
    
    // Process interaction with a cell
    public void interactWithCell(int x, int y) {
        if (isValidPosition(x, y)) {
            CellType cellType = grid[x][y];
            
            switch (cellType) {
                case Key:
                case Bonus:
                    // Collect item and clear cell
                    grid[x][y] = CellType.Empty;
                    break;
                case Lever:
                    // Activate lever (you can add specific actions here)
                    break;
                case Exit:
                    levelCompleted = true;
                    break;
                default:
                    break;
            }
        }
    }
    
    // Get player's grid position (convert from pixel to grid)
    public int getPlayerGridX() {
        return player.getX() / player.gp.tileSize;
    }
    
    public int getPlayerGridY() {
        return player.getY() / player.gp.tileSize;
    }
    
    // Check if a position is valid (within grid bounds)
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    // Check if a position is walkable
    public boolean isWalkable(int x, int y) {
        if (!isValidPosition(x, y)) {
            return false;
        }
        
        CellType cell = grid[x][y];
        return cell != CellType.Wall; // Everything except walls is walkable
    }
    
    // For storing initial goblin positions from map loading
    public void setInitialGoblinPositions(ArrayList<Point> positions) {
        this.initialGoblinPositions = positions;
    }
    
    public ArrayList<Point> getInitialGoblinPositions() {
        return initialGoblinPositions;
    }
    
    // Store and retrieve exit position
    public void setExitPosition(Point pos) {
        this.exitPosition = pos;
    }
    
    public Point getExitPosition() {
        return exitPosition;
    }
    
    // Getters and setters
    public Player getPlayer() {
        return player;
    }
    
    public ArrayList<Goblin> getEnemies() {
        return enemies;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public boolean isLevelCompleted() {
        return levelCompleted;
    }
}
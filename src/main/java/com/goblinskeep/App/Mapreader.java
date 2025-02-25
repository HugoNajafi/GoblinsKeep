package com.goblinskeep.App;

import java.awt.Point;
import java.util.ArrayList;

record Pair<C,R>(C column, R row){}

public class Mapreader {
    // Character mappings for the map - matching your legend
    private static final char WALL_CHAR = 'X';
    private static final char KEY_CHAR = '%';
    private static final char BONUS_CHAR = '$';
    private static final char PLAYER_CHAR = 'O';
    private static final char TRAP_CHAR = '&';
    private static final char LEVER_CHAR = 'L';
    private static final char ENEMY_CHAR = 'E';
    private static final char ENTRY_CHAR = '@';
    private static final char EXIT_CHAR = '!';
    private static final char EMPTY_CHAR = '.';
    
    /**
     * Read dimensions from a map string
     */
    public Pair<Integer, Integer> getDimensions(String map) {
        String[] lines = map.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();
        return new Pair<>(cols, rows);
    }
    
    /**
     * Load a map from a string and populate the gamestate
     */
    public void loadMap(String mapStr, Gamestate gamestate, GamePanel gp) {
        String[] lines = mapStr.split("\n");
        int rows = lines.length;
        int cols = 0;
        
        // Find the longest line to determine width
        for (String line : lines) {
            if (line.length() > cols) {
                cols = line.length();
            }
        }
        
        // Initialize positions
        Point playerPosition = null;
        ArrayList<Point> enemyPositions = new ArrayList<>();
        Point entryPosition = null;
        Point exitPosition = null;
        
        // Populate the grid based on the map
        for (int y = 0; y < rows; y++) {
            String line = lines[y];
            for (int x = 0; x < cols && x < line.length(); x++) {
                char cell = line.charAt(x);
                
                switch (cell) {
                    case WALL_CHAR:
                        gamestate.setCellType(x, y, CellType.Wall);
                        break;
                    case KEY_CHAR:
                        gamestate.setCellType(x, y, CellType.Key);
                        break;
                    case BONUS_CHAR:
                        gamestate.setCellType(x, y, CellType.Bonus);
                        break;
                    case PLAYER_CHAR:
                        playerPosition = new Point(x, y);
                        gamestate.setCellType(x, y, CellType.Empty);
                        break;
                    case TRAP_CHAR:
                        gamestate.setCellType(x, y, CellType.Trap);
                        break;
                    case LEVER_CHAR:
                        gamestate.setCellType(x, y, CellType.Lever);
                        break;
                    case ENEMY_CHAR:
                        enemyPositions.add(new Point(x, y));
                        gamestate.setCellType(x, y, CellType.Empty);
                        break;
                    case ENTRY_CHAR:
                        entryPosition = new Point(x, y);
                        gamestate.setCellType(x, y, CellType.Entry);
                        break;
                    case EXIT_CHAR:
                        exitPosition = new Point(x, y);
                        gamestate.setCellType(x, y, CellType.Exit);
                        break;
                    case EMPTY_CHAR:
                    default:
                        gamestate.setCellType(x, y, CellType.Empty);
                        break;
                }
            }
        }
        
        // Use the entry point for player position if available, otherwise use the explicit player position
        Point startPos = (entryPosition != null) ? entryPosition : playerPosition;
        
        // Set player position if found
        if (startPos != null && gamestate.getPlayer() != null) {
            gamestate.getPlayer().setX(startPos.x * gp.tileSize);
            gamestate.getPlayer().setY(startPos.y * gp.tileSize);
        }
        
        // Store enemy positions
        gamestate.setInitialGoblinPositions(enemyPositions);
        
        // Store exit position
        if (exitPosition != null) {
            gamestate.setExitPosition(exitPosition);
        }
    }
    
    /**
     * Parse a map string from your drawing
     * Assuming that X is a wall, O is player, E is enemy, etc.
     */
    public String parseDrawnMap(String rawMapStr) {
        // This is where you'd convert your drawn map to our format
        // For now, returning a simple conversion
        return rawMapStr
            .replace('X', WALL_CHAR)
            .replace('O', PLAYER_CHAR)
            .replace('E', ENEMY_CHAR)
            .replace('%', KEY_CHAR)
            .replace('$', BONUS_CHAR)
            .replace('&', TRAP_CHAR)
            .replace('L', LEVER_CHAR)
            .replace('@', ENTRY_CHAR)
            .replace('!', EXIT_CHAR)
            .replace(' ', EMPTY_CHAR);
    }
    
    /**
     * Create a map based on your sample design
     */
    public String createSampleMap() {
        // Create a string representation of your map design
        StringBuilder sb = new StringBuilder();
        
        // This is a simplified representation of your drawn map
        sb.append("XXXXXXX\n");
        sb.append("X@   XX\n");
        sb.append("X     X\n");
        sb.append("X$ XX X\n");
        sb.append("XXLX  X\n");
        sb.append("XXX  EX\n");
        sb.append("XX$X XX\n");
        sb.append("XX X!XX\n");
        
        return sb.toString();
    }
    
    /**
     * Convert a string map to a 2D array for easier manipulation
     */
    public char[][] mapToArray(String mapStr) {
        String[] lines = mapStr.split("\n");
        int height = lines.length;
        int width = 0;
        
        // Find the longest line
        for (String line : lines) {
            if (line.length() > width) {
                width = line.length();
            }
        }
        
        char[][] map = new char[height][width];
        
        // Fill the map
        for (int y = 0; y < height; y++) {
            String line = lines[y];
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    map[y][x] = line.charAt(x);
                } else {
                    map[y][x] = EMPTY_CHAR; // Fill with empty space
                }
            }
        }
        
        return map;
    }
}
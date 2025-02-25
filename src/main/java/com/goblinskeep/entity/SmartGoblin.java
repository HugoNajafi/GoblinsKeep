package com.goblinskeep.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.goblinskeep.App.Direction;
import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.Gamestate;

public class SmartGoblin extends Goblin {
    private Gamestate gamestate;
    private List<Point> path;
    private int pathIndex;
    private int updateCounter = 0;
    private final int UPDATE_INTERVAL = 30; // Update path every 30 frames
    
    public SmartGoblin(GamePanel gp, Player player, Gamestate gamestate) {
        super(gp, player);
        this.gamestate = gamestate;
        this.path = new ArrayList<>();
        this.speed = 3; // Adjust the speed as needed

            // Initialize direction to avoid null pointer exceptions
        this.direction = Direction.DOWN; // Give it a default direction
    
        // Load the goblin images
        getGoblinImage();
    

        // Register this goblin with the gamestate
        gamestate.addEnemy(this);
    }
    
    @Override
    public void getAction() {
        // Update path periodically instead of every frame for performance
        updateCounter++;
        if (updateCounter >= UPDATE_INTERVAL || path.isEmpty()) {
            updateCounter = 0;
            findPathToPlayer();
        }
        
        // Reset collision flag
        collisionOn = false;
        
        // Move along the path
        moveAlongPath();
    }
    
    private void moveAlongPath() {
        if (path.isEmpty()) {
            return; // No path to follow
        }
        
        // Get the next target position
        if (pathIndex >= path.size()) {
            pathIndex = 0;
            // Recalculate path if we've reached the end
            findPathToPlayer();
            return;
        }
        
        Point target = path.get(pathIndex);
        
        // Convert from grid position to pixel position
        int targetPixelX = target.x * gp.tileSize;
        int targetPixelY = target.y * gp.tileSize;
        
        // Determine direction to move
        if (Math.abs(this.WorldX - targetPixelX) < this.getSpeed() && 
            Math.abs(this.WorldY - targetPixelY) < this.getSpeed()) {
            // We've reached the target point, move to next point
            pathIndex++;
            return;
        }
        
        // Reset collision flag
        collisionOn = false;
        
        // Set the direction based on where we're trying to go
        if (this.WorldX < targetPixelX) {
            direction = Direction.RIGHT;
        } else if (this.WorldX > targetPixelX) {
            direction = Direction.LEFT;
        } else if (this.WorldY < targetPixelY) {
            direction = Direction.DOWN;
        } else if (this.WorldY > targetPixelY) {
            direction = Direction.UP;
        }
        
        // Check collision
        gp.collisionChecker.checkTile(this);
        
        // Move if no collision
        if (!collisionOn) {
            if (direction == Direction.UP) {
                this.WorldY -= Direction.UP.getDy() * this.getSpeed();
            } else if (direction == Direction.DOWN) {
                this.WorldY -= Direction.DOWN.getDy() * this.getSpeed();
            } else if (direction == Direction.LEFT) {
                this.WorldX += Direction.LEFT.getDx() * this.getSpeed();
            } else if (direction == Direction.RIGHT) {
                this.WorldX += Direction.RIGHT.getDx() * this.getSpeed();
            }
            SpriteCounter++;
            if(SpriteCounter> 10){
                if(SpriteNum == 1){
                    SpriteNum = 2;
                }
                else if(SpriteNum == 2){
                    SpriteNum = 1;
                }
                SpriteCounter = 0;
            }
        } else {
            // If there's a collision, recalculate the path
            pathIndex = 0;
            findPathToPlayer();
        }
    }
    
    private void findPathToPlayer() {
        // Convert pixel positions to grid positions
        int startX = this.WorldX / gp.tileSize;
        int startY = this.WorldY / gp.tileSize;
        
        // Get player's grid position directly from gamestate
        int goalX = gamestate.getPlayerGridX();
        int goalY = gamestate.getPlayerGridY();
        
        Point start = new Point(startX, startY);
        Point goal = new Point(goalX, goalY);
        
        // A* algorithm
        path = aStar(start, goal);
        pathIndex = 0;
    }
    
    // A* pathfinding algorithm (unchanged from previous implementation)
    private List<Point> aStar(Point start, Point goal) {
        // Priority queue for open nodes (nodes to be evaluated)
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.fScore));
        
        // Set of evaluated nodes
        Set<Point> closedSet = new HashSet<>();
        
        // Add start node to open set
        Node startNode = new Node(start);
        startNode.gScore = 0;
        startNode.fScore = heuristic(start, goal);
        openSet.add(startNode);
        
        // Maps for tracking scores and paths
        Map<Point, Integer> gScoreMap = new HashMap<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        
        gScoreMap.put(start, 0);
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            // If we've reached the goal, reconstruct and return the path
            if (current.position.equals(goal)) {
                return reconstructPath(cameFrom, current.position);
            }
            
            closedSet.add(current.position);
            
            // Check all neighbors
            for (Point neighbor : getNeighbors(current.position)) {
                // Skip if already evaluated or not walkable
                if (closedSet.contains(neighbor) || !gamestate.isWalkable(neighbor.x, neighbor.y)) {
                    continue;
                }
                
                // Calculate tentative g score
                int tentativeGScore = gScoreMap.getOrDefault(current.position, Integer.MAX_VALUE) + 1;
                
                // If this path is better than any previous one
                if (tentativeGScore < gScoreMap.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    // Record this path
                    cameFrom.put(neighbor, current.position);
                    gScoreMap.put(neighbor, tentativeGScore);
                    
                    // Update the neighbor in the open set
                    Node neighborNode = new Node(neighbor);
                    neighborNode.gScore = tentativeGScore;
                    neighborNode.fScore = tentativeGScore + heuristic(neighbor, goal);
                    
                    // Add to open set if not already there
                    if (!containsPoint(openSet, neighbor)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }
        
        // No path found
        return new ArrayList<>();
    }
    
    // Calculate Manhattan distance as heuristic
    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
    // Get valid neighboring cells
    private List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        
        // Check four directions
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        for (int[] dir : dirs) {
            int nx = p.x + dir[0];
            int ny = p.y + dir[1];
            
            if (gamestate.isValidPosition(nx, ny)) {
                neighbors.add(new Point(nx, ny));
            }
        }
        
        return neighbors;
    }
    
    // Check if a point is in the open set
    private boolean containsPoint(PriorityQueue<Node> set, Point p) {
        for (Node node : set) {
            if (node.position.equals(p)) {
                return true;
            }
        }
        return false;
    }
    
    // Reconstruct path from came-from map
    private List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        List<Point> totalPath = new ArrayList<>();
        totalPath.add(current);
        
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(0, current);
        }
        
        // Remove the starting position from the path
        if (totalPath.size() > 1) {
            totalPath.remove(0);
        }
        
        return totalPath;
    }
    
    // Node class for A* algorithm
    private class Node {
        Point position;
        int gScore = Integer.MAX_VALUE; // Cost from start to current
        int fScore = Integer.MAX_VALUE; // Estimated total cost (g + h)
        
        Node(Point position) {
            this.position = position;
        }
    }
}
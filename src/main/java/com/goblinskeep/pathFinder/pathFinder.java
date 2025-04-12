package com.goblinskeep.pathFinder;

import java.util.ArrayList;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
// import com.goblinskeep.tile.TileManager;
import com.goblinskeep.entity.Entity;
import com.goblinskeep.entity.RegularGoblin;

/**
 * Handles the pathfinding logic for entities in the game.
 * Calculates the shortest path from a start node to a goal node while avoiding obstacles.
 */
public class pathFinder {

    /** Reference to the main gamepanel*/
    GamePanel gp;

    /** 2D grid of nodes representing the map.*/
    Node[][] node;

    /** List of currently open nodes being evaluated. */
    ArrayList<Node> openList = new ArrayList<>();

    /** The final path of nodes from start to goal. */
    public ArrayList<Node> pathList = new ArrayList<>();
    // ArrayList<Node> openList = new ArrayList<>();

    /** start, goal and current nodes of the path. */
    Node startNode, goalNode, currentNode;

    /** flag indicating whether the goal has been reached.*/
    boolean goalReached = false;

    /** Step counter to prevent infinite loops. */
    int step = 0;

    /**
     * Constructs a pathFinder instance with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public pathFinder(GamePanel gp){
        this.gp = gp;
        createNodes();
    }

    /**
     * Initializes the 2D array of nodes based on the game world's column and row size.
     * Each node represents a tile and stores its coordinates.
     */
    public void createNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col, row);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    /**
     * Resets the nodes to their initial state to prepare for new pathfinding operation.
     */
    public void resetNodes(){
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row].open = false;
            node[col][row].explored = false;
            node[col][row].solid = false;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /**
     * Sets the start and goal nodes for the pathfinding.
     * Also calculates the cost values and marks solid tiles.
     *
     * @param startCol the column of the start node
     * @param startRow the row of the start node
     * @param goalCol the column of the goal node
     * @param goalRow the row of the goal node
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        if(goalCol >= gp.maxWorldCol || goalCol < 0 || goalRow >= gp.maxWorldRow || goalRow < 0 ||
        startCol >= gp.maxWorldCol || startCol < 0 || startRow >= gp.maxWorldRow || startRow < 0) {
         System.out.println("Invalid coordinates: start(" + startCol + "," + startRow + 
                           "), goal(" + goalCol + "," + goalRow + ")");
         return;
        }

        resetNodes();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];

        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            int tileNum = gp.tileM.mapTileNum[col][row];
            if(gp.tileM.checkCollisionOfTile(tileNum)){
                node[col][row].solid = true;
            }

            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }

        }
    }

    /**
     * Calculates the G (movement) and H (heuristic) costs for the given node.
     * The final cost (F) is the sum of G and H.
     *
     * @param node the node to calculate the cost for
     */
    public void getCost(Node node){

        //G cost (Cummulative cost function)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row -  startNode.row);

        node.gCost = xDistance + yDistance;

        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row -  goalNode.row);

        //heuristic Manhattan Distance
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Performs A* pathfiniding algorithm to find a path from the start node to the goal node.
     *
     * @return {@code true} if the goal is reached, {@code false} otherwise
     */
    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.explored = true;
            openList.remove(currentNode);

            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i< openList.size(); i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }

                }

            }
            if(openList.isEmpty()){
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
        }
        return goalReached;
    }



    /**
     * Adds a neighbouring node to the open list if it's not explored or solid.
     *
     * @param node The node to consider opening.
     */
    public void openNode(Node node){
        if(node.open == false && node.explored == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Traces the path from the goal node back to the start node.
     * adds the shortest path to {@code pathList}.
     */
    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }

    /**
     * Finds a path for a given entity to move towards the goal position.
     * Also updates the entity's direction based on teh next step in the path
     *
     * @param goalCol the column of the goal position
     * @param goalRow the row of the goal position
     * @param entity the entity to find the path for
     */
    public void searchPath(int goalCol, int goalRow, Entity entity){
        // System.out.println(myPath);

        
        RegularGoblin goblin = (RegularGoblin) entity;

        int startCol = (entity.WorldX + entity.hitboxDefaultX )/gp.tileSize;
        int startRow = (entity.WorldY + entity.hitboxDefaultY)/gp.tileSize;

        gp.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        boolean pathFound = gp.pathFinder.search();
        // if (!pathFound) {
        //     System.out.println("No path found from (" + startCol + "," + startRow +
        //                        ") to (" + goalCol + "," + goalRow + ")");
        //     randomMovement();
        // }
        if(pathFound){
            goblin.myPath.clear();
            goblin.myPath.addAll(gp.pathFinder.pathList);

            int nextX = gp.pathFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.getFirst().row * gp.tileSize;

            int enLeftX = entity.WorldX + entity.hitboxDefaultX;
            int enRightX = entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width;
            int enTopY = entity.WorldY + entity.hitboxDefaultY;
            int enBottomY = entity.WorldY + entity.hitboxDefaultY + entity.collisionArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                entity.direction = Direction.UP;
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                entity.direction = Direction.DOWN;
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                if(enLeftX > nextX){
                    entity.direction = Direction.LEFT;
                }
                else{
                    entity.direction = Direction.RIGHT;
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                entity.direction = Direction.UP;

                gp.collisionChecker.checkTileCollision(entity);

                if(entity.collisionOn){
                    entity.direction = Direction.LEFT;
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                entity.direction = Direction.UP;

                gp.collisionChecker.checkTileCollision(entity);

                if(entity.collisionOn){
                    entity.direction = Direction.RIGHT;
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                entity.direction = Direction.DOWN;

                gp.collisionChecker.checkTileCollision(entity);

                if(entity.collisionOn){
                    entity.direction = Direction.LEFT;
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                entity.direction = Direction.DOWN;

                gp.collisionChecker.checkTileCollision(entity);

                if(entity.collisionOn){
                    entity.direction = Direction.RIGHT;
                }
            }

        }



    }
}

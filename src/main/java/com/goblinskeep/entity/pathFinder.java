package com.goblinskeep.entity;

import java.util.ArrayList;

import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
// import com.goblinskeep.tile.TileManager;

public class pathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    // ArrayList<Node> openList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    
    public pathFinder(GamePanel gp){
        this.gp = gp;
        createNodes();
    }

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



    public void openNode(Node node){
        if(node.open == false && node.explored == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }

    public void searchPath(int goalCol, int goalRow, Entity entity){
        // System.out.println(myPath);
        if (!(entity instanceof RegularGoblin)) {
            System.out.println("Wrong entity passed, pass in RegularGoblin!");
            return; // Exit if not a RegularGoblin
        }
        
        RegularGoblin goblin = (RegularGoblin) entity;
        if(goblin.onPath){
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

                if(goblin.myPath.isEmpty()){
                    goblin.onPath = false;
                    return;
                }
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

                    gp.collisionChecker.checkTile(entity);

                    if(entity.collisionOn){
                        entity.direction = Direction.LEFT;
                    }
                }
                else if(enTopY > nextY && enLeftX < nextX){
                    entity.direction = Direction.UP;

                    gp.collisionChecker.checkTile(entity);

                    if(entity.collisionOn){
                        entity.direction = Direction.RIGHT;
                    }
                }
                else if(enTopY < nextY && enLeftX > nextX){
                    entity.direction = Direction.DOWN;

                    gp.collisionChecker.checkTile(entity);

                    if(entity.collisionOn){
                        entity.direction = Direction.LEFT;
                    }
                }
                else if(enTopY < nextY && enLeftX < nextX){
                    entity.direction = Direction.DOWN;

                    gp.collisionChecker.checkTile(entity);

                    if(entity.collisionOn){
                        entity.direction = Direction.RIGHT;
                    }
                }
    
                // int nextCol = gp.pathFinder.pathList.get(0).col;
                // int nextRow = gp.pathFinder.pathList.get(0).row;
                // if(nextCol == goalCol && nextRow == goalRow){
                //     onPath = false;
                // }
            }
        }


    }
}

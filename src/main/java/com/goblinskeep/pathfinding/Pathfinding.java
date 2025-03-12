package com.goblinskeep.pathfinding;


import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Entity;
import com.goblinskeep.tile.Tile;
import com.goblinskeep.tile.TileManager;

import java.util.ArrayList;


public class Pathfinding {
    TileManager tileM;
    com.goblinskeep.app.GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node start, goal, current;
    private boolean goalReached = false;


    public Pathfinding(GamePanel gp)
    {
        this.gp = gp;
        tileM = gp.tileM;
        createNodes();
    }


    private void createNodes()
    {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            node[col][row] = new Node(col, row);
            col++;

            if (col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }


    private void resetNodes()
    {
        int col = 0;
        int row = 0;

        createNodes();

        while (col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            node[col][row].open = false;
            node[col][row].blocked = false;
            node[col][row].checked = false;

            col++;

            if (col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
    }


    public void setNodes(int startCol, int startRow, int goalCol, int goalRow)
    {
        if (goalCol > gp.maxWorldCol || goalCol < 0 || goalRow > gp.maxWorldRow || goalRow < 0)
        {
            return;
        }

        resetNodes();
        start = node[startCol][startRow];
        current = start;
        goal = node[goalCol][goalRow];

        openList.add(current);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            int tileNum = gp.tileM.mapTileNum[col][row];
            if (tileM.checkCollisionOfTile(tileNum))
            {
                node[col][row].blocked = true;
            }

            getCost(node[col][row]);
            col++;

            if (col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }


    private void getCost(Node node)
    {

        int xDist = Math.abs(node.col - start.col);
        int yDist = Math.abs(node.row - start.row);
        node.gCost = xDist + yDist;


        xDist = Math.abs(node.col - goal.col);
        yDist = Math.abs(node.row - goal.row);
        node.hCost = xDist + yDist;


        node.fCost = node.gCost + node.hCost;
    }


    public boolean search()
    {
        while(!goalReached)
        {
            int col = current.col;
            int row = current.row;

            current.checked = true;
            openList.remove(current);
            if (row - 1 >= 0)
            {
                openNode(node[col][row - 1]);
            }
            if (col - 1 >= 0)
            {
                openNode(node[col - 1][row]);
            }
            if (row + 1 < gp.maxWorldRow)
            {
                openNode(node[col][row + 1]);
            }
            if (col + 1 < gp.maxWorldCol)
            {
                openNode(node[col + 1][row]);
            }
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            for (int i = 0; i < openList.size(); i++)
            {
                if (openList.get(i).fCost < bestNodeFCost)
                {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodeFCost)
                {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                    {
                        bestNodeIndex = i;
                    }
                }
            }
            if(openList.isEmpty())
            {
                break;
            }

            current = openList.get(bestNodeIndex);
            if(current == goal)
            {
                goalReached = true;
                pathTracker();
            }
        }

        return goalReached;
    }

    private void openNode(Node node)
    {
        if(!node.open && !node.checked && !node.blocked)
        {
            node.open = true;
            node.parent = current;
            openList.add(node);
        }
    }

    /**
     * Adds all of the best nodes to the pathList which will be used by goblin to determine how to move towards Player
     */
    private void pathTracker()
    {
        Node currentNode = goal;

        while (currentNode != start)
        {
            pathList.addFirst(currentNode);
            currentNode = currentNode.parent;
        }
    }

    public void searchPath(int goalCol, int goalRow, Entity entity)
    {
        int currCol = (entity.WorldX + entity.hitboxDefaultX) / gp.tileSize;
        int currRow = (entity.WorldY + entity.hitboxDefaultY) / gp.tileSize;
        gp.pathFinder.setNodes(currCol, currRow, goalCol, goalRow);
        boolean goalReached = gp.pathFinder.search();

        if (goalReached)
        {
            int nextX = gp.pathFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.getFirst().row * gp.tileSize;

            int goblinLeftX = entity.WorldX + entity.hitboxDefaultX;
            int goblinRightX = entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width;
            int goblinTopY = entity.WorldY + entity.hitboxDefaultY;
            int goblinBotY = entity.WorldY + entity.hitboxDefaultY + entity.collisionArea.height;
            if (goblinTopY > nextY && goblinLeftX >= nextX && goblinRightX < nextX + gp.tileSize)
            {
                entity.direction = Direction.UP;
            }
            else if (goblinTopY < nextY && goblinLeftX >= nextX && goblinRightX < nextX + gp.tileSize)
            {
                entity.direction = Direction.DOWN;
            }
            else if (goblinTopY >= nextY && goblinBotY < nextY + gp.tileSize)
            {
                if (goblinLeftX > nextX)
                {
                    entity.direction = Direction.LEFT;
                }
                if (goblinLeftX < nextX)
                {
                    entity.direction = Direction.RIGHT;
                }

            }
            else if (goblinTopY > nextY && goblinLeftX > nextX)
            {
                entity.direction = Direction.UP;

                gp.collisionChecker.checkTile(entity);

                if (entity.collisionOn)
                {
                    entity.direction = Direction.LEFT;
                }
            }
            else if(goblinTopY > nextY && goblinLeftX < nextX)
            {
                entity.direction = Direction.UP;

                gp.collisionChecker.checkTile(entity);

                if (entity.collisionOn)
                {
                    entity.direction = Direction.RIGHT;
                }
            }
            else if (goblinTopY < nextY && goblinLeftX > nextX)
            {
                entity.direction = Direction.DOWN;

                gp.collisionChecker.checkTile(entity);

                if (entity.collisionOn)
                {
                    entity.direction = Direction.LEFT;
                }

            }
            else if (goblinTopY < nextY && goblinLeftX < nextX)
            {
                entity.direction = Direction.DOWN;

                gp.collisionChecker.checkTile(entity);

                if (entity.collisionOn)
                {
                    entity.direction = Direction.RIGHT;
                }
            }
        }
    }
}


package com.goblinskeep.pathFinder;


import com.goblinskeep.app.Direction;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.entity.Entity;
import com.goblinskeep.tile.TileManager;

import java.util.ArrayList;


/**
 * Handles the pathfinding with A* logic for entities in the game.
 */
public class Pathfinding {
    private boolean goalReached = false;
    private TileManager tileM;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    public Node start, goal, current;
    public Node[][] node;
    private GamePanel gp;


    /**
     * Constructs a Pathfinding instance with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public Pathfinding(GamePanel gp) {
        this.gp = gp;
        tileM = gp.tileM;
        createNodes();
    }


    /**
     * Creates nodes for the pathfinding grid.
     */
    private void createNodes() {
        int col = 0;
        int row = 0;
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }


    /**
     * Resets the nodes to their initial state.
     */
    private void resetNodes() {
        int col = 0;
        int row = 0;
        createNodes();
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].blocked = false;
            node[col][row].checked = false;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
    }


    /**
     * Sets the start and goal nodes for the pathfinding.
     *
     * @param startCol the column of the start node
     * @param startRow the row of the start node
     * @param goalCol the column of the goal node
     * @param goalRow the row of the goal node
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        if (goalCol > gp.maxWorldCol || goalCol < 0 || goalRow > gp.maxWorldRow || goalRow < 0) {
            return;
        }
        resetNodes();
        start = node[startCol][startRow];
        current = start;
        goal = node[goalCol][goalRow];

        openList.add(current);
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            int tileNum = gp.tileM.mapTileNum[col][row];
            if (tileM.checkCollisionOfTile(tileNum)) {
                node[col][row].blocked = true;
            }
            getCost(node[col][row]);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }


    /**
     * Calculates the cost for a node.
     *
     * @param node the node to calculate the cost for
     */
    private void getCost(Node node) {
        int yDistance = Math.abs(node.row - start.row);
        int xDistance = Math.abs(node.col - start.col);
        node.gCost = yDistance + xDistance ;
        yDistance = Math.abs(node.row - goal.row);
        xDistance = Math.abs(node.col - goal.col);
        node.hCost = xDistance + yDistance;
        node.fCost = node.gCost + node.hCost;
    }


    /**
     * Searches for a path from the start node to the goal node.
     *
     * @return true if the goal is reached, false otherwise
     */
    public boolean search() {
        while(!goalReached) {
            current.checked = true;
            int row = current.row;
            int col = current.col;
            openList.remove(current);
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }
            if (col + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);
            }
            int bestNodeFCost = 999;
            int bestNodeIndex = 0;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodeFCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            if(openList.isEmpty()) {
                break;
            }

            current = openList.get(bestNodeIndex);
            if(current == goal) {
                goalReached = true;
                pathTracker();
            }
        }

        return goalReached;
    }

    /**
     * Opens a node for pathfinding.
     *
     * @param node the node to open
     */
    private void openNode(Node node) {
        if( !node.blocked&& !node.open && !node.checked) {
            node.parent = current;
            node.open = true;
            openList.add(node);
        }
    }

    /**
     * Tracks the path from the goal node to the start node.
     */
    private void pathTracker() {
        Node currentNode = goal;
        while (currentNode != start) {
            pathList.addFirst(currentNode);
            currentNode = currentNode.parent;
        }
    }

    /**
     * Searches for a path for an entity to a goal position.
     *
     * @param goalCol the column of the goal position
     * @param goalRow the row of the goal position
     * @param entity the entity to find the path for
     */
    public void searchPath(int goalCol, int goalRow, Entity entity) {
        int currCol = (entity.WorldX + entity.hitboxDefaultX) / gp.tileSize;
        int currRow = (entity.WorldY + entity.hitboxDefaultY) / gp.tileSize;
        gp.pathFinder.setNodes(currCol, currRow, goalCol, goalRow);
        boolean goalReached = gp.pathFinder.search();
        if (goalReached) {
            int nextX = gp.pathFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.getFirst().row * gp.tileSize;

            int goblinLeftX = entity.WorldX + entity.hitboxDefaultX;
            int goblinRightX = entity.WorldX + entity.hitboxDefaultX + entity.collisionArea.width;
            int goblinTopY = entity.WorldY + entity.hitboxDefaultY;
            int goblinBotY = entity.WorldY + entity.hitboxDefaultY + entity.collisionArea.height;
            if (goblinTopY > nextY && goblinLeftX >= nextX && goblinRightX < nextX + gp.tileSize) {
                entity.direction = Direction.UP;
            } else if (goblinTopY < nextY && goblinLeftX >= nextX && goblinRightX < nextX + gp.tileSize) {
                entity.direction = Direction.DOWN;
            } else if (goblinTopY >= nextY && goblinBotY < nextY + gp.tileSize) {
                if (goblinLeftX > nextX) {
                    entity.direction = Direction.LEFT;
                }
                if (goblinLeftX < nextX) {
                    entity.direction = Direction.RIGHT;
                }
            } else if (goblinTopY > nextY && goblinLeftX > nextX) {
                entity.direction = Direction.UP;
                gp.collisionChecker.checkTile(entity);
                if (entity.collisionOn) {
                    entity.direction = Direction.LEFT;
                }
            } else if(goblinTopY > nextY && goblinLeftX < nextX) {
                entity.direction = Direction.UP;
                gp.collisionChecker.checkTile(entity);
                if (entity.collisionOn) {
                    entity.direction = Direction.RIGHT;
                }
            }
            else if (goblinTopY < nextY && goblinLeftX > nextX) {
                entity.direction = Direction.DOWN;
                gp.collisionChecker.checkTile(entity);
                if (entity.collisionOn) {
                    entity.direction = Direction.LEFT;
                }

            } else if (goblinTopY < nextY && goblinLeftX < nextX) {
                entity.direction = Direction.DOWN;
                gp.collisionChecker.checkTile(entity);
                if (entity.collisionOn) {
                    entity.direction = Direction.RIGHT;
                }
            }
        }
    }
}


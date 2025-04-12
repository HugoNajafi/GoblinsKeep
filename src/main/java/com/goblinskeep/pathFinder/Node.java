package com.goblinskeep.pathFinder;


/**
 * Represents a node in the pathfinding grid.
 */

public class Node {
    /** initialize parent Node */
    Node parent;
    /** initialize the integer that keeps track of the column it represents on the map. */
    public int col;
    /** initialize the integer that keeps track of the row it represents on the map. */
    public int row;
    /** initialize the integers that store the costs of paths. */
    int gCost, hCost, fCost;
    /** flags for nodes to check if they have been explored or can be explored.*/
    boolean solid, open, explored;



    /**
     * Constructs a Node with the specified column and row positions.
     *
     * @param col the column position of the node
     * @param row the row position of the node
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
}

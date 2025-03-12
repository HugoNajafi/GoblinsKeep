package com.goblinskeep.pathFinder;

/**
 * Represents a node in the pathfinding grid.
 */
public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean blocked;
    boolean open;
    boolean checked;


    /**
     * Constructs a Node with the specified column and row positions.
     *
     * @param col the column position of the node
     * @param row the row position of the node
     */
    public Node(int col, int row)
    {
        this.col = col;
        this.row = row;
    }

}

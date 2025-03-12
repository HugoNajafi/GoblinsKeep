package com.goblinskeep.entity;

public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost, hCost, fCost;
    boolean solid, open, explored;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
    
}

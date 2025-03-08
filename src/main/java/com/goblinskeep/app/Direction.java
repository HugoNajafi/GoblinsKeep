package com.goblinskeep.app;

public enum Direction {
    UP (0,1), 
    DOWN (0, -1), 
    LEFT (-1, 0), 
    RIGHT (1, 0);

    private final int dx; //change in x
    private final int dy; //change in y

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx(){
        // System.out.println(dx);
        return dx;
    }
    
    public int getDy(){
        // System.out.println(dy);
        return dy;
    }

}

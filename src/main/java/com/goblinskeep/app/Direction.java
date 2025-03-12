package com.goblinskeep.app;

/**
 * Represents the four possible movement directions in a 2D space.
 * Each direction has associated changes in x (dx) and y (dy) coordinates.
 */
public enum Direction {

    /** Moves up: increases y by 1. */
    UP (0,1),

    /** Moves down: decreases y by 1. */
    DOWN (0, -1),

    /** Moves left: decreases x by 1. */
    LEFT (-1, 0),

    /** Moves right: increase x by 1. */
    RIGHT (1, 0);

    /** The change in the x-coordinate when moving in this direction. */
    private final int dx; //change in x

    /** The change in the y-coordinate when moving in this direction. */
    private final int dy; //change in y

    /**
     * Constructs a Direction enum with specified x and y changes.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets the change in the x-coordinate for this direction.
     *
     * @return The change in x-coordinate.
     */
    public int getDx(){
        return dx;
    }

    /**
     * Gets the change in the y-coordinate for this direction.
     *
     * @return The change in y-coordinate.
     */
    public int getDy(){
        return dy;
    }

}

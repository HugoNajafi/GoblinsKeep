package com.goblinskeep.App;

import java.util.ArrayList;

import javax.swing.text.Position;

import com.goblinskeep.Mapreader;

public enum CellType{
    Empty,
    Wall,
    Trap
}


public class Gamestate {
    private CellType[][] grid;
    private Position playerPosition;
    private ArrayList<Position> enemyPositions;
    private int score;
    private int width, height = read(map);
    private boolean gameOver;

    public GameState(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new CellType[width][height];

        for (int x=0; x < width; x++){
            for(int y=0; y < height; y++){
                grid[x][y] = CellType.Empty;
            }
        }
        this.gameOver = false;
        this.enemyPositions = new ArrayList<>();
        this.score = 0;
    }

    public GameState(GameState other){
        this.width = other.width;
        this.height = other.height;

        this.grid = new CellType[width][height];
        for (int x=0; x < width; x++){
            for(int y=0; y < height; y++){
                this.grid[x][y] = other.grid[x][y];
            }
        }

            this.playerPosition = new Position(
            other.playerPosition.getX(),
            other.playerPosition.getY()
        );
        
        this.enemyPositions = new ArrayList<>();
        for (Position pos : other.enemyPositions) {
            this.enemyPositions.add(new Position(pos.getX(), pos.getY()));
        }
    }

    
}

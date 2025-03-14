package com.goblinskeep.pathFinder;

// import com.goblinskeep.app.GamePanel;

// import java.beans.Transient;

public class Circle {
    // public GamePanel gp;
    public int radius;
    public int centerx, centery;

    public Circle(int radius, int centerx, int centery){
        // this.gp = gp;
        this.radius = radius;
        this.centerx = centerx;
        this.centery = centery;
    }

    public boolean intersects(int Pointx, int Pointy){
        boolean intersect = false;
        double Ax = Math.pow(Pointx - centerx,2);
        double Bx = Math.pow(Pointy - centery,2);
        double Distance = Math.sqrt(Ax + Bx);
        if(Distance <= radius){
            intersect = true;
        }
        return intersect; 
    }

    public void checkView(int Pointx, int Pointy){
        // boolean viewBlocked = false;
        //find slope of line
        // int rise = (Pointy - centery);
        // int run = (Pointx - centerx);
        // int slope = rise/run;
        // int tileNum;
        // for(int i = centerx; i < Pointx; i++){
        //     //Check each point on the line
        //     int line = (slope * (i-centerx)) + centery;
        //     // System.out.println(i + ", " + line);
        //     //Check the tile at that point
        //     // tileNum = gp.tileM.mapTileNum[i][line];
        //     // //If that tile is solid, then viewBlocked
        //     // if(gp.tileM.tile[tileNum].collision){
        //     //     viewBlocked = true;
        //     // }
        // }
        // return viewBlocked;
    }

    // @Test
    // public void test_intersects_outsideCircle() {
    //     final Circle circle = new Circle(1, 0, 0);
    //     assertFalse(circle.intersects(2, 2));
    // }
} 

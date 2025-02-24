package com.goblinskeep.entity;

import java.util.PriorityQueue;
import java.util.Set;

public class SmartGoblin extends Goblin{
    public void getAction(){
        PriorityQueue<Integer> Frontier = new PriorityQueue<Integer>();
        Set<Integer> explored = new Set<Integer>() {};
        Frontier.add((null))


        
    }

    public int ManhattanDistance(){
        return Math.abs(super.player.WorldX - this.WorldX) + Math.abs(super.player.WorldY - this.WorldY);
    }
    
}



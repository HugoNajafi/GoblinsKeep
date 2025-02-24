package com.goblinskeep.App;

record Pair<C,R>(C column, R row){}

public Mapreader {
    public Pair<Integer,Integer> read(String map){
        String[] lines = map.split("\n");
        int row = lines.length;
        int col = lines[0].length();
        return new Pair<>(col, row);
    }
}

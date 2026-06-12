package com.utils;

public class Arc {
    Position pos;

    public Arc(int x, int y){
        this.pos = new Position(x, y);
    }

    @Override
    public String toString() {
        return "x: " + pos.x + ", y: " + pos.y;
    }
}

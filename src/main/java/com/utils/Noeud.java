package com.utils;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
    List<Arc> arcs;
    Position pos;

    public Noeud(int x, int y){
        this.pos = new Position(x, y);
        arcs = new ArrayList<Arc>();
    }

    @Override
    public String toString() {
        String result = "x : " + pos.x + " - y : " + pos.y + " ---|> ";
        for (Arc arc : arcs) {
            result += "[" + arc.toString() + "]";
        }
        return result;
    }
}

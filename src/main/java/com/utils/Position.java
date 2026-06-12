package com.utils;

public class Position implements Comparable<Position>{
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Position autre) {
        // On compare d'abord sur l'axe x
        if (this.x != autre.x) {
            return Integer.compare(this.x, autre.x);
        }
        // Si les x sont identiques, on compare sur l'axe y
        return Integer.compare(this.y, autre.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "[x:" + x + ", y:" + y + "]";
    }
}

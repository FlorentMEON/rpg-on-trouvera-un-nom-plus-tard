package com;

import com.labyrinthe.cases.Gate;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public abstract class GameObject {
    public double x;
    public double y;
    public Rectangle2D hitbox;

    public GameObject(double x,double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle2D(x+width/2,y+height/2,width,height);
    }

    public Rectangle2D getHitbox()
    {
        return hitbox;
    }

    public Rectangle2D getHitboxAt(double x,double y)
    {
        return new Rectangle2D(x+hitbox.getWidth()/2,y+hitbox.getHeight()/2,hitbox.getWidth(),hitbox.getHeight());
    }

    public abstract Pane getDisplay();
}

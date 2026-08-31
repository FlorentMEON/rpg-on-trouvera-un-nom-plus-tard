package com.entities;

import com.GameObject;
import com.moteur.Controler;
import com.moteur.Jeu;
import javafx.scene.layout.Pane;

public abstract class Entity extends GameObject {
    public double speed;

    public Entity(double x, double y, double width, double height, double speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public abstract Pane getDisplay();

    public abstract void evoluer(Controler controler, Jeu jeu);

    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }
}

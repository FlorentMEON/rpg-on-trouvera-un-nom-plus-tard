package com.entities;

import com.Moteur.Controler;
import com.Moteur.Jeu;
import javafx.scene.layout.Pane;

public abstract class Entity {
    public int x;
    public int y;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract Pane getDisplay();

    public abstract void evoluer(Controler controler, Jeu jeu);

    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

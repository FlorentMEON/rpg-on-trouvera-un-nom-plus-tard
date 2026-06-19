package com.entities;

import com.Moteur.Controler;
import com.Moteur.Jeu;
import javafx.scene.layout.Pane;

public class Enemy extends Entity {

    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public Pane getDisplay() {
        return null;
    }

    @Override
    public void evoluer(Controler controler, Jeu jeu) {

    }
}

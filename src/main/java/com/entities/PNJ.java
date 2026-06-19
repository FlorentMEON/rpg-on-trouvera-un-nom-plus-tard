package com.entities;

import com.Moteur.Controler;
import com.Moteur.Jeu;
import javafx.scene.layout.Pane;

public class PNJ extends Entity {

    public PNJ(int x, int y) {
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

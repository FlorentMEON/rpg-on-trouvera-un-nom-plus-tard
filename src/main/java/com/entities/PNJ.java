package com.entities;

import com.moteur.Controler;
import com.moteur.Jeu;
import javafx.scene.layout.Pane;

public class PNJ extends Entity {

    public PNJ(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public Pane getDisplay() {
        return null;
    }

    @Override
    public void evoluer(Controler controler, Jeu jeu) {

    }
}

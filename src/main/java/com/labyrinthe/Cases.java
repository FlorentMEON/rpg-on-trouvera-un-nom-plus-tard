package com.labyrinthe;

import com.Moteur.Jeu;
import javafx.scene.layout.Pane;

public interface Cases {

    public boolean collision();

    public void activation(Jeu jeu);

    public Pane getDisplay();
}

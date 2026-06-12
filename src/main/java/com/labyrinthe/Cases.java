package com.labyrinthe;

import javafx.scene.layout.Pane;

public interface Cases {

    public boolean collision();

    public void activation(Jeu jeu);

    public Pane getDisplay();
}

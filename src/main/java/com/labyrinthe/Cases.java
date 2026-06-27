package com.labyrinthe;

import com.Moteur.Jeu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.layout.Pane;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface Cases {

    public boolean collision();

    public void activation(Jeu jeu);

    @JsonIgnore
    public Pane getDisplay();
}

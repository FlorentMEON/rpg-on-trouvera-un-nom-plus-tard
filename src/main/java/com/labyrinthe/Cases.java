package com.labyrinthe;

import com.Moteur.Jeu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Cases {

    public abstract boolean collision();

    public abstract void activation(Jeu jeu);

    @JsonIgnore
    public abstract Pane getDisplay();

    @JsonIgnore
    public abstract ImageView getDisplayEditor(int taille);

    public String toString(){
        return getClass().getName();
    }
}

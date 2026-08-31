package com.labyrinthe;

import com.moteur.Jeu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Cases {

    /**
     * Test si on peut traverser la case ou non
     *
     * @return
     */
    public abstract boolean collision();

    public abstract void activation(Jeu jeu);

    @JsonIgnore
    public abstract Node getDisplay();

    @JsonIgnore
    public abstract ImageView getDisplayEditor(int taille);

    public String toString(){
        return getClass().getName();
    }
}

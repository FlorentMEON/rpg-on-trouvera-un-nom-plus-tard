package com.labyrinthe.cases;

import com.AffichageJeu;
import com.labyrinthe.Cases;
import com.labyrinthe.Jeu;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Vide implements Cases {

    @Override
    public boolean collision() {
        return true;
    }

    @Override
    public void activation(Jeu jeu) {}

    public Pane getDisplay() {
        ImageView iv = new ImageView(ImageCache.getImage("/images/stone.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }
}

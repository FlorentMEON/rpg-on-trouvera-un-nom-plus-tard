package com.labyrinthe.cases;

import com.Moteur.AffichageJeu;
import com.labyrinthe.Cases;
import com.Moteur.Jeu;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Murs implements Cases {
    private char id = '#';

    @Override
    public boolean collision() {
        return false;
    }

    @Override
    public void activation(Jeu jeu) {}

    @Override
    public Pane getDisplay(){
        ImageView iv = new ImageView(ImageCache.getImage("/images/mur.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }
}

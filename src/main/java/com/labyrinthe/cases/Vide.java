package com.labyrinthe.cases;

import com.moteur.AffichageJeu;
import com.labyrinthe.Cases;
import com.moteur.Jeu;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Vide extends Cases {

    public Vide(){}

    @Override
    public boolean collision() {
        return true;
    }

    @Override
    public void activation(Jeu jeu) {}

    @Override
    public Pane getDisplay() {
        ImageView iv = new ImageView(ImageCache.getImage("/images/editeur/icons/sol/vide.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }

    @Override
    public ImageView getDisplayEditor(int taille) {
        ImageView iv = new ImageView(ImageCache.getImage("/images/editeur/icons/sol/vide.png"));
        iv.setFitWidth(taille);
        iv.setPreserveRatio(true);

        return iv;
    }
}

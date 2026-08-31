package com.labyrinthe.cases.sol;

import com.moteur.AffichageJeu;
import com.moteur.Jeu;
import com.labyrinthe.Cases;
import com.labyrinthe.cases.Sol;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Grass extends Cases implements Sol
{
    @Override
    public boolean collision()
    {
        return false;
    }

    @Override
    public void activation(Jeu jeu)
    {

    }

    @Override
    public Pane getDisplay() {
        ImageView iv = new ImageView(ImageCache.getImage("/images/vide.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }

    @Override
    public ImageView getDisplayEditor(int taille) {
        ImageView iv = new ImageView(ImageCache.getImage("/images/vide.png"));
        iv.setFitWidth(taille);
        iv.setPreserveRatio(true);

        return iv;
    }
}

package com.labyrinthe.cases;

import com.Moteur.AffichageJeu;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Water extends Murs{

    public Water(){}

    @Override
    public Pane getDisplay(){
        ImageView iv = new ImageView(ImageCache.getImage("/images/water.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }

    @Override
    public ImageView getEditor(int taille){
        ImageView iv = new ImageView(ImageCache.getImage("/images/water.png"));
        iv.setFitWidth(taille);
        iv.setPreserveRatio(true);

        return iv;
    }
}

package com.labyrinthe.cases;

import com.moteur.AffichageJeu;
import com.labyrinthe.Cases;
import com.moteur.Jeu;
import com.utils.ImageCache;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Murs extends Cases {
    private char id = '#';

    public Murs(){}

    @Override
    public boolean collision() {
        return false;
    }

    @Override
    public void activation(Jeu jeu) {}

    @Override
    public Node getDisplay(){
        Box boxMurs = new Box(AffichageJeu.TAILLE, AffichageJeu.TAILLE, AffichageJeu.TAILLE);
        boxMurs.setLayoutX((double) AffichageJeu.TAILLE /2);
        boxMurs.setLayoutY(AffichageJeu.TAILLE);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(ImageCache.getImage("/images/mur.png"));
        boxMurs.setMaterial(material);

        return boxMurs;
    }

    @Override
    public ImageView getDisplayEditor(int taille) {
        ImageView iv = new ImageView(ImageCache.getImage("/images/mur.png"));
        iv.setFitWidth(taille);
        iv.setPreserveRatio(true);

        return iv;
    }
}

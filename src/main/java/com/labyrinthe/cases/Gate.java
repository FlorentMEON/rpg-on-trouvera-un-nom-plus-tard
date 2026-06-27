package com.labyrinthe.cases;

import com.Moteur.AffichageJeu;
import com.Moteur.Jeu;
import com.labyrinthe.Cases;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Gate extends Cases {
    private String link;

    @Override
    public boolean collision() {
        return false;
    }

    @Override
    public void activation(Jeu jeu) {

    }

    @Override
    public Pane getDisplay() {
        ImageView iv = new ImageView(ImageCache.getImage("/images/gate.png"));
        iv.setFitWidth(AffichageJeu.TAILLE);
        iv.setPreserveRatio(true);

        return new Pane(iv);
    }

    @Override
    public ImageView getDisplayEditor(int taille) {
        ImageView iv = new ImageView(ImageCache.getImage("/images/gate.png"));
        iv.setFitWidth(taille);
        iv.setPreserveRatio(true);

        return iv;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getLink() {return link;}

    public String toString(){
        return "link : " + link;
    }
}

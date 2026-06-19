package com.entities;

import com.Moteur.AffichageJeu;
import com.Moteur.Controler;
import com.Moteur.Jeu;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Player extends Entity {
    public int PV;

    public Player(int x,int y){
        super(x, y);
        PV = 20;
    }

    @Override
    public Pane getDisplay() {
        ImageView imageView = new ImageView(ImageCache.getImage("/images/player.png"));
        StackPane pane = new StackPane(imageView);
        imageView.setFitHeight(AffichageJeu.TAILLE);
        imageView.setPreserveRatio(true);
        return pane;
    }

    @Override
    public void evoluer(Controler controler, Jeu jeu) {
        int newX = getX();
        int newY = getY();

        if (controler.move.HAUT)   newY--;
        if (controler.move.BAS)    newY++;
        if (controler.move.GAUCHE) newX--;
        if (controler.move.DROITE) newX++;

        if (newX < 0 || newX >= jeu.laby.getWidth()) return;
        if (newY < 0 || newY >= jeu.laby.getHeight()) return;
        if (!jeu.laby.getCase(newX, newY).collision()) return;

        // Vérification de la collision avec un monstre
        if (!jeu.checkCollision(newX, newY)) return;

        x = newX;
        y = newY;
    }
}

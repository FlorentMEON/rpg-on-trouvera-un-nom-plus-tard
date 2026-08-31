package com.entities;

import com.moteur.AffichageJeu;
import com.moteur.Controler;
import com.moteur.Jeu;
import com.utils.ImageCache;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Player extends Entity {
    public int PV;

    public Player(int x,int y){
        super(x, y, 0.5, 0.5, 0.3);
        PV = 20;
    }

    @Override
    public Pane getDisplay() {
        ImageView imageView = new ImageView(ImageCache.getImage("/images/entities/player/player.png"));
        StackPane pane = new StackPane(imageView);
        imageView.setFitHeight(AffichageJeu.TAILLE);
        imageView.setPreserveRatio(true);
        imageView.setTranslateZ((double) -AffichageJeu.TAILLE /2);
        return pane;
    }

    @Override
    public void evoluer(Controler controler, Jeu jeu) {
        double nextX = x;
        double nextY = y;

        // 1. On stocke la direction voulue
        double dx = 0;
        double dy = 0;

        if (controler.move.HAUT)   dy -= 1;
        if (controler.move.BAS)    dy += 1;
        if (controler.move.GAUCHE) dx -= 1;
        if (controler.move.DROITE) dx += 1;

        // 2. On met la norme si on se déplace en diagonale
        if (dx != 0 && dy != 0) {
            // Calcul de la longueur du vecteur
            double length = Math.sqrt(dx * dx + dy * dy);
            // On divise par la longueur pour avoir un vecteur de taille 1, puis on multiplie par la vitesse
            dx = (dx / length) * speed;
            dy = (dy / length) * speed;
        } else {
            dx *= speed;
            dy *= speed;
        }

        // On applique le vecteur
        nextX += dx;
        nextY += dy;

        // 3. TEST DE COLLISION SUR L'AXE X
        Rectangle2D hitboxX = getHitboxAt(nextX, y);
        if (!jeu.checkCollision(hitboxX, this)) {
            x = nextX;
        }

        // 4. TEST DE COLLISION SUR L'AXE Y
        Rectangle2D hitboxY = getHitboxAt(x, nextY);
        if (!jeu.checkCollision(hitboxY, this)) {
            y = nextY;
        }

        // 5. UPDATE DE L'HITBOX
        this.hitbox = getHitboxAt(x, y);
    }
}

package com.Moteur;

import com.entities.Entity;
import com.entities.Player;
import com.labyrinthe.Labyrinthe;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    public Labyrinthe laby;
    public Controler controler;
    public Player player;
    public List<Entity> entities;

    public Jeu(Labyrinthe laby, Player player) {
        this.laby = laby;
        this.player = player;
        this.controler = new Controler();
        this.entities = new ArrayList<>();
    }

    public Jeu(Player personnage,  Labyrinthe laby, List<Entity> entities) {
        this.controler = new Controler();
        this.player = personnage;
        this.laby = laby;
        this.entities = entities;
    }

    public Jeu(Labyrinthe laby) {
        this.laby = laby;
        this.player = new Player(0, 0);
        this.controler = new Controler();
        this.entities = new ArrayList<>();
    }

    public boolean checkCollision(int x, int y) {
        boolean res = true;
        for (Entity m : entities) {
            if (m.getX() == x && m.getY() == y) {
                return false;
            }
        }
        return res; // aucun monstre : libre
    }

    public Node getDisplay(){
        Pane root = new Pane();

        // 1. On donne au conteneur la taille EXACTE de ta résolution d'écran
        root.setPrefSize(AffichageJeu.RESOLUTION[0], AffichageJeu.RESOLUTION[1]);

        // 2. Calcul du point central en PIXELS
        // On prend la position X du joueur, on la convertit en pixels, et on vise le milieu de sa case
        double centreJoueurX = (player.x * AffichageJeu.TAILLE) + (AffichageJeu.TAILLE / 2.0);
        double centreJoueurY = (player.y * AffichageJeu.TAILLE) + (AffichageJeu.TAILLE / 2.0);

        // On déduit la moitié de l'écran pour trouver le coin "en haut à gauche" de notre caméra
        double cameraX = centreJoueurX - (AffichageJeu.RESOLUTION[0] / 2.0);
        double cameraY = centreJoueurY - (AffichageJeu.RESOLUTION[1] / 2.0);

        // 3. Blocage de la caméra aux bords du labyrinthe (pour ne pas voir de vide noir)
        double maxCameraX = (laby.getWidth() * AffichageJeu.TAILLE) - AffichageJeu.RESOLUTION[0];
        double maxCameraY = (laby.getHeight() * AffichageJeu.TAILLE) - AffichageJeu.RESOLUTION[1];

        cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

        // 4. On calcule quelles cases sont visibles (pour ne pas dessiner tout le labyrinthe)
        int minX = Math.max(0, (int) (cameraX / AffichageJeu.TAILLE));
        int maxX = Math.min(laby.getWidth() - 1, (int) ((cameraX + AffichageJeu.RESOLUTION[0]) / AffichageJeu.TAILLE) + 1);

        int minY = Math.max(0, (int) (cameraY / AffichageJeu.TAILLE));
        int maxY = Math.min(laby.getHeight() - 1, (int) ((cameraY + AffichageJeu.RESOLUTION[1]) / AffichageJeu.TAILLE) + 1);

        // 5. On dessine les tuiles en appliquant le décalage de la caméra en PIXELS
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Pane pane = laby.getCase(x, y).getDisplay();

                // C'est ici qu'on gère les "demi-cases" sur les bords !
                pane.setLayoutX((x * AffichageJeu.TAILLE) - cameraX);
                pane.setLayoutY((y * AffichageJeu.TAILLE) - cameraY);

                root.getChildren().add(pane);
            }
        }

        // 6. On dessine les entités avec le même décalage
        for (Entity entity : entities) {
            Pane pane = entity.getDisplay();
            pane.setLayoutX((entity.getX() * AffichageJeu.TAILLE) - cameraX);
            pane.setLayoutY((entity.getY() * AffichageJeu.TAILLE) - cameraY);
            root.getChildren().add(pane);
        }

        return root;
    }
}

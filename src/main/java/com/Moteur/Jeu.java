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
        root.setPrefSize((AffichageJeu.DISTANCE_VUE[0]*2+1)*AffichageJeu.TAILLE, (AffichageJeu.DISTANCE_VUE[1]*2+1)*AffichageJeu.TAILLE);

        int minX = (player.x + AffichageJeu.DISTANCE_VUE[0] >= laby.getWidth()-1 ?
                        laby.getWidth() - (AffichageJeu.DISTANCE_VUE[0] * 2 + 1):
                        Math.max(player.x - AffichageJeu.DISTANCE_VUE[0], 0)
                );
        int minY = (player.y + AffichageJeu.DISTANCE_VUE[1] >= laby.getHeight()-1 ?
                        laby.getHeight() - (AffichageJeu.DISTANCE_VUE[1] * 2 + 1):
                        Math.max(player.y - AffichageJeu.DISTANCE_VUE[1], 0));
        int maxX = Math.min(minX + AffichageJeu.DISTANCE_VUE[0]*2 + 1, laby.getWidth()-1);
        int maxY = Math.min(minY + AffichageJeu.DISTANCE_VUE[1]*2 + 1, laby.getHeight()-1);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Pane pane = laby.getCase(x, y).getDisplay();

                pane.setLayoutX((x-minX)*AffichageJeu.TAILLE);
                pane.setLayoutY((y-minY)*AffichageJeu.TAILLE);

                root.getChildren().add(pane);
            }
        }
        for (Entity entity : entities) {
            Pane pane = entity.getDisplay();
            pane.setLayoutX(AffichageJeu.TAILLE * (entity.getX()-minX));
            pane.setLayoutY(AffichageJeu.TAILLE * (entity.getY()-minY));
            root.getChildren().add(pane);
        }

        return root;
    }

    public void updateDisplay(Scene scene) {
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().clear();
        root.getChildren().add(getDisplay());
    }
}

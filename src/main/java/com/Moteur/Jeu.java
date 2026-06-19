package com.Moteur;

import com.entities.Entity;
import com.entities.Player;
import com.labyrinthe.Labyrinthe;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
        root.setPrefSize(laby.getWidth()*AffichageJeu.TAILLE, laby.getHeight()*AffichageJeu.TAILLE);

        for (int x = 0; x < laby.getWidth(); x++){
            for (int y = 0; y < laby.getHeight(); y++){
                Pane pane = laby.getCase(x, y).getDisplay();

                pane.setLayoutX(x*AffichageJeu.TAILLE);
                pane.setLayoutY(y*AffichageJeu.TAILLE);

                root.getChildren().add(pane);
            }
        }
        for (Entity entity : entities) {
            Pane pane = entity.getDisplay();
            pane.setLayoutX(AffichageJeu.TAILLE * entity.getX());
            pane.setLayoutY(AffichageJeu.TAILLE * entity.getY());
            root.getChildren().add(pane);
        }

        return root;
    }
}

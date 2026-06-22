package com.Moteur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MoteurJeu {
    private Jeu jeu;
    private AffichageJeu affichageJeu;

    public MoteurJeu(Jeu jeu){
        this.jeu = jeu;
    }

    public void lancerJeu(Stage stage, int width, int height) {
        // creation de l'interface graphique
        this.affichageJeu = new AffichageJeu(jeu);
        Scene scene = affichageJeu.getAffichageJeu();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case Z -> {
                    jeu.controler.move.HAUT = true;
                    System.out.println("HAUT");
                    System.out.println("Player : " + jeu.player.x + " " + jeu.player.y);
                }
                case Q -> {
                    jeu.controler.move.GAUCHE = true;
                    System.out.println("GAUCHE");
                    System.out.println("Player : " + jeu.player.x + " " + jeu.player.y);
                }
                case S -> {
                    jeu.controler.move.BAS = true;
                    System.out.println("BAS");
                    System.out.println("Player : " + jeu.player.x + " " + jeu.player.y);
                }
                case D -> {
                    jeu.controler.move.DROITE = true;
                    System.out.println("DROITE");
                    System.out.println("Player : " + jeu.player.x + " " + jeu.player.y);
                }
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case Z -> jeu.controler.move.HAUT = false;
                case Q -> jeu.controler.move.GAUCHE = false;
                case S -> jeu.controler.move.BAS = false;
                case D -> jeu.controler.move.DROITE = false;
            }
        });

        // Moteur VISUEL
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(50), // Ici, on règle les fps du joueur
                        event -> {
                    jeu.updateDisplay(scene);
                })
        );

        // Moteur LOGIC
        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.getKeyFrames().add(
                new KeyFrame(Duration.millis(100), event -> {
                    jeu.player.evoluer(jeu.controler, jeu);
                })
        );
        timeline2.play();
        timeline.play();

        stage.setScene(scene);
        stage.setTitle("RPG");
        stage.show();
    }
}

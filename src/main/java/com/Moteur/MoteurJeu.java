package com.Moteur;

import com.settings.SettingsGraphic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MoteurJeu {
    private Jeu jeu;
    private AffichageJeu affichageJeu;
    private SettingsGraphic settingsGraphic;
    public Stage stage;

    public MoteurJeu(Jeu jeu){
        this.jeu = jeu;
    }

    public void lancerJeu(Stage stage) {
        this.stage = stage;
        this.settingsGraphic = new SettingsGraphic(stage);
        // creation de l'interface graphique
        this.affichageJeu = new AffichageJeu(this, jeu);
        Scene scene = new Scene(affichageJeu.getAffichageJeu());

        // Lorsqu'on presse ces touches
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
                case ESCAPE -> {
                    if (affichageJeu.getListMenu().getFirst().isDisplayed) affichageJeu.getListMenu().getFirst().isDisplayed = false;
                    else affichageJeu.getListMenu().getFirst().isDisplayed = true;
                }
            }
        });

        // Lorsqu'on relâche ces touches
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case Z -> jeu.controler.move.HAUT = false;
                case Q -> jeu.controler.move.GAUCHE = false;
                case S -> jeu.controler.move.BAS = false;
                case D -> jeu.controler.move.DROITE = false;
            }
        });

        // Lorsque l'app n'est plus au 1er plan
        stage.focusedProperty().addListener((observable, ancienEtat, nouveauEtat) -> {
            if (!nouveauEtat) {
                affichageJeu.getListMenu().getFirst().isDisplayed = true;
            }
        });

        // Moteur VISUEL
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(50), // Ici, on règle les fps du joueur
                        event -> {
                    affichageJeu.updateDisplay(scene);
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
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.show();
    }

    public AffichageJeu getAffichageJeu() {
        return affichageJeu;
    }
}

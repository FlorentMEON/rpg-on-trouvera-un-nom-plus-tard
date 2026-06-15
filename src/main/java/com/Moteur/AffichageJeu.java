package com.Moteur;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class AffichageJeu {
    public static int TAILLE = 80;
    private Jeu jeu;

    public AffichageJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public Scene getAffichageJeu() {
        StackPane root = new StackPane();

        root.getChildren().add(jeu.getDisplay());
        Scene scene = new Scene(root);
        return scene;
    }
}

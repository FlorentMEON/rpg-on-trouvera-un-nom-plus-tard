package com.settings;

import com.Moteur.AffichageJeu;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsGraphic {
    Stage stage;
    public int[] resolution;
    public int[] ratio;

    public SettingsGraphic(Stage stage) {
        this.stage = stage;
        Rectangle2D limitesTotales = Screen.getPrimary().getBounds();
        int largeurTotale = (int)(limitesTotales.getWidth());
        int hauteurTotale = (int)(limitesTotales.getHeight());

        // Ton calcul original :
        this.resolution = new int[]{largeurTotale, hauteurTotale};

        // LA LIGNE À AJOUTER : On partage ta résolution avec le reste du jeu
        AffichageJeu.RESOLUTION = this.resolution;

        int casesY = 9;
        AffichageJeu.TAILLE = hauteurTotale / casesY;
        int casesX = (int) Math.ceil((double) largeurTotale / AffichageJeu.TAILLE);
        System.out.println("Cases affichées : " + casesX + " sur " + casesY);
        AffichageJeu.DISTANCE_VUE = new int[]{casesX, casesY};
    }
}

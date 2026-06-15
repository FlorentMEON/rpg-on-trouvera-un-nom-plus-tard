package com;

import com.Moteur.MoteurJeu;
import com.labyrinthe.Chargement;
import com.Moteur.Jeu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Jeu jeu = Chargement.chargerJeu("/map/laby.txt");
        new MoteurJeu(jeu).lancerJeu(stage, 500, 500);
    }
}
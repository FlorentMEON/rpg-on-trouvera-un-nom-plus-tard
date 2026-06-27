package com;

import com.Moteur.AffichageJeu;
import com.Moteur.MoteurJeu;
import com.guis.MenuParam;
import com.labyrinthe.Chargement;
import com.Moteur.Jeu;
import com.labyrinthe.LabyLoader;
import com.labyrinthe.Labyrinthe;
import com.utils.ImageCache;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Labyrinthe> labyrinthes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        // Setup du jeu

        // Affichage du menu
        StackPane root = new StackPane();
        ImageView background = new ImageView(ImageCache.getImage("/images/mur.png"));
        background.setFitWidth(500);
        background.setPreserveRatio(true);

        // Bouton PLAY
        Button play = new Button("PLAY");
        play.setPrefSize(300, 50);
        play.setStyle("-fx-background-color: rgba(200, 200, 200, 0.9);" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: black;" +
                "-fx-border-radius: 5;" +
                "-fx-border-width: 2px;" +
                "-fx-font-size: 40;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;");
        play.setOnAction(e -> {
            try {
                jeu(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Bouton PARAMÈTRE
        Button options = new Button("OPTIONS");
        options.setPrefSize(300, 50);
        options.setStyle(
                "-fx-background-color: rgba(200, 200, 200, 0.9);" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: black;" +
                "-fx-border-radius: 5;" +
                "-fx-border-width: 2px;" +
                "-fx-font-size: 40;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;"
        );
        options.setOnAction(e -> {
            try {
                options(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox menu = new VBox(10, play, options);
        menu.setPrefSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        menu.setAlignment(Pos.CENTER);

        root.getChildren().addAll(background, menu);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void jeu(Stage stage) throws IOException {
        Jeu jeu = Chargement.chargerJeu("/map/laby.txt");
        new MoteurJeu(jeu).lancerJeu(stage);
    }

    public void options(Stage stage) throws IOException {
        Pane root = new MenuParam().getDisplay();
        stage.setScene(new Scene(root));
        stage.setTitle("Jeu");
        stage.show();
    }
}
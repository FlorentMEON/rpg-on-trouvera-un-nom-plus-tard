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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Labyrinthe> labyrinthes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        // Setup du jeu
        labyrinthes.addAll(LabyLoader.loadLabys());

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
                "-fx-font-size: 30;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;");
        play.setOnMouseClicked(e -> {
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
                "-fx-font-size: 30;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;"
        );
        options.setOnMouseClicked(e -> {
            try {
                options(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Bouton MAP EDITOR
        Button mapEditor = new Button("MAP EDITOR");
        mapEditor.setPrefSize(300, 50);
        mapEditor.setStyle(
                "-fx-background-color: rgba(200, 200, 200, 0.9);" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-width: 2px;" +
                        "-fx-font-size: 30;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: black;"
        );
        mapEditor.setOnMouseClicked(e -> {
            new MapEditor().displayMapEditor(stage);
        });

        VBox menu = new VBox(10, play, options, mapEditor);
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

    public class MapEditor{
        private Pane affichageMap = new Pane();

        public void displayMap(Labyrinthe labyBase, int taille){
            affichageMap.getChildren().clear();
            for (int x = 0; x < labyBase.getWidth(); x++){
                for (int y = 0; y < labyBase.getHeight(); y++){
                    StackPane pane = new StackPane(labyBase.getCase(x, y).getEditor(taille));
                    pane.setOnMouseEntered(event -> {
                        pane.setStyle("-fx-border-color: gold;" +
                                "-fx-border-width: 2px");
                        pane.setViewOrder(-1);
                    });
                    pane.setOnMouseExited(event -> {
                        pane.setStyle("");
                        pane.setViewOrder(0);
                    });

                    pane.setLayoutX((x * taille));
                    pane.setLayoutY((y * taille));

                    affichageMap.getChildren().add(pane);
                }
            }
        }

        public void displayMapEditor(Stage stage){
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: rgb(80, 89, 107);");
            int[] tailleCase = {20};

            // AFFICHAGE DE LA MAP
            Labyrinthe labyBase = labyrinthes.get(0);
            displayMap(labyBase, tailleCase[0]);
            root.setCenter(new ScrollPane(affichageMap));


            // BOUTON ZOOM
            Slider zoomSlider = new Slider(10, 100, 20);
            zoomSlider.setPadding(new Insets(5, 0, 5, 0));
            zoomSlider.valueProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
                tailleCase[0] = nouvelleValeur.intValue();
                // On efface et on redessine la carte avec la nouvelle taille !
                displayMap(labyBase, tailleCase[0]);
            });
            zoomSlider.setMaxWidth(200);
            HBox zoom = new HBox(zoomSlider);
            zoom.setAlignment(Pos.CENTER_RIGHT);
            root.setBottom(zoom);

            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/map_editor.css")));
            stage.setScene(scene);
            stage.setTitle("MapEditor");
            stage.show();
        }
    }
}
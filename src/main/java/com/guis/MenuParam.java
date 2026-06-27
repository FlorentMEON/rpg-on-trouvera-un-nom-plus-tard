package com.guis;

import com.Moteur.AffichageJeu;
import com.Moteur.MoteurJeu;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class MenuParam extends GUI{

    public MenuParam(MoteurJeu moteur) {
        super(moteur);
    }

    public MenuParam(){}

    @Override
    public Pane getDisplay() {
        BorderPane root = new BorderPane();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");

        String style = "-fx-font-size: " + (10*AffichageJeu.TAILLE/AffichageJeu.DISTANCE_VUE[0]) + ";";

        // Parametre Graphique
        Label titreGraphique = new Label("Graphique :");
        titreGraphique.setStyle(style);
        Label resolution = new Label("Resolution : " + AffichageJeu.RESOLUTION[0] + ":" + AffichageJeu.RESOLUTION[1]);
        resolution.setStyle(style);
        Label format = new Label("Format de l'écran : " + AffichageJeu.DISTANCE_VUE[0] + ":" + AffichageJeu.DISTANCE_VUE[1]);
        format.setStyle(style);

        Button exit = new Button("Exit");
        exit.setStyle(style);
        exit.setOnMouseClicked(event -> this.isDisplayed = false);

        VBox reglage = new VBox(10, titreGraphique, resolution, format);
        //reglage.setStyle("-fx-background-color: rgba(255, 0, 0, 0.9);");
        reglage.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(reglage);
        scrollPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        scrollPane.setStyle("-fx-background-color: transparent;" +
                "-fx-background: transparent;");
        root.setCenter(scrollPane);
        root.setBottom(exit);

        this.affichage = new StackPane(region, root);
        this.affichage.setVisible(false);
        this.affichage.setMouseTransparent(true);

        return this.affichage;
    }
}

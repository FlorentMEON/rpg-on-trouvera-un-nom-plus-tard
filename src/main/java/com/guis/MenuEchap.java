package com.guis;

import com.Moteur.AffichageJeu;
import com.Moteur.MoteurJeu;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuEchap extends GUI {
    private Stage stage;

    public MenuEchap(MoteurJeu moteur) {
        super(moteur);
        this.stage = moteur.stage;
    }

    @Override
    public Pane getDisplay() {
        StackPane root = new StackPane();
        Region region = new Region();
        region.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

        String style =
                "-fx-background-color: rgba(200, 200, 200, 0.9);" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-color: black;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-width: 2px;" +
                        "-fx-font-size: " + (10*AffichageJeu.TAILLE/AffichageJeu.DISTANCE_VUE[0]) + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: black;"
                ;

        // Bouton RESUME
        Label resume = new Label("RESUME");
        resume.setPrefSize((double) AffichageJeu.RESOLUTION[0]/4 , (double) AffichageJeu.RESOLUTION[1] /10);
        resume.setStyle(style);
        resume.setAlignment(Pos.CENTER);
        resume.setCursor(Cursor.HAND);
        resume.setOnMouseClicked(event -> {
            this.isDisplayed = false;
        });

        // Bouton PARAMÈTRE
        Label param = new Label("SETTINGS");
        param.setPrefSize((double) AffichageJeu.RESOLUTION[0]/4 , (double) AffichageJeu.RESOLUTION[1] /10);
        param.setStyle(style);
        param.setAlignment(Pos.CENTER);
        param.setCursor(Cursor.HAND);
        param.setOnMouseClicked(event -> {
            moteurJeu.getAffichageJeu().getListMenu().get(1).isDisplayed = true;
            this.isDisplayed = false;
        });


        // Bouton EXIT
        Button close = new Button("EXIT");
        close.setPrefSize((double) AffichageJeu.RESOLUTION[0]/4 , (double) AffichageJeu.RESOLUTION[1] /10);
        close.setStyle(style);
        close.setAlignment(Pos.CENTER);
        close.setCursor(Cursor.HAND);
        close.setOnMouseClicked(event -> {
            stage.close();
        });

        VBox listBouton = new VBox(20, resume, param, close);
        listBouton.setPrefSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        listBouton.setAlignment(Pos.CENTER);
        root.getChildren().addAll(region, listBouton);

        this.affichage = root;

        this.affichage.setVisible(false);
        this.affichage.setMouseTransparent(true);

        return affichage;
    }
}

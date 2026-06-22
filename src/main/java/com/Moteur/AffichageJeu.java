package com.Moteur;

import com.guis.GUI;
import com.guis.MenuEchap;
import com.guis.MenuParam;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AffichageJeu {
    public static int TAILLE = 160;
    public static int[] DISTANCE_VUE = {16, 9};
    public static int[] RESOLUTION = {1920, 1080};
    private Jeu jeu;
    private List<GUI> listMenu = new ArrayList<>();
    private MoteurJeu moteurJeu;

    public AffichageJeu(MoteurJeu moteurJeu, Jeu jeu) {
        this.jeu = jeu;
        this.moteurJeu = moteurJeu;
        this.listMenu = List.of(new MenuEchap(moteurJeu), new MenuParam(moteurJeu));
    }

    public StackPane getAffichageJeu() {
        StackPane root = new StackPane(jeu.getDisplay());
        listMenu.forEach(menu -> root.getChildren().add(menu.getDisplay()));
        return root;
    }

    public void updateDisplay(Scene scene) {
        StackPane root = (StackPane) scene.getRoot();
        if (!root.getChildren().isEmpty()) {
            root.getChildren().set(0, jeu.getDisplay());
        }
        listMenu.forEach(GUI::updateDisplay);
    }

    public List<GUI> getListMenu() {
        return listMenu;
    }
}

package com.guis;

import com.moteur.MoteurJeu;
import javafx.scene.layout.Pane;

public abstract class GUI {
    public boolean isDisplayed = false;
    public Pane affichage;
    public MoteurJeu moteurJeu;

    public GUI(MoteurJeu moteur) {
        this.moteurJeu = moteur;
    }

    public GUI(){}

    public abstract Pane getDisplay();

    public void updateDisplay() {
        if (this.isDisplayed) {
            this.affichage.setVisible(true);
            this.affichage.setMouseTransparent(false);
        }else  {
            this.affichage.setVisible(false);
            this.affichage.setMouseTransparent(true);
        }
    }
}

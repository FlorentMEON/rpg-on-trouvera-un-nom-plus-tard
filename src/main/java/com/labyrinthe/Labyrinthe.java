package com.labyrinthe;

import com.labyrinthe.cases.Vide;
import com.utils.ImageCache;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Labyrinthe {

    private Cases[][] laby;

    public Labyrinthe() {
        laby = new Cases[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                laby[i][j] = new Vide();
            }
        }
    }

    public Labyrinthe(int x, int y) {
        laby = new Cases[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                laby[i][j] = new Vide();
            }
        }
    }

    public Cases getCase(int x, int y) {
        if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight())
            return null;
        else return laby[x][y];
    }

    public void setCase(int x, int y, Cases element) {
        laby[x][y] = element;
    }

    public Cases[][] getListCases(){
        return this.laby;
    }

    public void setCase(Cases avant, Cases après){
        for (int i = 0; i < laby.length; i++) {
            for (int j = 0; j < laby[i].length; j++) {
                if (laby[i][j] == avant) {
                    laby[i][j] = après;
                }
            }
        }
    }

    public int getWidth() {
        return laby.length;
    }
    public int getHeight() {
        return laby[0].length;
    }
    public Pane getDisplay(){
        Pane res = new Pane();
        for (int x = 0; x < getWidth(); x++){
            for (int y = 0; y < getHeight(); y++){
                ImageView pane = getCase(x, y).getDisplayEditor(5);

                pane.setLayoutX((x * 5));
                pane.setLayoutY((y * 5));

                res.getChildren().add(pane);
            }
        }
        return res;
    }

    public void setCases(Cases[][] laby){
        this.laby = laby;
    }
    public Cases[][] getCases(){
        return this.laby;
    }
}

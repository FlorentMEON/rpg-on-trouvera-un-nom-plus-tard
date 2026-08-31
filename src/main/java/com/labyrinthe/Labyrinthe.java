package com.labyrinthe;

import com.labyrinthe.cases.Vide;
import com.labyrinthe.cases.sol.Grass;
import com.utils.ImageCache;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Labyrinthe {

    private Cases[][][] laby;

    public Labyrinthe() {
        laby = new Cases[10][10][2];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                laby[i][j][0] = new Vide();
                laby[i][j][1] = null;
            }
        }
    }

    public Labyrinthe(Labyrinthe labyrinthe) {
        laby = labyrinthe.laby;
    }

    public Labyrinthe(int x, int y) {
        laby = new Cases[x][y][2];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                laby[i][j][0] = new Grass();
                laby[i][j][1] = new Vide();
            }
        }
    }

    public Labyrinthe clone() {
        Labyrinthe copie = new Labyrinthe(this.getWidth(), this.getHeight());
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                copie.setCase(x, y, 0, this.getCase(x, y, 0));
                copie.setCase(x, y, 0, this.getCase(x, y, 0));
            }
        }

        return copie;
    }

    public Cases getCase(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight())
            return null;
        else return laby[x][y][z];
    }

    public void setCase(int x, int y, int z, Cases element) {
        laby[x][y][z] = element;
    }

    public Cases[][][] getListCases(){
        return this.laby;
    }

    public void setCase(Cases avant, Cases après){
        for (int i = 0; i < laby.length; i++) {
            for (int j = 0; j < laby[i].length; j++) {
                for (int k = 0; k < laby[i][j].length; k++) {
                    if (laby[i][j][k] == avant) {
                        laby[i][j][k] = après;
                    }
                }
            }
        }
    }

    public void extenceLaby(Pos pos){
        Labyrinthe res;
        switch (pos){
            case TOP_CENTER ->  {
                res = new Labyrinthe(this.getWidth(), this.getHeight()+1);
                for (int x = 0; x < this.getWidth(); x++) {
                    for (int y = 1; y < this.getHeight()+1; y++) {
                        res.setCase(x, y, 0, laby[x][y-1][0]);
                        res.setCase(x, y, 1, laby[x][y-1][1]);
                    }
                }
                this.laby = res.laby;
            }
            case BOTTOM_CENTER ->  {
                res = new Labyrinthe(this.getWidth(), this.getHeight()+1);
                for (int x = 0; x < this.getWidth(); x++) {
                    for (int y = 0; y < this.getHeight(); y++) {
                        res.setCase(x, y, 0, laby[x][y][0]);
                        res.setCase(x, y, 1, laby[x][y][1]);
                    }
                }
                this.laby = res.laby;
            }
            case CENTER_LEFT ->   {
                res = new Labyrinthe(this.getWidth()+1, this.getHeight());
                for (int x = 1; x < this.getWidth()+1; x++) {
                    for (int y = 0; y < this.getHeight(); y++) {
                        res.setCase(x, y, 0, laby[x-1][y][0]);
                        res.setCase(x, y, 1, laby[x-1][y][1]);
                    }
                }
                this.laby = res.laby;
            }
            case CENTER_RIGHT ->  {
                res = new Labyrinthe(this.getWidth()+1, this.getHeight());
                for (int x = 0; x < this.getWidth(); x++) {
                    for (int y = 0; y < this.getHeight(); y++) {
                        res.setCase(x, y, 0, laby[x][y][0]);
                        res.setCase(x, y, 1, laby[x][y][1]);
                    }
                }
                this.laby = res.laby;
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
                ImageView pane = getCase(x, y, 0).getDisplayEditor(5);
                ImageView pane2 = getCase(x, y, 1).getDisplayEditor(5);

                pane.setLayoutX((x * 5));
                pane.setLayoutY((y * 5));

                res.getChildren().addAll(pane, pane2);
            }
        }
        return res;
    }

    public void setCases(Cases[][][] laby){
        this.laby = laby;
    }
    public Cases[][][] getCases(){
        return this.laby;
    }
}

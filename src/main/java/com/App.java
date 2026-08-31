package com;

import com.moteur.MoteurJeu;
import com.entities.Player;
import com.guis.MenuParam;
import com.labyrinthe.Cases;
import com.moteur.Jeu;
import com.labyrinthe.LabyLoader;
import com.labyrinthe.Labyrinthe;
import com.labyrinthe.cases.Gate;
import com.labyrinthe.cases.Sol;
import com.utils.ImageCache;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App extends Application {
    private List<Labyrinthe> labyrinthes = new ArrayList<>();
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Setup du jeu
        labyrinthes.addAll(LabyLoader.loadLabys());
        this.primaryStage = stage;
        hub(stage);
    }

    public void hub(Stage stage)
    {
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
        Jeu jeu = new Jeu(LabyLoader.loadLabys().getFirst(), new Player(5, 5));
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
        private Cases casesSelected = null;
        private Set<Class<? extends Cases>> listCases;
        private int taille;
        private Labyrinthe labyBase;
        private List<Labyrinthe> editsLaby;
        private int idBackup;
        private int[] selection;
        private int z = 0;

        private MapEditor()
        {
            Reflections reflections = new Reflections("com.labyrinthe");
            this.listCases = reflections.getSubTypesOf(Cases.class);
            this.taille = 20;
            this.labyBase = labyrinthes.get(0);
            this.editsLaby = new ArrayList<>();
            this.editsLaby.add(labyBase.clone());
            this.idBackup = 0;
            this.selection = new int[]{-1, -1, -1, -1};
        }

        public void addLabyPile(Labyrinthe laby)
        {
            while (editsLaby.size() > idBackup + 1) {
                editsLaby.remove(editsLaby.size() - 1);
            }

            // 2. On ajoute la nouvelle sauvegarde (le paramètre "laby") à la fin de la liste
            this.editsLaby.add(laby);

            // 3. Limite de taille :
            // Si on dépasse les 5 sauvegardes autorisées, on supprime la plus vieille (index 0)
            if (this.editsLaby.size() > 5) {
                this.editsLaby.remove(0);
            }

            // 4. On met à jour l'index de backup pour qu'il pointe sur la toute dernière sauvegarde
            this.idBackup = this.editsLaby.size() - 1;

            // Debug console
            System.out.println("Taille historique : " + editsLaby.size() + " | Position : " + idBackup);
        }

        public void displayMap()
        {
            affichageMap.getChildren().clear();
            for (int x = 0; x < labyBase.getWidth(); x++){
                for (int y = 0; y < labyBase.getHeight(); y++){
                    int finalX = x;
                    int finalY = y;

                    StackPane pane = new StackPane(labyBase.getCase(x, y, z).getDisplayEditor(taille));
                    pane.setOnMouseEntered(event -> {
                        pane.setStyle("-fx-border-color: gold;" +
                                "-fx-border-width: 2px");
                        pane.setViewOrder(-1);
                    });
                    pane.setOnMouseExited(event -> {
                        pane.setStyle("");
                        pane.setViewOrder(0);
                    });
                    pane.setOnMouseClicked(event -> {
                        casesSelected = labyBase.getCase(finalX, finalY, z);
                        try {
                            displayListCases(finalX, finalY);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });


                    pane.setLayoutX((x * taille));
                    pane.setLayoutY((y * taille));

                    affichageMap.getChildren().add(pane);
                }
            }
        }

        public void displayListCases(int x, int y) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
        {
            Stage stageCases = new Stage();
            stageCases.initOwner(primaryStage);

            // AFFICHAGE DES CASES
            TilePane tilePaneCases = new TilePane(20, 20);
            int taille = 100;
            for (Class<? extends Cases> clazz : listCases) {
                Cases cases = clazz.getDeclaredConstructor().newInstance();
                if ((z == 0 && cases instanceof Sol) || (z == 1 && !(cases instanceof Sol))) {
                    StackPane pane = new StackPane(cases.getDisplayEditor(taille));
                    pane.setOnMouseClicked(event -> {
                        changeCase(cases, x, y);
                        stageCases.close();
                    });

                    tilePaneCases.getChildren().add(pane);
                }
            }

            ScrollPane scrollPaneCases = new ScrollPane(tilePaneCases);
            scrollPaneCases.setFitToWidth(true);
            stageCases.setScene(new Scene(scrollPaneCases, 500, 500));
            stageCases.show();
        }

        public void changeCase(Cases casesSelected, int x, int y)
        {
            VBox root = new VBox(10);
            root.setPadding(new Insets(10, 10, 10, 10));
            root.setAlignment(Pos.CENTER);
            Stage stageCases = new Stage();
            stageCases.initOwner(primaryStage);
            Button buttonCases = new Button("REPLACE");
            Cases newCase;
            if (casesSelected instanceof Gate)
            {
                TextField linkText = new TextField();
                newCase = new Gate();
                root.getChildren().addAll(casesSelected.getDisplayEditor(100), linkText);
                Runnable remplacement = () -> {
                    ((Gate)newCase).setLink(linkText.getText());

                    labyBase.setCase(x, y, z, newCase);
                    addLabyPile(labyBase.clone());
                    System.out.println(newCase);
                    displayMap();
                    stageCases.close();
                };
                buttonCases.setOnMouseClicked(event -> remplacement.run());
                linkText.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) remplacement.run();
                });
            }
            else
            {
                labyBase.setCase(x, y, z, casesSelected);
                addLabyPile(labyBase.clone());
                displayMap();
                return;
            }

            root.getChildren().add(buttonCases);
            stageCases.setScene(new Scene(root));
            stageCases.show();
        }

        public void displayMapEditor(Stage stage)
        {
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: rgb(80, 89, 107);");
            int[] tailleCase = {20};

            // 🖼️ - AFFICHAGE DE LA MAP
            displayMap();
            ScrollPane map = new ScrollPane(affichageMap);
            root.setCenter(map);

            // 🔎 - BOUTON ZOOM
            Slider zoomSlider = new Slider(1, 100, 20);
            zoomSlider.setPadding(new Insets(5, 0, 5, 0));
            zoomSlider.valueProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
                taille = nouvelleValeur.intValue();
                // On efface et on redessine la carte avec la nouvelle taille !
                displayMap();
            });
            zoomSlider.setMaxWidth(200);
            HBox zoom = new HBox(zoomSlider);
            zoom.setAlignment(Pos.CENTER_RIGHT);
            root.setBottom(zoom);

            // 🛠️ - BARRE D'OUTILS
            HBox barOutils = new HBox(10);
            barOutils.setAlignment(Pos.CENTER_LEFT);
            barOutils.setPadding(new Insets(5, 5, 5, 5));
            HBox.setHgrow(barOutils, Priority.ALWAYS);
            //      BOUTON SAVE
            Button saveButton = new Button("SAVE");
            saveButton.setOnMouseClicked(event -> {
                LabyLoader.saveLaby(labyrinthes);
            });
            //      SÉLECTEUR DE MAP
            Button mapSelectorButton = new Button("CHANGE MAP");
            mapSelectorButton.setOnMouseClicked(event -> {
                Stage stageSelectMap = new Stage();
                stageSelectMap.initOwner(primaryStage);

                VBox tilepane = new VBox(20);
                int taille = 100;
                for (Labyrinthe laby : labyrinthes) {
                    StackPane stackPane = new StackPane(laby.getDisplay());
                    stackPane.setOnMouseClicked(e -> {
                        labyBase = laby;
                        displayMap();
                        stageSelectMap.close();
                    });

                    tilepane.getChildren().add(stackPane);
                }
                ScrollPane scrollPane = new ScrollPane(tilepane);
                scrollPane.setFitToWidth(true);
                stageSelectMap.setScene(new Scene(scrollPane, 500, 500));
                stageSelectMap.show();
            });
            //      NOUVELLE MAP
            Button newMapButton = new Button("NEW MAP");
            newMapButton.setOnMouseClicked(event -> {
                Stage stageNewMap = new Stage();
                stageNewMap.initOwner(primaryStage);

                VBox vbox = new VBox(10);
                vbox.setAlignment(Pos.CENTER);


                Label labelX = new Label("Taille des x:");
                TextField textFieldX = new TextField();
                HBox hboxX = new HBox(10, labelX, textFieldX);

                Label labelY = new Label("Taille des y:");
                TextField textFieldY = new TextField();
                HBox hboxY = new HBox(10, labelY, textFieldY);

                Button createNewMap = new Button("CREATE");
                createNewMap.setOnMouseClicked(e -> {
                    Labyrinthe newLaby = new Labyrinthe(Integer.parseInt(textFieldX.getText()), Integer.parseInt(textFieldY.getText()));
                    labyrinthes.add(newLaby);
                    labyBase = newLaby;
                    displayMap();
                    stageNewMap.close();
                });
                vbox.getChildren().addAll(hboxX, hboxY, createNewMap);
                stageNewMap.setScene(new Scene(vbox));
                stageNewMap.show();
            });
            //      EXTEND LA MAP
            ImageView iconExtendButton = new ImageView(ImageCache.getImage("/images/editeur/icons/extend_map_icon.png"));
            iconExtendButton.setFitWidth(20);
            iconExtendButton.setPreserveRatio(true);
            Button extendButton = new Button("", iconExtendButton);
            extendButton.setOnMouseClicked(event -> {
                Stage stageExtendMap = new Stage();
                stageExtendMap.initOwner(primaryStage);

                BorderPane paneExtendMap = new BorderPane();
                paneExtendMap.setStyle("-fx-background-color: rgb(80, 89, 107)");
                Pane mapPreview = labyBase.getDisplay();
                mapPreview.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                BorderPane.setAlignment(mapPreview, Pos.CENTER);
                paneExtendMap.setCenter(mapPreview);

                Scene scene = new Scene(paneExtendMap, labyBase.getWidth()*10, labyBase.getHeight()*10);

                // TOP
                Button topButton = new Button("+");
                topButton.setPrefWidth(150);
                BorderPane.setAlignment(topButton, Pos.CENTER);
                paneExtendMap.setTop(topButton);
                topButton.setOnMouseClicked(e -> {
                    labyBase.extenceLaby(Pos.TOP_CENTER);
                    mapPreview.getChildren().clear();
                    mapPreview.getChildren().add(labyBase.getDisplay());
                    addLabyPile(labyBase.clone());
                    displayMap();
                });
                // BOTTOM
                Button bottomButton = new Button("+");
                bottomButton.setPrefWidth(150);
                BorderPane.setAlignment(bottomButton, Pos.CENTER);
                paneExtendMap.setBottom(bottomButton);
                bottomButton.setOnMouseClicked(e -> {
                    labyBase.extenceLaby(Pos.BOTTOM_CENTER);
                    mapPreview.getChildren().clear();
                    mapPreview.getChildren().add(labyBase.getDisplay());
                    addLabyPile(labyBase.clone());
                    displayMap();
                });
                // LEFT
                Button leftButton = new Button("+");
                leftButton.setPrefHeight(150);
                BorderPane.setAlignment(leftButton, Pos.CENTER);
                paneExtendMap.setLeft(leftButton);
                leftButton.setOnMouseClicked(e -> {
                    labyBase.extenceLaby(Pos.CENTER_LEFT);
                    mapPreview.getChildren().clear();
                    mapPreview.getChildren().add(labyBase.getDisplay());
                    addLabyPile(labyBase.clone());
                    displayMap();
                });
                // RIGHT
                Button rightButton = new Button("+");
                rightButton.setPrefHeight(150);
                BorderPane.setAlignment(rightButton, Pos.CENTER);
                paneExtendMap.setRight(rightButton);
                rightButton.setOnMouseClicked(e -> {
                    labyBase.extenceLaby(Pos.CENTER_RIGHT);
                    mapPreview.getChildren().clear();
                    mapPreview.getChildren().add(labyBase.getDisplay());
                    addLabyPile(labyBase.clone());
                    displayMap();
                });

                stageExtendMap.setScene(scene);
                stageExtendMap.show();
            });
            //      BOUTON UNDO
            Button undoButton = new Button("↩");
            Runnable undo = () -> {
                if (idBackup > 0){
                    idBackup--;
                    labyBase = editsLaby.get(idBackup).clone();
                    displayMap();
                }
            };
            Button swapZButton = new Button("SWAP STREAK");
            swapZButton.setOnMouseClicked(e -> {
                if (z == 0) z = 1;
                else z = 0;
                displayMap();
            });
            //      BOUTON MENU GENERAL
            ImageView iconHub = new ImageView(ImageCache.getImage("/images/editeur/icons/hub_icon.png"));
            iconHub.setFitWidth(20);
            iconHub.setPreserveRatio(true);
            Button hubButton = new Button("", iconHub);
            hubButton.setOnMouseClicked(e -> undo.run());
            hubButton.setOnMouseClicked(e -> {
                hub(stage);
            });
            barOutils.getChildren().addAll(hubButton, mapSelectorButton, newMapButton, saveButton, extendButton, swapZButton, undoButton);
            root.setTop(barOutils);

            // RACCOURCIE CLAVIER
            //      ZOOM sur Ctrl+molette
            root.addEventFilter(ScrollEvent.SCROLL, event -> {
                if (event.isControlDown()){
                    if (event.getDeltaY() > 0){
                        zoomSlider.valueProperty().set(zoomSlider.valueProperty().get() - 5);
                    }else if (event.getDeltaY() < 0){
                        zoomSlider.valueProperty().set(zoomSlider.valueProperty().get() + 5);
                    }
                    event.consume();
                }
            });
            root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                // CTRL+Z -> retour arrière/undo
                if (event.getCode() == KeyCode.Z && event.isControlDown()) {
                    undo.run();
                }
                // CTRL+S -> sauvegarde de la map actuel
                if (event.getCode() == KeyCode.S && event.isControlDown()) {
                    LabyLoader.saveLaby(labyrinthes);
                }
            });

            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/map_editor.css")));
            stage.setScene(scene);
            stage.setTitle("MapEditor");
            stage.show();
        }
    }
}
package com.moteur;

import com.entities.Entity;
import com.entities.Player;
import com.labyrinthe.Cases;
import com.labyrinthe.Labyrinthe;
import com.labyrinthe.cases.Vide;
import com.props.Prop;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    public Labyrinthe laby;
    public Controler controler;
    public Player player;
    public List<Entity> entities;
    public List<Prop> props;

    public Jeu(Labyrinthe laby, Player player)
    {
        this.laby = laby;
        this.player = player;
        this.controler = new Controler();
        this.entities = new ArrayList<>();
        this.props = new ArrayList<>();
    }

    public Jeu(Player personnage, Labyrinthe laby, List<Entity> entities, List<Prop> props)
    {
        this.controler = new Controler();
        this.player = personnage;
        this.laby = laby;
        this.entities = entities;
        this.props = props;
    }

    public Jeu(Labyrinthe laby)
    {
        this.laby = laby;
        this.player = new Player(0, 0);
        this.controler = new Controler();
        this.entities = new ArrayList<>();
        this.props = new ArrayList<>();
    }

    /**
     * Test s'il y a une collision avec une certaine hitbox.
     * @param futureHitbox
     * @param currentEntity
     * @return
     */
    public boolean checkCollision(Rectangle2D futureHitbox, Entity currentEntity)
    {
        System.out.println("Entity : " + checkEntityCollision(futureHitbox, currentEntity));
        System.out.println("Prop : " + checkPropCollision(futureHitbox));
        System.out.println("Terrain : " + checkTerrainCollision(futureHitbox));
        return checkEntityCollision(futureHitbox, currentEntity) ||
                checkPropCollision(futureHitbox) ||
                checkTerrainCollision(futureHitbox);
    }

    /**
     * Test s'il y a une entité à la position donnée.
     *
     * @param futureHitbox
     * @param currentEntity
     * @return
     */
    public boolean checkEntityCollision(Rectangle2D futureHitbox, Entity currentEntity)
    {
        for (Entity e : entities) {
            if (e != currentEntity && e.getHitbox().intersects(futureHitbox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test si la hitbox entre en collision avec un prop.
     *
     * @param futureHitbox
     * @return
     */
    public boolean checkPropCollision(Rectangle2D futureHitbox)
    {
        for (Prop p : props) {
            if (p.getHitbox().intersects(futureHitbox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test si la hitbox entre en contact avec une case non traversable
     *
     * @param hitbox
     * @return
     */
    public boolean checkTerrainCollision(Rectangle2D hitbox) {
        // On arrondit les coins de la hitbox pour savoir quelles cases du tableau vérifier
        int startX = (int) Math.floor(hitbox.getMinX());
        int endX = (int) Math.floor(hitbox.getMaxX());
        int startY = (int) Math.floor(hitbox.getMinY());
        int endY = (int) Math.floor(hitbox.getMaxY());

        // On check toutes les cases recouvertes par la hitbox
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x < 0 || x >= laby.getWidth() || y < 0 || y >= laby.getHeight()) {
                    return true;
                }

                Cases caseObjet = laby.getCase(x, y, 1);
                if (caseObjet != null && !caseObjet.collision()) {
                    // Il y a un mur
                    return true;
                }
            }
        }
        // Il n'y a pas de mur
        return false;
    }

    public Node getDisplay()
    {
        // Le Group est le conteneur principal pour la 3D (pas de StackPane ici !)
        Group root3D = new Group();

        // --- 1. CRÉATION DE LA CAMÉRA 3D ---
        PerspectiveCamera camera = new PerspectiveCamera(true);

        camera.setNearClip(0.1);
        camera.setFarClip(20000.0);

        // On accroche la caméra dans le ciel et on la recule
        camera.setTranslateY(-750);
        camera.setTranslateZ(-1000);

        // On l'incline de 45 degrés vers le bas pour regarder le plateau
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(-35);

        // Suivi du joueur
        double playerX = player.x * AffichageJeu.TAILLE;
        double playerZ = -player.y * AffichageJeu.TAILLE; // L'ancien Y devient la profondeur Z
        camera.setTranslateX(playerX + (double) AffichageJeu.TAILLE / 2);
        camera.setTranslateZ(playerZ - 1100); // On ajuste le recul par rapport au joueur

        // --- 2. CONSTRUCTION DU MONDE ---
        // Pour simplifier ce premier test 3D, on dessine tout le labyrinthe d'un coup
        for (int x = 0; x < laby.getWidth(); x++) {
            for (int y = 0; y < laby.getHeight(); y++) {

                // COUCHE 0 : LE SOL (L'Herbe, l'Eau...)
                Cases caseSol = laby.getCase(x, y, 0);
                if (caseSol != null) {
                    Node paneSol = caseSol.getDisplay();

                    // On place sur la grille (X et Z)
                    paneSol.setTranslateX(x * AffichageJeu.TAILLE);
                    paneSol.setTranslateZ(-y * AffichageJeu.TAILLE);
                    paneSol.setTranslateY(0); // Au niveau du sol

                    // LA MAGIE : On couche le sol à plat !
                    paneSol.setRotationAxis(Rotate.X_AXIS);
                    paneSol.setRotate(-90);

                    root3D.getChildren().add(paneSol);
                }

                // COUCHE 1 : LES OBJETS (Les Murs, Maisons...)
                Cases caseObjet = laby.getCase(x, y, 1);
                // On s'assure qu'on ne dessine pas les entités vides pour économiser de la RAM
                if (caseObjet != null && !(caseObjet instanceof Vide)) {
                    Node paneObjet = caseObjet.getDisplay();

                    paneObjet.setTranslateX(x * AffichageJeu.TAILLE);
                    paneObjet.setTranslateZ(-y * AffichageJeu.TAILLE);

                    // On remonte l'image pour que le bas touche le sol (Y vers le haut = négatif)
                    paneObjet.setTranslateY(-AffichageJeu.TAILLE);

                    // Aucune rotation ! L'image reste parfaitement droite face à la caméra.
                    root3D.getChildren().add(paneObjet);
                }
            }
        }

        for (Entity entity : entities) {
            Pane paneEntity = entity.getDisplay();
            paneEntity.setTranslateX(entity.x * AffichageJeu.TAILLE);
            paneEntity.setTranslateZ(-entity.y * AffichageJeu.TAILLE);
            paneEntity.setTranslateY(-AffichageJeu.TAILLE);
            root3D.getChildren().add(paneEntity);
        }

        Pane panePlayer = player.getDisplay();
        panePlayer.setTranslateX(player.x * AffichageJeu.TAILLE);
        panePlayer.setTranslateZ(-player.y * AffichageJeu.TAILLE);
        panePlayer.setTranslateY(-AffichageJeu.TAILLE);
        root3D.getChildren().add(panePlayer);

        // --- 4. LA SUBSCENE (Le monde encapsulé) ---
        // Le "true" active le Z-Buffer (Vital : c'est ce qui calcule ce qui est devant/derrière automatiquement !)
        SubScene subScene = new SubScene(root3D, AffichageJeu.RESOLUTION[0], AffichageJeu.RESOLUTION[1], true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.CYAN);

        return subScene;
    }
}

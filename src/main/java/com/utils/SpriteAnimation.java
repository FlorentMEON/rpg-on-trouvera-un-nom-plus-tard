package com.utils;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Cette class permet de créer des animations à partir d'un sprite pour économiser des performances et surtout,
 * de les simplifier.
 */
public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;      // Nombre total d'images (frames) dans l'animation
    private final int columns;    // Nombre de colonnes sur ta planche de sprite
    private final int offsetX;    // Décalage X de départ (souvent 0)
    private final int offsetY;    // Décalage Y de départ (souvent 0)
    private final int width;      // Largeur d'une seule case (frame)
    private final int height;     // Hauteur d'une seule case (frame)
    private int lastIndex;

    public SpriteAnimation(ImageView imageView, Duration duration, int count, int columns,
                           int offsetX, int offsetY, int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR); // Pour éviter que l'animation n'accélère/décélère
    }

    @Override
    protected void interpolate(double k) {
        // k est une valeur entre 0.0 et 1.0 qui représente l'avancement de l'animation
        final int index = Math.min((int) Math.floor(k * count), count - 1);

        if (index != lastIndex) {
            // On calcule la position X et Y de la "fenêtre" sur la grande image
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;

            // On déplace le Viewport
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}

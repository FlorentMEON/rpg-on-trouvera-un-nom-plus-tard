package com.utils;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Permet de stocker les images une fois charger une fois.
 * Cela permet d'éviter les lags, surtout lorsqu'on charge plusieurs fois la même image.
 * Elle s'utilise comme ceci :
 * ImageView iv = new ImageView(ImageCache.getImage("le path"));
 */
public class ImageCache {

    // Le "coffre-fort" qui va stocker les images déjà chargées
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(String path) {
        // 1. Si on a DÉJÀ chargé cette image avant, on la donne instantanément !
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        // 2. Si c'est la TOUTE PREMIÈRE FOIS, on va la chercher sur le disque dur
        InputStream stream = ImageCache.class.getResourceAsStream(path);
        if (stream != null) {
            Image nouvelleImage = new Image(stream);
            cache.put(path, nouvelleImage); // On la sauvegarde dans le coffre
            return nouvelleImage;
        } else {
            System.err.println("❌ Fichier introuvable : " + path);
            return null;
        }
    }
}
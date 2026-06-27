package com.labyrinthe;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LabyLoader {

    public static List<Labyrinthe> loadLabys() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File is = new File("maps.json");

        if (!is.exists()) {
            System.err.println("ERREUR : Le fichier est introuvable.");
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(is, new TypeReference<List<Labyrinthe>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveLaby(List<Labyrinthe> labyrinthes) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File file = new File("maps.json");

        if (!file.exists()) {
            System.err.println("ERREUR : Le fichier est introuvable.");
            return;
        }

        // Écriture du fichier JSON avec la liste mise à jour
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, labyrinthes);
            System.out.println("Sauvegarde réussie (Mise à jour/Ajout) dans : " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON.");
            e.printStackTrace();
        }
    }
}

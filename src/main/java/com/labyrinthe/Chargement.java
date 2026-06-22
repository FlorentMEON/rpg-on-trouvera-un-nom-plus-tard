package com.labyrinthe;

import com.Moteur.Jeu;
import com.entities.Player;
import com.labyrinthe.cases.Murs;
import com.labyrinthe.cases.Vide;
import com.labyrinthe.cases.Water;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Chargement {

    public static Jeu chargerJeu(String path) throws IOException {
        List<String> file;
        /*List.of(
                "####################",
                "#####  *############",
                "#####2  ############",
                "#####   ############",
                "###      ###########",
                "### # ## ###########",
                "#@  # ## ##### &  ##",
                "#+    1           ##",
                "##### ### # ##   %##",
                "#####% 2  #####>####",
                "####################"
        );
        List<String> file = List.of(
                "####################",
                "#@     #     #     #",
                "#####  #  #  #  #  #",
                "#+  #     #2    #  #",
                "#   #######  ####  #",
                "# &    #  #  #     #",
                "#####  #  #  #  ## #",
                "#1  #  #     #  #  #",
                "#   #  #######  #  #",
                "#*              # >#",
                "####################"
        );
         */
        InputStream is = Chargement.class.getResourceAsStream(path);
        if (is == null) System.out.println("Erreur de chargement : " + path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        file = new ArrayList<>();
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            file.add(tmp);
        }
        reader.close();

        // On initialise les val
        // LABY
        // On initialise les valeurs proprement
        int[] tailleTab = calculerLMax(file);
        int hauteur = tailleTab[0]; // Nombre de lignes
        int largeur = tailleTab[1]; // Nombre de colonnes de la ligne la plus longue

        // IMPORTANT : On crée le tableau en [y][x] -> [ligne][colonne]
        Labyrinthe laby = new  Labyrinthe(largeur, hauteur);
        Jeu jeu = new Jeu(laby);
        Player pj = null;

        // Créer le laby (y = ligne, x = colonne)
        for (int y = 0; y < hauteur; y++) {
            String ligne = file.get(y);

            for (int x = 0; x < ligne.length(); x++) {
                switch (ligne.charAt(x)) {
                    case '#' -> {
                        laby.setCase(x, y, new Murs());
                    }
                    case '%' -> {
                        laby.setCase(x, y, new Water());
                    }
                    case '@' -> {
                        laby.setCase(x, y, new Vide());
                        Player p = new Player(x, y);
                        jeu.player = p;
                    }
                    default -> {
                        laby.setCase(x, y, new Vide());
                    }
                }
            }
        }
        jeu.entities.add(jeu.player);

        return new Jeu(jeu.player, laby, jeu.entities);
    }

    public static int[] calculerLMax(List<String> list){
        int[] res = {list.size(), 0};
        int maxY = 0;
        for(String s: list){
            if (s.length() > maxY) maxY = s.length();
        }
        System.out.println(list.size());
        res[1] = maxY;
        return res;
    }
}

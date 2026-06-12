package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
     * Classe fournie, permet de stocker des valeurs associées au noeud et
     * des parents
     * - un noeud est represente par un String (son nom)
     * - on accede avec des get (getValeur et getParent)
     * - on modifie avec des set (setValeur et setParent)
     */
    public class Valeurs {

        Map<Position, Integer> valeur;
        Map<Position, Position> parent;

        public Valeurs() {
            this.valeur = new TreeMap<>();
            this.parent = new TreeMap<>();
        }

        public void setValeur(Position pos, int valeur) {
            // modifie valeur
            this.valeur.put(pos, valeur);
        }

        public void setParent(Position pos, Position parent) {
            this.parent.put(pos, parent);
        }

        public Position getParent(Position pos) {
            return this.parent.get(pos);
        }

        public Integer getValeur(Position pos) {
            return this.valeur.get(pos);
        }

        public List<Position> calculerChemin(Position destination){
            List<Position> res = new ArrayList<>();
            List<Position> liste = new ArrayList<>();
            liste.add(destination);
            Position pointSuivant = destination;

            // remplissage de la liste
            for (int i=0; i<valeur.size(); i++){
                pointSuivant = getParent(pointSuivant);
                if (pointSuivant == null) break;
                liste.add(pointSuivant);

                if (getValeur(pointSuivant) == 0) break;
            }

            // Inversion de la liste
            for (int i = liste.size()-1; i >= 0;i--) {
                res.add(liste.get(i));
            }

            res.forEach(ev -> System.out.println(ev));
            return res;
        }

        /**
         * retourne une chaine qui affiche le contenu
         * - par noeud stocke
         * - a chaque noeud, affiche la valeur puis le noeud
         *   parent
         *
         * @return descriptif du noeud
         */
        public String toString() {
            String res = "";
            // pour chaque noeud
            for (Position s : this.valeur.keySet()) {
                // ajoute la valeur et le noeud parent
                int valeurNoeud = valeur.get(s);
                Position noeudParent = parent.get(s);
                res += s + " ->  V:" + valeurNoeud + " p:" + noeudParent + "\n";
            }
            return res;

        }

    }

package com.utils;

import com.labyrinthe.Labyrinthe;
import com.labyrinthe.cases.Murs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Graph {
    List<Noeud> noeuds;

    public Graph() {
        noeuds = new ArrayList<>();
    }

    public void load(Labyrinthe laby){
        for(int x = 0; x < laby.getWidth(); x++){
            for(int y = 0; y < laby.getHeight(); y++){
                if (!(laby.getCase(x,y) instanceof Murs)) {
                    Noeud noeud = new Noeud(x, y);
                    for (Arc n : getAdj(laby, new Position(x, y))) {
                        noeud.arcs.add(new Arc(n.pos.x, n.pos.y));
                    }
                    noeuds.add(noeud);
                }
            }
        }
    }

    public Valeurs resoudre(Labyrinthe laby, Position depart){

        // Setup
        Valeurs res = new Valeurs();    // Resultat
        Valeurs keys = new Valeurs();   // Les Différents Noeuds

        // On associe l'infini ah tout, sauf à la valeur de départ
        for (Noeud noeud: noeuds){
            if (noeud.arcs.size() == 0) keys.setParent(noeud.pos, null);
            else keys.setParent(noeud.pos, noeud.arcs.getFirst().pos);
            if (!noeud.pos.equals(depart)) keys.setValeur(noeud.pos, Integer.MAX_VALUE);
            else keys.setValeur(noeud.pos, 0);
        }
        // System.out.println(keys);

        // On boucle jusqu'à ce que le résultat soit le même que le précédent
        while (true){
            // On boucle pour chaque Noeud
            for (Noeud arcs1: this.noeuds){
                // Si le Noeud actuel n'est pas celui de départ
                if (!arcs1.pos.equals(depart)) {

                    // On créer la list des Anté
                    Valeurs valeurs = new Valeurs();
                    // System.out.println(arcs1.getName() + ": {");

                    // On ajoute un Valeurs à 'valeurs'
                    // pour chaque Anté de 'arcs1'
                    // On ajoute un Valeurs à 'valeurs' pour chaque Anté de 'arcs1'
                    for (Arc arc : this.getAdj(laby, arcs1.pos)) {
                        int valeurAntecedente = keys.getValeur(arc.pos);

                        // On ne calcule le +1 QUE si l'antécédent n'est pas "l'infini"
                        if (valeurAntecedente != Integer.MAX_VALUE) {
                            valeurs.setParent(arc.pos, arcs1.pos);
                            valeurs.setValeur(arc.pos, 1 + valeurAntecedente);
                        }
                    }

                    // On accocie la valeur minimal au Noeud actuel
                    keys.setValeur(arcs1.pos, (
                            getMin(valeurs) == null ?
                                    Integer.MAX_VALUE : valeurs.getValeur(getMin(valeurs))
                    ));
                    keys.setParent(arcs1.pos, getMin(valeurs));
                }
            }

            // Affichage pour le débug
            /*
            System.out.println("Avant : \n" + keys);
            System.out.println("Après : \n" + res);
            System.out.println("" + checkEquals(res,keys));
             */

            // Si le res et la valeur d'avant sont = alors on break
            if (checkEquals(res,keys)) {
                res = keys;
                break;
            }else res = copier(keys); // Sinon on copie la nouvelle valeur dans le résultat
        }

        // return
        return res;
    }

    @Override
    public String toString() {
        String result = "";
        for(Noeud n : noeuds){
            result += n.toString() + "\n";
        }
        return result;
    }

    public List<Arc> getAdj(Labyrinthe laby, Position pos){
        List<Arc> result = new ArrayList<>();
        // x+1
        if (laby.getCase(pos.x+1,pos.y) != null && !(laby.getCase(pos.x+1,pos.y) instanceof Murs))
            result.add(new Arc(pos.x+1,pos.y));
        // x-1
        if (laby.getCase(pos.x-1,pos.y) != null && !(laby.getCase(pos.x-1,pos.y) instanceof Murs))
            result.add(new Arc(pos.x-1,pos.y));
        // y+1
        if (laby.getCase(pos.x,pos.y+1) != null && !(laby.getCase(pos.x,pos.y+1) instanceof Murs))
            result.add(new Arc(pos.x,pos.y+1));
        // y-1
        if (laby.getCase(pos.x,pos.y-1) != null && !(laby.getCase(pos.x,pos.y-1) instanceof Murs))
            result.add(new Arc(pos.x,pos.y-1));
        return result;
    }

    private Position getMin(Valeurs list){
        // On récupère une liste des parents des Noeuds adjacents (list)
        Set<Position> keys = list.parent.keySet();
        List<Position> listeKeys = new ArrayList<>(keys);

        // On définit le Minimum au 1er adjacent
        Position[] min = new Position[1];
        if (keys.size() == 0) min[0] = null;
        else min[0] = listeKeys.getFirst();

        // On test si la valeur du min actuel et inférieur a celui de chaque iteration
        listeKeys.forEach(val -> {
            if (list.getValeur(val) < list.getValeur(min[0])) min[0] = val;
        });

        return min[0];
    }

    private boolean checkEquals(Valeurs valeurs1,Valeurs valeurs2){
        if (valeurs1.valeur.size() != valeurs2.valeur.size()) return false;
        for (Position noeud : valeurs1.valeur.keySet()) {
            Integer val1 = valeurs1.getValeur(noeud);
            Integer val2 = valeurs2.getValeur(noeud);
            if (val1 == null || !val1.equals(val2)) return false;
        }
        return true;
    }

    private Valeurs copier(Valeurs v) {
        Valeurs copie = new Valeurs();
        for (Position noeud : v.valeur.keySet()) {
            copie.setValeur(noeud, v.getValeur(noeud));
            copie.setParent(noeud, v.getParent(noeud));
        }
        return copie;
    }
}

package com.Moteur;

public class Controler {
    public Commande move;
    public Commande view;

    public Controler() {
        move = new Commande();
        view = new Commande();
    }

    public int[] getVertor(Commande commande) {
        int[] vertor = {0, 0};
        if (commande.HAUT) vertor[1]--;
        if (commande.BAS) vertor[1]++;
        if (commande.GAUCHE) vertor[0]--;
        if (commande.DROITE) vertor[0]++;
        return vertor;
    }
}

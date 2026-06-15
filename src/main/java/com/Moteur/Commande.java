package com.Moteur;

/**
 * permet de representer une commande de l'utilisateur
 * 
 * @author vthomas
 *
 */
public class Commande {
	public boolean HAUT;
	public boolean BAS;
	public boolean GAUCHE;
	public boolean DROITE;
	
	public Commande() {
		this.HAUT = false;
		this.BAS = false;
		this.GAUCHE = false;
		this.DROITE = false;
	}

	public String toString(){
		return ("HAUT : " + HAUT + " BAS : " + BAS + " DROITE : " + DROITE);
	}
}

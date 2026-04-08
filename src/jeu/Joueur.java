package jeu;

import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zone;
	private MainJoueur main;
	
	public MainJoueur getMain() {
		return main;
	}

	public Joueur(String nom) {
		this.nom = nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Joueur joueur) {
			return nom.equals(joueur.nom);
		}
		return false;
	}

	@Override
	public String toString() {
		return nom;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
	}
	
	public Carte prendreCarte(Sabot sabot) {
		if(sabot.estVide()) return null;
		Carte carte = sabot.piocher();
		donner(carte);
		return carte;
		}
	
	public void deposer(Carte c) {
		zone.deposer(c);
	}
	
	public void estDepotAutorise(Carte carte) {
		zone.estDepotAutorise(carte);
		
	}
}

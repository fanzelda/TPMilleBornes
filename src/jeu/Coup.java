package jeu;

import cartes.Attaque;
import cartes.Carte;
import cartes.DebutLimite;

public class Coup {
	private Joueur courant;
	private Carte carte;
	private Joueur cible;
	
	
	protected Coup(Joueur courant, Carte carte, Joueur cible) {
		super();
		this.courant = courant;
		this.carte = carte;
		this.cible = cible;
	}


	public Joueur getCourant() {
		return courant;
	}


	public Carte getCarte() {
		return carte;
	}


	public Joueur getCible() {
		return cible;
	}
	
	public boolean estValide() {
		if (cible == null) {
			return true;
		}
		if (carte instanceof Attaque || carte instanceof DebutLimite) {
			return !courant.equals(cible);
		}
		return courant.equals(cible);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coup coup) {
			return courant.equals(coup.getCourant()) && carte.equals(coup.getCarte()) && cible.equals(coup.getCible());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 67 * courant.hashCode() * carte.hashCode() * cible.hashCode();
	}
}

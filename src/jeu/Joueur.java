package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	
	public HashSet<Coup> coupsPossibles(Set<Joueur> participants) {
		HashSet<Coup> coupsPossibles = new HashSet<>();
		for (Joueur courant : participants) {
			MainJoueur mainJoueur = courant.getMain();
			for (Iterator<Carte> iterator = mainJoueur.iterator(); iterator.hasNext();) {
				Carte carte = iterator.next();
				for (Joueur cible : participants) {
					Coup coup = new Coup(courant,carte,cible);
					if(coup.estValide()) {
						coupsPossibles.add(coup);
					}
				}
			}
		}
		return coupsPossibles;
	}
	
	public HashSet<Coup> coupsDefausse() {
		HashSet<Coup> coups = new HashSet<>();
		for (Iterator<Carte> iterator = main.iterator(); iterator.hasNext();) {
			Carte carte = iterator.next();
			coups.add(new Coup(this,carte,null));
		}
		return coups;
	}
	
	
	@Override
	public int hashCode() {
		return 19 * nom.hashCode() * zone.hashCode() * main.hashCode();
	}
}

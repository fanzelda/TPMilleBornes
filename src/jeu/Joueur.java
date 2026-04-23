package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cartes.Botte;
import cartes.Carte;

public class Joueur {
	private String nom;
	private ZoneDeJeu zone = new ZoneDeJeu();
	private MainJoueur main = new MainJoueur();
	private Random random = new Random();
	
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
	
	public int donnerKmParcourus() {
		return zone.donnerKmParcourus();
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
	
	public void deposer(Carte carte) {
		zone.deposer(carte);
	}
	
	public void estDepotAutorise(Carte carte) {
		zone.estDepotAutorise(carte);
		
	}
	
	public Set<Coup> coupsPossibles(Set<Joueur> participants) {
		Set<Coup> coupsPossibles = new HashSet<>();
		for (Joueur courant : participants) {
			MainJoueur mainJoueur = courant.getMain();
			checkCarte(participants, coupsPossibles, courant, mainJoueur);
		}
		return coupsPossibles;
	}

	private void checkCarte(Set<Joueur> participants, Set<Coup> coupsPossibles, Joueur courant, MainJoueur mainJoueur) {
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
	
	public Set<Coup> coupsDefausse() {
		Set<Coup> coups = new HashSet<>();
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
	
	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}
	
	public Coup choisirCoup(Set<Joueur> participants) {
		Set<Coup> coups = coupsPossibles(participants);
		
		if(coups.isEmpty()) {
			coups = coupsDefausse();
		}
		
		Coup[] coupsArr = coups.toArray(new Coup[coups.size()]);
		return coupsArr[random.nextInt(coupsArr.length)];
	}
	
	public String afficherEtatJoueur() {
		StringBuilder str = new StringBuilder("Joueur : " + nom);
		
		str.append("\nBottes : ");
		for (Botte botte : zone.getBottes()) {
			str.append(botte.toString()).append(" ");
		}
		
		str.append("\nLimitation de vitesse : " + (zone.donnerLimitationVitesse() != 200));
		if (zone.getPileBataille().isEmpty()) {
			str.append("\nSommet de la pile de Bataille : null");
		}
		else {
			str.append("\nSommet de la pile de Bataille : " + zone.getPileBataille().getFirst());
		}
		
		str.append("\nContenu de la main : ");
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			str.append(carte.toString());
			if (iter.hasNext()) {
				str.append(", ");
			}
		}
		str.append("\n");
		
		return str.toString();
	}

	public String getNom() {
		return nom;
	}
}

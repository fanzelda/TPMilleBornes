package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private static final int NBCARTES = 6;
	
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs = new LinkedHashSet<>();
	private Iterator<Joueur> iterJoueurs;
	

	public Jeu() {
		JeuDeCartes jeuCartes = new JeuDeCartes();
		Carte[] tabCarte = jeuCartes.donnerCartes();
		List<Carte> listeCartes = new ArrayList<>();

		Collections.addAll(listeCartes, tabCarte);
		listeCartes = GestionCartes.melanger(listeCartes);
		
		tabCarte = listeCartes.toArray(new Carte[0]);
		this.sabot = new Sabot(tabCarte);
	}
	
	public void inscrire(Joueur ...joueursInscrire) {
		for (Joueur joueur : joueursInscrire) {
			joueurs.add(joueur);
		}
	}
	
	public void distribuerCartes() {
		for (int i = 0; i < NBCARTES; i++) {
			for (Joueur joueur : joueurs) {
				joueur.donner(sabot.piocher());
			}
		}
	}
	
	public String jouerTour(Joueur joueur) {
		StringBuilder out = new StringBuilder();
		Carte cartePiochee = sabot.piocher();
		joueur.donner(cartePiochee);
		out.append("Le joueur " + joueur.getNom() + " a pioche " + cartePiochee.toString() + "\n");
		
		out.append("Il a dans sa main : [");
		for (Iterator<Carte> iter = joueur.getMain().iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			out.append(carte.toString());
			if (iter.hasNext()) {
				out.append(", ");
			}
		}
		out.append("]\n");
		
		Coup coup = joueur.choisirCoup(joueurs);
		Carte carteJouee = coup.getCarte();
		joueur.retirerDeLaMain(carteJouee);
		if (coup.getCible() == null) {
			sabot.ajouterCarte(carteJouee);
		}
		else {
			coup.getCible().deposer(carteJouee);
		}
		out.append(joueur.getNom() + " " + coup.toString() + "\n\n");
		
		return out.toString();
	}
	
	public Joueur donnerJoueurSuivant() {
		if (joueurs.isEmpty()) {
			throw new IllegalStateException("Aucun joueur");
		}
		if (iterJoueurs == null || !iterJoueurs.hasNext()) {
			iterJoueurs = joueurs.iterator();
		}
		return iterJoueurs.next();
	}
	
	public String lancer() {
		StringBuilder str = new StringBuilder();
		
		while(!sabot.estVide()) {
			Joueur courant = donnerJoueurSuivant();
			str.append(jouerTour(courant));
			
			int km = courant.donnerKmParcourus();
			if (km >= 1000) {
				str.append(courant.getNom()).append(" a parcouru ").append(Integer.toString(km));
				str.append(" km. Il remporte la partie.\n\n");
				str.append("Le Classement est :\n\n");
				str.append(afficherClassement(classement()));
				return str.toString();
			}
		}
		
		str.append("Le sabot est vide, fin de la partie.\n\n");
		str.append("Le Classement est :\n\n");
		str.append(afficherClassement(classement()));
		return str.toString();
	}
	
	private String afficherClassement(List<Joueur> liste) {
		StringBuilder out = new StringBuilder();
		for (Iterator<Joueur> iterator = liste.iterator(); iterator.hasNext();) {
			Joueur joueur = iterator.next();
			out.append(joueur.getNom());
			out.append(" a fini avec ");
			out.append(joueur.donnerKmParcourus());
			out.append(" km\n");
		}
		return out.toString();
	}
	
	public List<Joueur> classement() {
		NavigableSet<Joueur> joueurDecroissant = new TreeSet<>(
			new Comparator<Joueur>() {
				@Override
				public int compare(Joueur J1, Joueur J2) {
					if (J2.donnerKmParcourus()-J1.donnerKmParcourus()==0	) return 1;
					return J2.donnerKmParcourus()-J1.donnerKmParcourus();
				}
			}
		); //Création TreeSet
		
		joueurDecroissant.addAll(joueurs);
		
		List<Joueur> listeJoueursDec = new LinkedList<>();
		for (Iterator<Joueur> iterator = joueurDecroissant.iterator(); iterator.hasNext();) {
			Joueur joueur = iterator.next();
			listeJoueursDec.add(joueur);
		} //Remplissage liste
		
		return listeJoueursDec;
	}

}
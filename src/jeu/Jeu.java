package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs;
	
	public void inscrire(Joueur ...joueursInscrire) {
		for (Joueur joueur : joueurs) {
			joueurs.add(joueur);
		}
	}

	public Jeu() {
		JeuDeCartes jeuCartes = new JeuDeCartes();
		Carte[] tabCarte = jeuCartes.donnerCartes();
		List<Carte> listeCartes = new ArrayList<>();

		Collections.addAll(listeCartes, tabCarte);
		listeCartes = GestionCartes.melanger(listeCartes);
		
		tabCarte = listeCartes.toArray(new Carte[0]);
		this.sabot = new Sabot(tabCarte);
	}

}
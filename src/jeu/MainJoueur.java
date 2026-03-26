package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cartes.Carte;

public class MainJoueur{
	private List<Carte> listeCartes = new ArrayList<>();
	
	public void prendre(Carte carte) {
		listeCartes.add(carte);
	}
	
	public void jouer(Carte carte) {
		assert listeCartes.contains(carte);
		listeCartes.remove(carte);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Iterator<Carte> iterator = listeCartes.iterator(); iterator.hasNext();) {
			Carte carte = iterator.next();
			str.append(carte.toString());
			str.append("\n");
		}
		return str.toString();
	}
	
	
}

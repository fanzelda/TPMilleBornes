package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import cartes.Carte;

public class MainJoueur implements Iterable<Carte>{
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
	
	@Override
	public Iterator<Carte> iterator() {
		return new Iterateur();
	}
	
	private class Iterateur implements Iterator<Carte>{
		
		private int indiceIterateur = 0;
		
		@Override
		public boolean hasNext() {
			return indiceIterateur < listeCartes.size();
		}

		@Override
		public Carte next() {
			if (hasNext()) {
				return listeCartes.get(indiceIterateur++);
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
}

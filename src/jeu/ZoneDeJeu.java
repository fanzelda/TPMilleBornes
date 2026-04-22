package jeu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;

public class ZoneDeJeu {
	private LinkedList<Borne> pileBorne = new LinkedList<>();
	private LinkedList<Bataille> pileBataille = new LinkedList<>();
	private LinkedList<Limite> pileLimite = new LinkedList<>();
	private HashSet<Botte> bottes = new HashSet<>();
	
	public boolean estPrioritaire() {
		return bottes.contains(Cartes.PRIORITAIRE);
	}
	
	public int donnerLimitationVitesse() {
		if(estPrioritaire() || pileLimite.isEmpty() || pileLimite.getFirst() instanceof FinLimite) {
			return 200;
		}
		return 50;
	}
	
	public int donnerKmParcourus() {
		int somme = 0;
		for (ListIterator<Borne> iterator = pileBorne.listIterator(); iterator.hasNext();) {
			Borne borne = iterator.next();
			somme += borne.getKm();
		}
		return somme;
	}
	
	public void deposer(Carte c) {
		switch (c) {
		case Borne borne:
			pileBorne.addFirst(borne);
			break;
		case Limite limite:
			pileLimite.addFirst(limite);
			break;
		case Bataille bataille:
			pileBataille.addFirst(bataille);
				break;
			default:
				bottes.add((Botte) c);
	}
	}
	
//	public boolean peutAvancer() {
//		if (!pileBataille.isEmpty() && estPrioritaire()) {
//			Bataille sommet = pileBataille.getFirst();
//			return sommet.equals(Cartes.FEU_VERT);
//		}
//		return false;
//	}
	
	public boolean peutAvancer() {
		if(pileBataille.isEmpty()) {
			return estPrioritaire();
		}
		Bataille sommet = pileBataille.getFirst();
		if(sommet.equals(Cartes.FEU_VERT)) {
			return true;
		}
		if(sommet instanceof Parade || sommet.equals(Cartes.FEU_ROUGE)) {
			return estPrioritaire();
		}
		return estPrioritaire() && bottes.contains(new Botte(sommet.getType()));
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (pileBataille.isEmpty()) {
			return true;
		}
		if (estPrioritaire()) {
			return false;
		}
		
		Bataille sommet = pileBataille.getFirst();
		if (sommet instanceof Parade parade) {
			return !parade.equals(Cartes.FEU_VERT);
		}
		if (sommet instanceof Attaque) {
			return bottes.contains(new Botte(sommet.getType()));
		}
		else return sommet.equals(Cartes.FEU_ROUGE);
		
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		if (donnerLimitationVitesse() < borne.getKm() || donnerKmParcourus() + borne.getKm() > 1000) {
			return false;
		}
		Bataille sommetBataille = pileBataille.getFirst();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) {
			return false;
		}
		if (limite instanceof DebutLimite) {
			return pileLimite.isEmpty() || pileLimite.getFirst() instanceof FinLimite;
		}
		else {
			return !pileLimite.isEmpty() && pileLimite.getFirst() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bottes.contains(new Botte(bataille.getType()))) {
			return false;
		}
		if (bataille instanceof Attaque) {
			return peutAvancer();
		}
		if (bataille instanceof Parade parade) {
			if (parade.equals(Cartes.FEU_VERT)) {
				return estDepotFeuVertAutorise();
			}
			else {
				if (pileBataille.isEmpty()) {
					return false;
				}
				Bataille sommet = pileBataille.getFirst();
				if(sommet instanceof Attaque attaque) {
					return attaque.getType().equals(parade.getType());
				}
			}
		}
		return false;
	}
	
	public boolean estDepotAutorise(Carte carte) {
		if (carte instanceof Bataille bataille) {
			return estDepotBatailleAutorise(bataille);
		}
		
		if (carte instanceof Limite limite) {
			return estDepotLimiteAutorise(limite);
		}
		
		if (carte instanceof Borne borne) {
			return estDepotBorneAutorise(borne);
		}
		
		return true;
	}

	public Set<Botte> getBottes() {
		return bottes;
	}

	public List<Bataille> getPileBataille() {
		return pileBataille;
	}

	
}

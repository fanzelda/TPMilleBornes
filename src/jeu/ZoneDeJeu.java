package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;

public class ZoneDeJeu {
	private List<Borne> pileBorne = new ArrayList<>();
	private List<Bataille> pileBataille = new ArrayList<>();
	private List<Limite> pileLimite = new ArrayList<>();
	
	public int donnerLimitationVitesse() {
		if(pileLimite.isEmpty() || pileLimite.get(pileLimite.size()-1) instanceof FinLimite) {
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
		if (c instanceof Borne borne) {
			pileBorne.add(borne);
		}
		if (c instanceof Limite limite) {
			pileLimite.add(limite);
		}
		if (c instanceof Bataille bataille) {
			pileBataille.add(bataille);
		}
	}
	
	public boolean peutAvancer() {
		if (!pileBataille.isEmpty()) {
			Bataille sommet = pileBataille.getLast();
			return sommet.equals(Cartes.FEU_VERT);
		}
		return false;
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (!pileBataille.isEmpty()) {
			Bataille sommet = pileBataille.getLast();
			if (sommet instanceof Parade parade) {
				return !parade.equals(Cartes.FEU_VERT);
			}
			else return sommet.equals(Cartes.FEU_ROUGE);
		}
		return true;
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		if (donnerLimitationVitesse() < borne.getKm() || donnerKmParcourus() + borne.getKm() > 1000) {
			return false;
		}
		Bataille sommetBataille = pileBataille.getLast();
		return sommetBataille.equals(Cartes.FEU_VERT);
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (limite instanceof DebutLimite) {
			return pileLimite.isEmpty() || pileLimite.getLast() instanceof FinLimite;
		}
		else {
			return !pileLimite.isEmpty() && pileLimite.getLast() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
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
				Bataille sommet = pileBataille.getLast();
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
		
		return false;
	}
}

package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cartes.Bataille;
import cartes.Borne;
import cartes.FinLimite;
import cartes.Limite;

public class ZoneDeJeu {
	private List<Borne> pileBorne = new ArrayList<>();
	private List<Bataille> pileBataille = new ArrayList<>();
	private List<Limite> pileLimite = new ArrayList<>();
	
	public int donnerLimitationVitesse() {
		if(pileLimite.isEmpty() || pileLimite.get(pileLimite.size()) instanceof FinLimite) {
			return 200;
		}
		return 50;
	}
	
	public int donnerKmParcourus() {
		int somme = 0;
		for (ListIterator<Borne> iterator = pileBorne.listIterator(); iterator.hasNext();) {
			Borne borne = (Borne) iterator.next();
			somme = borne.getKm();
		}
		return somme;
	}
}

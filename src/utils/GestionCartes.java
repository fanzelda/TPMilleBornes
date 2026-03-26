package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GestionCartes {
	
	private GestionCartes(){
		throw new IllegalStateException();
	}
	
//	public static <E> E extraire(List<E> liste) {
//		int index = new Random().nextInt(liste.size());
//		return liste.remove(index);
//	}
	
	public static <E> E extraire(List<E> liste) {
		int index = new Random().nextInt(liste.size());
		ListIterator<E> iterator = liste.listIterator(index);
		E elt = iterator.next();
		iterator.remove();
		return elt;
	}
	
	public static <E> List<E> melanger(List<E> liste) {
		List<E> listeMelangee = new ArrayList<>();

		while (!liste.isEmpty()) {
			listeMelangee.add(extraire(liste));
		}

		return listeMelangee;
	}
	
	public static <T> boolean verifierMelange(List<T> liste, List<T> listeMelangee) {
		if(liste.size()!=listeMelangee.size()) return false;
		for (T t : liste) {
			if ((Collections.frequency(liste, t) != Collections.frequency(listeMelangee, t))) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> List<T> rassembler(List<T> liste) {
		List<T> listeRassemblee = new ArrayList<>();

		for (T t : liste) {
			int dernierIndex = listeRassemblee.lastIndexOf(t);
			if (dernierIndex == -1) {
				listeRassemblee.add(t);
			} else {
				listeRassemblee.add(dernierIndex, t);
			}
		}

		return listeRassemblee;
	}

	public static <T> boolean verifierRassemblement(List<T> liste) {
		for (ListIterator<T> iter = liste.listIterator(); iter.hasNext();) {
			T curr = (T) iter.next();
			
			if (iter.hasNext()) {
				T prochain = iter.next();

				if (!curr.equals(prochain) && !isSimilaire(liste, iter.nextIndex(), curr)) {
						return false;
					}
				iter.previous();
			}
			
		}

		return true;
	}

	private static <T> boolean isSimilaire(List<T> liste, int index, T elem) {
		for (ListIterator<T> iter = liste.listIterator(); iter.hasNext();) {
			if (iter.next().equals(elem)) {
				return false;
			}
		}
		return true;
	}
}

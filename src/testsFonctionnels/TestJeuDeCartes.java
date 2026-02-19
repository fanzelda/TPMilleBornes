package testsFonctionnels;

import cartes.JeuDeCartes;

public class TestJeuDeCartes {
	public static void main(String[] args) {
		JeuDeCartes jeu = new JeuDeCartes();
		System.out.println(jeu.affichageJeuDeCartes());
		
//        if (!jeu.checkCount()) {
//            System.out.println("erreur de nombre");
//        }

	}
}
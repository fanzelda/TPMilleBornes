package testsFonctionnels;

import java.util.Iterator;

import cartes.Botte;
import cartes.Carte;
import cartes.JeuDeCartes;
import cartes.Type;
import jeu.Sabot;

public class TestSabot  {
	public static void main(String[] args) {
		JeuDeCartes jeu = new JeuDeCartes();
		System.out.println(jeu.affichageJeuDeCartes());
		Sabot sabot = new Sabot(jeu.donnerCartes());
//		for(int i = 0; i < jeu.donnerCartes().length; i++){
//			System.out.println("Je pioche " + sabot.piocher());
//		}
		
		sabot.piocher();
		Iterator<Carte> it = sabot.iterator();

        while(it.hasNext()) {
            Carte c = it.next();
            System.out.println("je pioche " + c);
            //sabot.piocher();
            sabot.ajouterCarte(new Botte(Type.FEU));
            
            //it.remove();
        }
	}
	
	
}

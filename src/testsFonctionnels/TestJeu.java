package testsFonctionnels;

import jeu.Jeu;
import jeu.Joueur;

public class TestJeu {

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        Joueur jack = new Joueur("Jack");
        Joueur bill = new Joueur("Bill");
        Joueur luffy = new Joueur("Luffy");

        jeu.inscrire(jack, bill, luffy);

        jeu.distribuerCartes();

//
//        System.out.println(jack.afficherEtatJoueur());
//        System.out.println(jeu.jouerTour(jack));
//
//        System.out.println(bill.afficherEtatJoueur());
//        jeu.jouerTour(bill);
//
//        System.out.println(luffy.afficherEtatJoueur());
//        jeu.jouerTour(luffy);

        System.out.println(jeu.lancer());
    }

}
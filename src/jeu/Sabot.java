package jeu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import cartes.Carte;

public class Sabot implements Iterable<Carte> {
    
    private Carte[] tabCartes;
    private int nbCartes;
    private int nbOpe = 0;
    
    @Override
    public Iterator<Carte> iterator() {
        return new IterateurSabot();
    }
    
    private class IterateurSabot implements Iterator<Carte>{
        private int idIterateur = 0;
        private boolean nextEffectue = false;
        private int nbOpeRef = nbOpe;
        
        @Override
        public boolean hasNext() {
            return idIterateur < nbCartes;
        }
        
        private void verifConcurrence() {
            if (nbOpe != nbOpeRef) {
                throw new ConcurrentModificationException();
            }
        }
        
        public Carte next() {
            verifConcurrence();
            if (hasNext()) {
                Carte carte = tabCartes[idIterateur];
                idIterateur++;
                nextEffectue = true;
                return carte;
            }
            else {
                throw new NoSuchElementException();
            }
        }
        @Override
        public void remove() {
            verifConcurrence();
            if (estVide() || !nextEffectue) {
                throw new IllegalStateException();
            }
            for (int i = idIterateur - 1; i < nbCartes - 1; i++) {
                tabCartes[i] = tabCartes[i + 1];
            }
            
            nextEffectue = false;
            idIterateur--;
            nbCartes--;
            nbOpeRef++;
            nbOpe++;
        }
    }
    
    public Sabot(Carte[] tabCartes) {
        this.tabCartes = tabCartes;
        nbCartes = tabCartes.length;
    }
    
    public boolean estVide() {
        return nbCartes == 0;
    }

    public void ajouterCarte(Carte carte) {
        if (nbCartes == tabCartes.length) {
            throw new IllegalStateException();
        }
        else {
            tabCartes[nbCartes] = carte;
            nbCartes++;
        }
    }
    
    public Carte piocher() {
        if (estVide()) {
            throw new IllegalStateException();
        }
        else {
            Iterator<Carte> iter = iterator();
            Carte carte = iter.next();
            iter.remove();
            
            return carte;
        }
        
    }

    

}
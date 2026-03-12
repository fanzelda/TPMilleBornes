package cartes;

public class JeuDeCartes {
	private Configuration[] typesDeCartes = new Configuration[] {
			// Bornes
			new Configuration(new Borne(25), 10), new Configuration(new Borne(50), 10),
			new Configuration(new Borne(75), 10), new Configuration(new Borne(100), 12),
			new Configuration(new Borne(200), 4),

			// Parades
			new Configuration(new Parade(Type.FEU), 14), new Configuration(new FinLimite(), 6),
			new Configuration(new Parade(Type.ESSENCE), 6), new Configuration(new Parade(Type.CREVAISON), 6),
			new Configuration(new Parade(Type.ACCIDENT), 6),

			// Attaques
			new Configuration(new Attaque(Type.FEU), 5), new Configuration(new DebutLimite(), 4),
			new Configuration(new Attaque(Type.ESSENCE), 3), new Configuration(new Attaque(Type.CREVAISON), 3),
			new Configuration(new Attaque(Type.ACCIDENT), 3),

			// Bottes
			new Configuration(new Botte(Type.FEU), 1), new Configuration(new Botte(Type.ESSENCE), 1),
			new Configuration(new Botte(Type.CREVAISON), 1), new Configuration(new Botte(Type.ACCIDENT), 1) };

	public String affichageJeuDeCartes() {
		StringBuilder result = new StringBuilder();
		result.append("JEU :\n\n");
		for (int i = 0; i < typesDeCartes.length; i++) {
			result.append(typesDeCartes[i].getNbExemplaires());
			result.append(" ");
			result.append(typesDeCartes[i].getCarte());
			result.append("\n");
		}
		return result.toString();
	}

	public Carte[] donnerCartes() {
		int nbTotalCartes = 0;
		for (Configuration configuration : typesDeCartes) {
			nbTotalCartes = nbTotalCartes + configuration.getNbExemplaires();
		}
		Carte[] tabCartes = new Carte[nbTotalCartes];
		int indiceCarte = 0;
		for (Configuration configuration : typesDeCartes) {
			for (int i = 0; i < configuration.getNbExemplaires(); i++) {
				tabCartes[indiceCarte] = configuration.getCarte();
				indiceCarte++;
			}
		}
		return tabCartes;
	}
	
	public boolean checkCount() {
		Carte[] tabC = donnerCartes();
		for(Configuration config : typesDeCartes) {
			int nbEx = compteCarte(config.getCarte(),tabC);
			if (nbEx != config.getNbExemplaires()) return false;
		}
		return true;
	}
	
	private int compteCarte(Carte carte, Carte[] tabC) {
		int i = 0;
		for (Carte carte2 : tabC) {
			if(carte2.equals(carte)) i++;
		}
		return i;
	}

	private static class Configuration {
		private int nbExemplaires;
		private Carte carte;

		private Configuration(Carte carte, int nbExemplaires) {
			this.nbExemplaires = nbExemplaires;
			this.carte = carte;
		}

		private int getNbExemplaires() {
			return nbExemplaires;
		}

		private Carte getCarte() {
			return carte;
		}
	}
}

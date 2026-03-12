package cartes;

public abstract class Carte {
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Carte carte) {
			return getClass().equals(carte.getClass());
		}
		return false;
	}
}

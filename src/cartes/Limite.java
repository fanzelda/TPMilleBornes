package cartes;

public abstract class Limite extends Carte {
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Limite limite) {
			return getClass().equals(limite.getClass());
		}
		return false;
	}
}

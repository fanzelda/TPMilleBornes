package cartes;

public enum Type {
	FEU("Feu Rouge","Feu Vert","Vehicule Prioritaire"),
	ESSENCE("Panne d'Essence","Essence","Citerne d'Essence"),
	CREVAISON("Crevaison","Roue de Secours","Increvable"),
	ACCIDENT("Accident","Reparations","As du Volant");
	
	private String attaque;
	private String parade;
	private String botte;
	
	private Type(String attaque, String parade, String botte) {
		this.attaque= attaque;
		this.parade = parade;
		this.botte = botte;
	}

	public String getAttaque() {
		return attaque;
	}

	public String getParade() {
		return parade;
	}

	public String getBotte() {
		return botte;
	}
}

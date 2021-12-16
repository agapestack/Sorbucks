package drink;

import db.IngredientPersister;

public class Ingredient {
	private String name;
	
	public Ingredient(String name) {
		this.name = name;
	}
	
	public Ingredient clone() {
		return new Ingredient(this.name);
	}
	
	public static void addToDb(Ingredient i) {
		IngredientPersister.insert(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}

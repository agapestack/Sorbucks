package drink;

import java.util.ArrayList;

public abstract class Drink {
	private double price;
	private String name;
	private int sugarQte;
	private int volume;
	private ArrayList<Ingredient> ingredients;
	@SuppressWarnings("unused")
	private final int id;
	private static int cpt = 0;
	
	public Drink(double price, String name, int sugarQte, int volume) {
		ingredients = new ArrayList<Ingredient>();
		this.price = price;
		this.name = name;
		this.sugarQte = sugarQte;
		this.volume = volume;
		this.id = cpt++;
	}
	
	public Drink(double price, String name, int sugarQte, int volume, ArrayList<Ingredient> ingredients) {
		this(price, name, sugarQte, volume);
//		Careful here no deep copy just referencing for create instance from database
		this.ingredients = ingredients;
	}
	
	public String toString() {
		String res = this.name + " price: " + this.price + "$\nIngredients: ";
		for(int i=0; i < ingredients.size(); i++) {
			res += ingredients.get(i).toString() + "\t";
		}
		return res + "\n";
	}
	
	public DrinkType getDrinkType() {
		return DrinkType.valueOf(this.getClass().getSimpleName());
	}
	
	public void newIngredientListReference(ArrayList<Ingredient> ingredients)  {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient i) {
		ingredients.add(i);
	}
	
	public void removeIngredient(Ingredient i) {
		ingredients.remove(i);
	}
	
	public ArrayList<Ingredient> getIngredientList() {
		return ingredients;
	}
	
	
//	Setters/ Getters

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSugarQte() {
		return sugarQte;
	}

	public void setSugarQte(int sugarQte) {
		this.sugarQte = sugarQte;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	
	
}

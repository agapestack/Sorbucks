package drink.coffee;

import drink.Drink;

public abstract class Coffee extends Drink {
	private boolean isGroundCoffee;
//	coffeeType example : Ristretto, Gommosa, Con Hielo, Lungo, ...
	private String coffeeType;
	
	public Coffee(double price, String name, int sugarQte, int volume, boolean isGroundCoffee, String coffeeType) {
		super(price, name, sugarQte, volume);
		this.isGroundCoffee = isGroundCoffee;
	}
	
	public String toString() {
		return super.toString();
	}
	
//	Getters & Setters
	public boolean isGroundCoffee() {
		return isGroundCoffee;
	}
	
	public int isGroundCoffeeInt() {
		return (isGroundCoffee == true ? 1 : 0);
	}
	
	public void setGroundCoffee() {
		this.isGroundCoffee = !this.isGroundCoffee;
	}

	public String getCoffeeType() {
		return coffeeType;
	}

	public void setCoffeeType(String coffeeType) {
		this.coffeeType = coffeeType;
	}
	

}

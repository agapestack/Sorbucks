package drink.coffee;

// LatteCoffee = Coffee with milk
public class LatteCoffee extends Coffee{
	
	private int milkQte;
	
	public LatteCoffee(double price, String name, int sugarQte, int volume, boolean isGroundCoffee, String coffeeType, int milkQte) {
		super(price, name, sugarQte, volume, isGroundCoffee, coffeeType);
		this.milkQte = milkQte;
	}
	
	public String toString() {
		return "Latte Coffee\nmilk: " + this.milkQte + "mL\n" + super.toString();
	}

	
//	 Getters & Setters
	public int getMilkQte() {
		return milkQte;
	}

	public void setMilkQte(int milkQte) {
		this.milkQte = milkQte;
	}
	
	
	
}

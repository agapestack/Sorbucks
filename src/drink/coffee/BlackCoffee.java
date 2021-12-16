package drink.coffee;

public class BlackCoffee extends Coffee {
	
	public BlackCoffee(double price, String name, int sugarQte, int volume, boolean isGroundCoffee, String coffeeType) {
		super(price, name, sugarQte, volume, isGroundCoffee, coffeeType);
	}
	
	public String toString() {
		return "Black Coffee\n" + super.toString();
	}
	
}

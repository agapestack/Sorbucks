package drink.tea;

public class MilkTea extends Tea{
	
	private int milkQte;
	
	public MilkTea(double price, String name, int sugarQte, int volume, int infusionTime, String teaType, int milkQte) {
		super(price, name, sugarQte, volume, infusionTime, teaType);
		this.milkQte = milkQte;
	}

//	Getters & Setters
	public int getMilkQte() {
		return milkQte;
	}

	public void setMilkQte(int milkQte) {
		this.milkQte = milkQte;
	}
	
	public String toString() {
		return "Milk Tea\n" + super.toString();
	}
	
}

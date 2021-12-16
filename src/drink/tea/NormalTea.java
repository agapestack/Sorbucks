package drink.tea;

public class NormalTea extends Tea{
	public NormalTea(double price, String name, int sugarQte, int volume, int infusionTime, String teaType) {
		super(price, name, sugarQte, volume, infusionTime, teaType);
	}
	
	public String toString() {
		return "Normal Tea\n" + super.toString();
	}
}

package drink.tea;

import drink.Drink;

public abstract class Tea extends Drink {
//	 infusion Time in seconds
	private int infusionTime;
	private String teaType;
	
	public Tea(double price, String name, int sugarQte, int volume, int infusionTime, String teaType) {
		super(price, name, sugarQte, volume);
		this.infusionTime = infusionTime;
		this.teaType = teaType;
	}
	
	public String toString() {
		return super.toString();
	}
	
//	Getters & Setters
	public int getInfusionTime() {
		return infusionTime;
	}
	public void setInfusionTime(int infusionTime) {
		this.infusionTime = infusionTime;
	}
	public String getTeaType() {
		return teaType;
	}
	public void setTeaType(String teaType) {
		this.teaType = teaType;
	}
	
	
}

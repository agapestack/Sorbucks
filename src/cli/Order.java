package cli;

import java.util.ArrayList;
import drink.Drink;

public class Order {
	private String customerName;
	private ArrayList<Drink> drinkList;
	
	public Order(String customerName) {
		this.customerName = customerName;
		drinkList = new ArrayList<Drink>();
	}
	
// Getters & Setters	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public void addDrink(Drink d) {
		drinkList.add(d);
	}
	
	public ArrayList<Drink> getDrinkList() {
		return drinkList;
	}
	
	public void removeDrink(Drink d) {
		drinkList.remove(d);
	}
	
	public int getDrinkNumber() {
		return drinkList.size();
	}
	
	public double getTotal() {
		double total = 0.0;
		for(int i=0; i < drinkList.size(); i++) {
			total += drinkList.get(i).getPrice();
		}
		return total;
	}
	
	
}

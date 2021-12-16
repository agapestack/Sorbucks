package test;

import java.util.ArrayList;

import db.DrinkIngredientPersister;
import db.DrinkPersister;
import drink.Drink;

public class Test {
	public static void main(String[] args) {
		DrinkPersister dp = DrinkPersister.getInstance();
		ArrayList<Drink> drinkListFromDb = dp.getAll();
		
		for(int i=0; i < drinkListFromDb.size(); i++) {
			System.out.println(drinkListFromDb.get(i).toString());
		}	
		
//		DrinkIngredientPersister.DropTable();
//		dp.DropTable();
	}
}

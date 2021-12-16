package main;

import java.util.ArrayList;

import db.DbSqlite;
import db.DrinkIngredientPersister;
import db.IngredientPersister;
import db.DrinkPersister;
import drink.Drink;
import drink.Ingredient;
import drink.coffee.*;
import drink.tea.*;

public class DrinkTest {
	public static void main(String[] args) {
//		Checking that we can connect to database
		DbSqlite.connect();
		DrinkIngredientPersister.CreateTable();
		
		DrinkPersister dp = DrinkPersister.getInstance();
		ArrayList<Drink> drinkList = new ArrayList<Drink>();
		
//		Creating drink table
		dp.CreateTable();
		
		ArrayList<Ingredient> ingredients = IngredientPersister.getAll();
//		for(int i=0; i < ingredients.size(); i++) {
//			System.out.println(ingredients.get(i).toString());
//		}
		
//		System.out.println(ingredients.size());
		if(ingredients.size() < 1) {
			System.out.println("Need to seed Ingredient table to test DrinkPersister\n");
		}

		LatteCoffee lc1 = new LatteCoffee(7.0, "Special Latte", 2, 350, false, "Classic Latte", 50);
		BlackCoffee bc1 = new BlackCoffee(3.5, "Expresso", 2, 60, false, "Gommosa");
		MilkTea mt1 = new MilkTea(4.5, "Thé au Lait", 0, 350, 120, "Thé au Perles", 50);
		NormalTea nt1 = new NormalTea(2.5, "Thé vert", 2, 200, 100, "thé à la menthe");
		
		drinkList.add(mt1);
		drinkList.add(lc1);
		drinkList.add(bc1);
		drinkList.add(nt1);
		
//		Adding randomly some ingredient
		for(Drink d : drinkList) {
			int rand = (int)(Math.random() * (ingredients.size()));
			int rand2 = (int)(Math.random() * (ingredients.size()));
			d.addIngredient(ingredients.get(rand));
			d.addIngredient(ingredients.get(rand2));
			dp.insert(d);
		}
		
		ArrayList<Drink> drinkListFromDb = dp.getAll();
		
		for(int i=0; i < drinkListFromDb.size(); i++) {
			System.out.println(drinkListFromDb.get(i).toString());
		}
		
		
		
//		Dropping table
//		DrinkIngredientPersister.DropTable();
//		dp.DropTable();
	}
}

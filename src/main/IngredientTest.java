package main;

import java.util.ArrayList;

import db.DbSqlite;
import db.IngredientPersister;
import drink.Ingredient;

public class IngredientTest {

	public static void main(String[] args) {
//		Verify that connection can be established successfully
		DbSqlite.connect();
		IngredientPersister.CreateTable();
		
		System.out.println("Nb of ingredients in DB initially: " + IngredientPersister.getMaxId());
		
		Ingredient miel = new Ingredient("Miel");
//		Seeding ingredient table from a String list
		String[] listNomIngredient = {"Chocolat", "Crème Chantilly", "Fraise", "Sirop saveur Chocolat", "Sirop saveur Chocolat Blanc", "Crème fouettée sucrée", "Poudre de Cacao", "Glace Pilée"};
		
		for(int i=0; i < listNomIngredient.length; i++) {
			Ingredient.addToDb(new Ingredient(listNomIngredient[i]));
		}
		Ingredient.addToDb(miel);
		
		System.out.println("Nb of ingredients in DB: " + IngredientPersister.getMaxId());
		
//		Removing Element
		IngredientPersister.remove(miel);
		
		System.out.println("Nb of ingredients in DB: " + IngredientPersister.getMaxId());
		
		ArrayList<Ingredient> iList = new ArrayList<Ingredient>();
		iList = IngredientPersister.getAll();
//		Showing ingredient table
		System.out.println("Element in Ingredient table:\n");
		iList.forEach(e -> System.out.println(e.toString()));

		
//		Dropping ingredient table
//		IngredientPersister.DropTable();
		
	}

}

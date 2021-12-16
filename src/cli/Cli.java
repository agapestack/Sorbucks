package cli;
import java.util.ArrayList;
import java.util.Scanner;

import db.DrinkPersister;
import drink.Drink;

public class Cli {
	private static Cli instance = new Cli();
	private ArrayList<Drink> drinkList = DrinkPersister.getInstance().getAll();
	
	private Cli() {

	}
	
	public static Cli getCli() {
		return instance;
	}
	
	public void run() {
//		Order customerOrder = new Order
		System.out.println("Welcome to Sorbuck please enter your name: ");
		String name = getUserName();
		Order order = new Order(name);
		
		mainMenu(order);
		
	}
	
	public void mainMenu(Order o) {
		
		boolean quit = false;
		
		do {
			System.out.println(
					"1/ Ajouter une boisson à la commande\n" +
					"2/ Payer\n" +
					"3/ Partir\n");
			
			int choix = getUserInt();
			
			try {
				if(choix < 1 || choix > 3) {
					throw new InputException("Mauvaise entrée");
				}
			} catch (InputException e) {
				System.out.println(e.getMessage());
			}	
			
			switch(choix) {
				case 1: 
					drinkMenu(o);
					break;
				case 2: 
					pay(o);
					quit = true;
					break;
				case 3: 
					steal(o);
					quit = true;
					break;
			}
			
		}while(quit == false);
		
	}
	
	private void drinkMenu(Order o) {
		for(int i=1; i < drinkList.size(); i++) {
			System.out.println(i + "/ " + drinkList.get(i).toString() +"\n");
		}
		
		int choix = getUserInt();
		try {
			if(choix < 0 || choix > drinkList.size()) {
				throw new InputException("Mauvaise entrée");
			}
		} catch(InputException e) {
			System.out.println(e.getMessage());
		}
		
		o.addDrink(drinkList.get(choix));
	}
	
	private void pay(Order o) {
		if(o.getTotal() <= 0) {
			System.out.println("Rien à payer vous êtes libre de partir\n");
			return;
		}
		System.out.println("Total: " + o.getTotal() + "€");
	}
	
	private void steal(Order o) {
		if(o.getTotal() > 0) {
			System.out.println("Il va falloir courrir vite!\n");
		} else {
			System.out.println("Vous partez sans avoir trouver votre bonheur...\n");
		}
	}
	
	
	
	private String getUserName() {
		return this.getUserString();
	}
	
	private int getUserInt() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int userInt = scanner.nextInt();
		return userInt;
	}
	
	private String getUserString() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String userString = scanner.nextLine();
		return userString;
	}
	
}

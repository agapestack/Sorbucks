package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import drink.Drink;
import drink.DrinkType;
import drink.Ingredient;
import drink.coffee.BlackCoffee;
import drink.coffee.LatteCoffee;
import drink.tea.MilkTea;
import drink.tea.NormalTea;

// Singleton to match exercice expectation
public class DrinkPersister {
	private static DrinkPersister instance = new DrinkPersister();
	
	private DrinkPersister() {}
	
	public static final DrinkPersister getInstance()  {
		return instance;
	}
	
	public void CreateTable() {
		String sql = "CREATE TABLE IF NOT EXISTS \"drink\" (\n" + 
				"	\"id\"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" + 
				"	\"price\" REAL NOT NULL,\n" +
				"	\"name\"	TEXT NOT NULL UNIQUE,\n" + 
				"	\"sugarQte\"	INTEGER,\n" + 
				"	\"volume\"	INTEGER,\n" + 
				"	\"infusionTime\"	INTEGER DEFAULT NULL,\n" + 
				"	\"isGroundCoffee\"	INTEGER DEFAULT NULL,\n" + 
				"	\"milkQte\"	INTEGER DEFAULT NULL,\n" + 
				"	\"coffeeType\" TEXT DEFAULT NULL,\n" + 
				"	\"teaType\" TEXT DEFAULT NULL\n" + 
				")";
		
		try (Connection conn = DbSqlite.getConnection()){
			System.out.println("Creating drink table in database...\n");
			Statement stmt = conn.createStatement();
			
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void DropTable() {
		String sql = "DROP TABLE IF EXISTS drink";
		
		try (Connection conn = DbSqlite.getConnection()){
			System.out.println("Drop drink table in database...\n");
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public void insert(Drink d) {
		String sql = "INSERT INTO main.drink(name, price, sugarQte, volume, infusionTime, isGroundCoffee, milkQte, coffeeType, teaType) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
//		Adding d to drink table
		try (Connection conn = DbSqlite.getConnection()){
			
			ArrayList<Ingredient> ingredients = d.getIngredientList();
			for(int i=0; i < ingredients.size(); i++) {
//				adding drink-relation in drink_relation table
				DrinkIngredientPersister.insert(d, ingredients.get(i));
			}
			
			System.out.println("Inserting " + d.toString());
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, d.getName());
			pstmt.setDouble(2, d.getPrice());
			pstmt.setInt(3, d.getSugarQte());
			pstmt.setInt(4, d.getVolume());
			
			DrinkType type = d.getDrinkType();
			System.out.println(type);
			
//			THIS IS NOT A GOOD PRACTICE TO USE ENUM AND CLASSNAME TO MAP OBJECT INTO A SINGLE TABLE, this is just to simplify database handling and practice java accordingly to the exercice expectation !!!
//			5->infusionTime		6->isGroundCoffee	7->milkQte	8->coffeeType 9->teaType
			
			switch(type) {
				case BlackCoffee:
					BlackCoffee tmp1 = (BlackCoffee) d;
					pstmt.setInt(6, tmp1.isGroundCoffeeInt());
					pstmt.setString(8,  tmp1.getCoffeeType());
					break;
				case LatteCoffee:
					LatteCoffee tmp2 = (LatteCoffee) d;
					pstmt.setInt(6, tmp2.isGroundCoffeeInt());
					pstmt.setInt(7, tmp2.getMilkQte());
					pstmt.setString(8,  tmp2.getCoffeeType());
					break;
				case MilkTea:
					MilkTea tmp3 = (MilkTea) d;
					pstmt.setInt(5, tmp3.getInfusionTime());
					pstmt.setInt(7, tmp3.getMilkQte());
					pstmt.setString(9,  tmp3.getTeaType());
					break;
				case NormalTea:
					NormalTea tmp4 = (NormalTea) d;
					pstmt.setInt(5, tmp4.getInfusionTime());
					pstmt.setString(9,  tmp4.getTeaType());
					break;
				default:
					System.out.println("Error: trying to insert unknow drink type to drink table\n");
					System.exit(-2);
					
			}
			
			pstmt.execute();
	        pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	public void remove(Drink d) {
		String sql = "DELETE FROM drink WHERE name = ?";
		
		try (Connection conn = DbSqlite.getConnection()) {
			System.out.println("Removing " + d.toString() + " to drink table");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  d.getName());
			pstmt.executeUpdate();
//			removing link in drink_ingredient table
			DrinkIngredientPersister.removeDrinkRelation(d);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public ArrayList<Drink> getAll() {
		String sql = "SELECT * FROM drink";
		ArrayList<Drink> res = new ArrayList<Drink>();
		
		try (Connection conn = DbSqlite.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql);
			while(rs.next()) {
				//name, price, sugarQte, volume, infusionTime, isGroundCoffee, milkQte, coffeeType, teaType
				double price = rs.getDouble("price");
				String name = rs.getString("name");
				int sugarQte = rs.getInt("sugarQte");
				int volume = rs.getInt("volume");
				int infusionTime = rs.getInt("infusionTime");
				boolean isGroundCoffee = (rs.getInt("isGroundCoffee") == 1 ? true : false);
				int milkQte = rs.getInt("milkQte");
				String coffeeType = rs.getString("coffeeType");
				String teaType = rs.getString("teaType");
				
//				System.out.println(name + "\t infusionTime=" + infusionTime + "\t coffeeType=" + coffeeType + "\t teaType=" + teaType + "\tmilkQte=" + milkQte);
				
				ArrayList<Ingredient> ingredients = DrinkIngredientPersister.getIngredientFromDrinkName(name);
//				Generating class depending on info from DB
				if(infusionTime !=0) {
//					Tea case
					if(milkQte !=0) {
						MilkTea mt = new MilkTea(price, name, sugarQte, volume, infusionTime, teaType, milkQte);
						mt.newIngredientListReference(ingredients);
						res.add(mt);
					} else {
						NormalTea nt = new NormalTea(price, name, sugarQte, volume, infusionTime, teaType);
						nt.newIngredientListReference(ingredients);
						res.add(nt);
					}
				} else {
//					Coffee case
					if(milkQte != 0) {
						LatteCoffee latte = new LatteCoffee(price, name, sugarQte, volume, isGroundCoffee, coffeeType, milkQte);
						latte.newIngredientListReference(ingredients);
						res.add(latte);
					} else {
						BlackCoffee black = new BlackCoffee(price, name, sugarQte, volume, isGroundCoffee, coffeeType);
						black.newIngredientListReference(ingredients);
						res.add(black);
					}
				}

						

						

			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		
		return res;
	}
		
}
